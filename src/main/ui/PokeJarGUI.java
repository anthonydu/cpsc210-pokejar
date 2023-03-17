package ui;

import model.Box;
import model.*;
import org.json.JSONException;
import persistence.InvalidJarException;
import persistence.JsonFile;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * PokeJar App's GUI
 *
 * @author Anthony Du
 */
public class PokeJarGUI extends JFrame {
    // Jar Object
    private Jar jar;

    // Main Panel
    private JPanel main;

    // Menu Bar
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveButton;
    private JMenuItem loadButton;

    // Left Pane
    private JTabbedPane leftPane;
    // Box Panel
    private JPanel boxPanel;
    private JButton addPokemon;
    private JButton removePokemon;
    private JScrollPane boxScrollPane;
    private DefaultListModel<Pokemon> boxModel;
    private JList boxList;
    // Team Label
    private JLabel teamLabel;

    // Right Pane
    private JTabbedPane rightPane;
    // Basic Info Panel
    private JPanel infoPanel;
    private JLabel infoLabel;
    private JButton editButton;
    // Edit Panel
    private JPanel editPanel;
    private JLabel nameLabel;
    private JTextField nameField;
    private JPanel typesPanel;
    private JButton addMove;
    private JButton removeMove;
    private DefaultListModel<Move> movesModel;
    private JList movesList;
    private JButton doneEditing;

    /**
     * WindowAdapter to handle windowClosing.
     */
    private WindowAdapter windowAdapter = new WindowAdapter() {
        /**
         * Asks the user to confirm before closing the app and autosaving.
         *
         * @param e the WindowEvent
         */
        @Override
        public void windowClosing(WindowEvent e) {
            int canceled = JOptionPane.showConfirmDialog(
                    null, "Are you sure you want to close PokéJar?",
                    "Confirm Close", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
            );
            if (canceled == 0 && autosave()) {
                System.exit(2);
            }
        }

        /**
         * Saves the current app state to ./data/autosave.json.
         *
         * @return true if autosave was successful, false otherwise
         */
        private boolean autosave() {
            prepareSave();
            try {
                new JsonFile("./data/autosave.json").saveJarToFile(jar);
                return true;
            } catch (IOException ex) {
                int canceled = JOptionPane.showConfirmDialog(
                        null, "Autosave failed! Continue to close?",
                        "Autosave Error", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
                );
                if (canceled == 0) {
                    return true;
                }
                return false;
            }
        }
    };

    /**
     * DocumentListener for dynamically updating the UI upon field change.
     */
    private DocumentListener nameFieldListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            handleNameChange();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            handleNameChange();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            handleNameChange();
        }
    };

    /**
     * Constructs PokeJarGUI and sets it to visible.
     */
    public PokeJarGUI() {
        window();
        menuBar();
        mainPanel();
        leftPane();
        rightPane();
        editPanel();
        this.setVisible(true);
    }

    /**
     * Sets up window.
     * <p>
     * MODIFIES: this
     */
    private void window() {
        this.setTitle("PokéJar");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(800, 600);
        this.setResizable(false);
        this.addWindowListener(windowAdapter);
    }

    /**
     * Sets up menuBar.
     * <p>
     * MODIFIES: this
     */
    private void menuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        saveButton = new JMenuItem("Save As...");
        saveButton.addActionListener(e -> handleSave());
        loadButton = new JMenuItem("Load Save...");
        loadButton.addActionListener(e -> handleLoad());
        fileMenu.add(saveButton);
        fileMenu.add(loadButton);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
    }

    /**
     * Sets up and adds mainPanel to window.
     * <p>
     * MODIFIES: this
     */
    private void mainPanel() {
        main = new JPanel(new GridLayout(1, 2));
        main.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.add(main);
    }

    /**
     * Sets up and adds leftPane to mainPanel.
     * <p>
     * MODIFIES: this
     */
    private void leftPane() {
        leftPane = new JTabbedPane();
        // Box Panel
        boxPanel = new JPanel();
        boxModel = new DefaultListModel<>();
        boxList = new JList(boxModel);
        loadAutosave();
        boxList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        addPokemon = new JButton("New Pokémon");
        addPokemon.addActionListener(e -> handleAddPokemon());
        removePokemon = new JButton("Remove Pokémon");
        boxList.addListSelectionListener(e -> handleListSelection(e));
        boxList.setFixedCellWidth(300);
        boxScrollPane = new JScrollPane(boxList);
        boxScrollPane.setPreferredSize(new Dimension(300, 370));
        boxScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        removePokemon.addActionListener(e -> handleRemovePokemon());
        boxPanel.add(addPokemon);
        boxPanel.add(removePokemon);
        boxPanel.add(boxScrollPane);
        // Team Label
        teamLabel = new JLabel("Coming soon!", SwingConstants.CENTER);
        // Add Tabs
        leftPane.add("Box", boxPanel);
        leftPane.add("Team", teamLabel);
        main.add(leftPane);
    }

    /**
     * Sets the rightPane to reflect the newly selected Pokemon.
     * <p>
     * MODIFIES: this
     *
     * @param e the ListSelectionEvent
     */
    private void handleListSelection(ListSelectionEvent e) {
        // Prevent event loop and NullPointerException when calling getSelectedPokemon
        if (e.getValueIsAdjusting() || boxList.getSelectedIndex() == -1) {
            return;
        }
        // Sets nameField to name of the selected Pokemon
        nameField.setText(getSelectedPokemon().getName());
        // Sets checkBoxes to represent the selected Pokemon's type
        for (Component checkBox : typesPanel.getComponents()) {
            model.Type checkBoxType = model.Type.fromSafeString(((JCheckBox) checkBox).getText());
            if (getSelectedPokemon().getTypes().contains(checkBoxType)) {
                ((JCheckBox) checkBox).setSelected(true);
            } else {
                ((JCheckBox) checkBox).setSelected(false);
            }
        }
        // Updates infoLabel
        setInfoLabel();
        // Disables checkBoxes if two are selected
        handleCheckType();
        // Disables addMove if there are already four moves
        limitNumberOfMoves();
        // Sets moves to the selected Pokemon's moves
        reloadMoves();
    }

    /**
     * Updates the text of infoLabel.
     * <p>
     * MODIFIES: this
     */
    private void setInfoLabel() {
        String info = "<html><pre>Name: " + getSelectedPokemon().getName() + "\n\nTypes:";
        for (model.Type t : getSelectedPokemon().getTypes()) {
            info += " " + t;
        }
        info += "\n\nMoves:";
        for (Move m : getSelectedPokemon().getMoves()) {
            info += "\n" + m;
        }
        info += "</pre></html>";
        infoLabel.setText(info);
    }

    /**
     * Adds a Pokemon to boxModel.
     * <p>
     * MODIFIES: this
     */
    private void handleAddPokemon() {
        Pokemon newPokemon = new Pokemon();
        boxModel.addElement(newPokemon);
        boxList.setSelectedIndex(boxModel.size() - 1);
    }

    /**
     * Sets up and adds rightPane to mainPanel.
     * <p>
     * MODIFIES: this
     */
    private void rightPane() {
        rightPane = new JTabbedPane();
        rightPane.addChangeListener(e -> setInfoLabel());
        // Info Panel
        infoPanel = new JPanel();
        editButton = new JButton("Edit Pokémon");
        infoPanel.add(editButton);
        editButton.addActionListener(e -> handleEditButton());
        infoLabel = new JLabel();
        infoLabel.setPreferredSize(new Dimension(300, 370));
        infoLabel.setVerticalAlignment(SwingConstants.TOP);
        infoPanel.add(infoLabel);
        // Add Tabs
        rightPane.add("Basic Info", infoPanel);
        // Add Pane
        main.add(rightPane);
    }

    /**
     * Replaces infoPanel with editPanel.
     * <p>
     * MODIFIES: this
     */
    private void handleEditButton() {
        rightPane.remove(0);
        rightPane.add(editPanel, 0);
        rightPane.setTitleAt(0, "Edit Pokémon");
    }

    /**
     * Sets up and adds addMove, removeMove, movesList to editPanel.
     * <p>
     * MODIFIES: this
     */
    private void addMovesEditor() {
        addMove = new JButton("Add Move");
        addMove.addActionListener(e -> handleAddMove());
        removeMove = new JButton("Remove Move");
        removeMove.addActionListener(e -> handleRemoveMove());
        movesModel = new DefaultListModel<>();
        movesList = new JList<>(movesModel);
        movesList.setFixedCellWidth(300);
        movesList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        editPanel.add(addMove);
        editPanel.add(removeMove);
        editPanel.add(movesList);
    }

    /**
     * Sets up editPanel.
     * <p>
     * MODIFIES: this
     */
    private void editPanel() {
        editPanel = new JPanel();
        doneEditing = new JButton("Done");
        doneEditing.addActionListener(e -> handleDoneEditing());
        JPanel namePanel = new JPanel();
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        nameField.getDocument().addDocumentListener(nameFieldListener);
        typesPanel = new JPanel(new GridLayout(6, 3));
        for (model.Type t : model.Type.values()) {
            JCheckBox checkBox = new JCheckBox(t.name());
            checkBox.addActionListener(e -> handleCheckType());
            typesPanel.add(checkBox);
        }
        editPanel.add(doneEditing);
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        editPanel.add(namePanel);
        editPanel.add(typesPanel);
        addMovesEditor();
    }

    /**
     * Removes a move from both movesModel and the selected Pokemon.
     * <p>
     * MODIFIES: this
     */
    private void handleRemoveMove() {
        getSelectedPokemon().getMoves().remove(movesList.getSelectedIndex());
        movesModel.remove(movesList.getSelectedIndex());
    }

    /**
     * Replaces editPanel with infoPanel.
     * <p>
     * MODIFIES: this
     */
    private void handleDoneEditing() {
        rightPane.remove(0);
        rightPane.add(infoPanel, 0);
        rightPane.setTitleAt(0, "Basic Info");
        setInfoLabel();
    }

    /**
     * Sets the name of the selected Pokemon and repaints boxList.
     * <p>
     * MODIFIES: this
     */
    private void handleNameChange() {
        getSelectedPokemon().setName(nameField.getText());
        boxList.repaint();
    }

    /**
     * Shows a series of dialogs to construct a new move.
     * And adds it to the selected Pokemon and movesModel.
     * <p>
     * MODIFIES: this
     */
    private void handleAddMove() {
        // Get Name
        String name = JOptionPane.showInputDialog(
                null, "What is the name of this move?",
                "New Move", JOptionPane.OK_CANCEL_OPTION
        );
        if (name == null) {
            return;
        }
        // Get Type
        model.Type type = getMoveType();
        if (type == null) {
            return;
        }
        // Get isStatus
        int isStatus = JOptionPane.showConfirmDialog(
                null, "Is this move an attacking move?",
                "New Move", JOptionPane.YES_NO_CANCEL_OPTION
        );
        if (isStatus == JOptionPane.CANCEL_OPTION) {
            return;
        }
        // Add Move
        getSelectedPokemon().getMoves().add(new Move(name, type, (isStatus == 0) ? false : true));
        reloadMoves();
        // Disable addMove
        limitNumberOfMoves();
    }

    /**
     * Disables the addMove if there are four moves
     * <p>
     * MODIFIES: this
     */
    private void limitNumberOfMoves() {
        if (movesModel.size() == 4) {
            addMove.setEnabled(false);
        }
    }

    /**
     * Shows a dialog to get a Type for constructing a move and returns it.
     *
     * @return the Type gotten from the user
     */
    private model.Type getMoveType() {
        JPanel typePanel = new JPanel();
        JLabel typeLabel = new JLabel("What is the type of your move?");
        JComboBox<model.Type> typeBox = new JComboBox<>(model.Type.values());
        typePanel.add(typeLabel);
        typePanel.add(typeBox);
        int result = JOptionPane.showConfirmDialog(
                null, typePanel,
                "New Move", JOptionPane.OK_CANCEL_OPTION
        );
        if (result == JOptionPane.CANCEL_OPTION) {
            return null;
        }
        return (model.Type) typeBox.getSelectedItem();
    }

    /**
     * Disables unselected checkboxes when two checkboxes are selected.
     * Sets the types of the selected Pokemon and repaints boxList.
     * <p>
     * MODIFIES: this
     */
    private void handleCheckType() {
        int count = 0;
        List<model.Type> types = new ArrayList<>();
        for (Component checkBox : typesPanel.getComponents()) {
            if (((JCheckBox) checkBox).isSelected()) {
                // Sets the Pokemon to the selected types
                types.add(model.Type.fromSafeString(((JCheckBox) checkBox).getText()));
                count += 1;
            }
        }
        for (Component checkBox : typesPanel.getComponents()) {
            if (count == 2 && !((JCheckBox) checkBox).isSelected()) {
                checkBox.setEnabled(false);
            } else {
                checkBox.setEnabled(true);
            }
        }
        getSelectedPokemon().setTypes(types);
        boxList.repaint();
    }

    /**
     * Removes a pokemon by asking the user to confirm.
     * <p>
     * MODIFIES: this
     */
    private void handleRemovePokemon() {
        int result = JOptionPane.showConfirmDialog(
                null, "Are you sure you want to remove this Pokémon?",
                "Confirm Removal", JOptionPane.YES_NO_OPTION
        );
        if (result == JOptionPane.YES_OPTION) {
            boxModel.removeElementAt(boxList.getSelectedIndex());
        }
    }

    /**
     * Loads from ./data/autosave.json. Prompt user if failed.
     * <p>
     * MODIFIES: this
     */
    private void loadAutosave() {
        jar = new Jar();
        try {
            new JsonFile("./data/autosave.json").loadFileToJar(jar);
            reloadBox();
        } catch (IOException | JSONException | InvalidJarException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "PokéJar cannot be opened due to a corrupted autosave file!",
                    "Autosave Corrupted",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * Reloads movesModel using moves from the selected Pokemon.
     * <p>
     * MODIFIES: this
     */
    private void reloadMoves() {
        movesModel.clear();
        for (Move m : getSelectedPokemon().getMoves()) {
            movesModel.addElement(m);
        }
    }

    /**
     * Gets the selected Pokemon from boxModel.
     *
     * @return the Pokemon selected in boxList
     */
    private Pokemon getSelectedPokemon() {
        return boxModel.get(boxList.getSelectedIndex());
    }

    /**
     * Reloads boxModel from jar.
     * Creates a new Pokemon if box is empty.
     * <p>
     * MODIFIES: this
     */
    private void reloadBox() {
        boxModel.clear();
        for (Pokemon p : jar.getBox()) {
            boxModel.addElement(p);
        }
        if (boxModel.isEmpty()) {
            boxModel.addElement(new Pokemon());
        }
        boxList.setSelectedIndex(0);
    }

    /**
     * Prompts the user for a file and loads that file to the app.
     * <p>
     * MODIFIES: this
     */
    private void handleLoad() {
        JFileChooser fileChooser = new JFileChooser("./data/");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON File (*.json)", "json"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                new JsonFile(fileChooser.getSelectedFile().getAbsolutePath()).loadFileToJar(jar);
                reloadBox();
                JOptionPane.showMessageDialog(
                        null, "File loaded successfully!",
                        "File Loaded", JOptionPane.INFORMATION_MESSAGE
                );
            } catch (IOException | JSONException | InvalidJarException ex) {
                JOptionPane.showMessageDialog(
                        null, "This file cannot be loaded by PokéJar!",
                        "Load Error", JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Prepares save by setting jar's box to the current boxModel.
     * Sets teams to empty list because team management is not implemented and will cause jar to be invalid if saved.
     * <p>
     * MODIFIES: this
     */
    private void prepareSave() {
        Box newBox = new Box();
        for (int i = 0; i < boxModel.size(); i++) {
            newBox.add(boxModel.get(i));
        }
        // Sets box to boxModel
        jar.setBox(newBox);
        // Clears teams because team management is not implemented and will cause jar to be invalid
        jar.setTeams(new TeamList());
    }

    /**
     * Prompts the user for path and saves the current app state.
     * <p>
     * MODIFIES: this
     */
    private void handleSave() {
        prepareSave();
        JFileChooser fileChooser = new JFileChooser("./data/");
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".json")) {
                filePath += ".json";
            }
            try {
                new JsonFile(filePath).saveJarToFile(jar);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Save failed!", "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
