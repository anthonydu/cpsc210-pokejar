package ui;

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
import java.util.Map;

import static javax.swing.JOptionPane.*;

/**
 * PokeJar App's GUI
 *
 * @author Anthony Du
 */
public class PokeJarGUI extends JFrame {
    // Jar Object
    private model.Jar jar;

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
    private DefaultListModel<model.Pokemon> boxModel;
    private JList boxList;
    // Team Label
    private JLabel teamLabel;

    // Right Pane
    private JTabbedPane rightPane;
    // Basic Info Panel
    private JPanel infoPanel;
    private JLabel infoLabel;
    private JButton editButton;
    // Insight Pane
    private JPanel insightPanel;
    private JLabel insightLabel;
    // Edit Panel
    private JPanel editPanel;
    private JLabel nameLabel;
    private JTextField nameField;
    private JPanel typesPanel;
    private JButton addMove;
    private JButton removeMove;
    private DefaultListModel<model.Move> movesModel;
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
            int canceled = showConfirmDialog(
                    null, "Are you sure you want to close PokéJar?",
                    "Confirm Close", YES_NO_OPTION, WARNING_MESSAGE
            );
            if (canceled == 0 && autosave()) {
                System.exit(0);
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
                int canceled = showConfirmDialog(
                        null, "Autosave failed! Continue to close?",
                        "Autosave Error", YES_NO_OPTION, WARNING_MESSAGE
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
            updateName();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateName();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateName();
        }

        /**
         * Sets the name of the selected Pokemon and repaints boxList.
         * <p>
         * MODIFIES: this
         */
        private void updateName() {
            getSelectedPokemon().setName(nameField.getText());
            boxList.repaint();
        }
    };


    // UI


    /**
     * Constructs PokeJarGUI and sets it to visible.
     * <p>
     * MODIFIES: this
     */
    public PokeJarGUI() {
        this.setTitle("PokéJar");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(800, 600);
        this.setResizable(false);
        this.addWindowListener(windowAdapter);

        setMenuBar();
        addMainPanel();
        addLeftPane(); // added to mainPanel
        addRightPane(); // added to mainPanel
        setupEditPanel(); // hidden initially
        loadAutosave();
        this.setVisible(true);
    }

    /**
     * Sets up and adds menuBar to window.
     * <p>
     * MODIFIES: this
     */
    private void setMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        saveButton = new JMenuItem("Save As...");
        saveButton.addActionListener(e -> handleSave());
        loadButton = new JMenuItem("Load Save...");
        loadButton.addActionListener(e -> loadFile());
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
    private void addMainPanel() {
        main = new JPanel(new GridLayout(1, 2));
        main.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.add(main);
    }

    /**
     * Sets up and adds leftPane to mainPanel.
     * <p>
     * MODIFIES: this
     */
    private void addLeftPane() {
        leftPane = new JTabbedPane();
        // Box Panel
        boxPanel = new JPanel();
        boxModel = new DefaultListModel<>();
        boxList = new JList(boxModel);
        boxList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        addPokemon = new JButton("New Pokémon");
        addPokemon.addActionListener(e -> addPokemon());
        removePokemon = new JButton("Remove Pokémon");
        boxList.addListSelectionListener(e -> onBoxSelectionChange(e));
        boxList.setFixedCellWidth(300);
        boxList.setFixedCellHeight(15);
        boxScrollPane = new JScrollPane(boxList);
        boxScrollPane.setPreferredSize(new Dimension(300, 350));
        boxScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        removePokemon.addActionListener(e -> removePokemon());
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
     * Sets up and adds rightPane to mainPanel.
     * <p>
     * MODIFIES: this
     */
    private void addRightPane() {
        rightPane = new JTabbedPane();
        rightPane.addChangeListener(e -> setInsightLabel());
        // Info Panel
        infoPanel = new JPanel();
        editButton = new JButton("Edit Pokémon");
        editButton.addActionListener(e -> switchInEditPanel());
        infoLabel = new JLabel();
        infoLabel.setPreferredSize(new Dimension(300, 350));
        infoLabel.setVerticalAlignment(SwingConstants.TOP);
        infoPanel.add(editButton);
        infoPanel.add(infoLabel);
        // Insight Panel
        insightPanel = new JPanel();
        insightLabel = new JLabel();
        insightLabel.setPreferredSize(new Dimension(300, 385));
        insightLabel.setVerticalAlignment(SwingConstants.TOP);
        insightPanel.add(insightLabel);
        // Add Tabs
        rightPane.add("Basic Info", infoPanel);
        rightPane.add("Insight", insightPanel);
        // Add Pane
        main.add(rightPane);
    }

    /**
     * Sets up editPanel.
     * <p>
     * MODIFIES: this
     */
    private void setupEditPanel() {
        editPanel = new JPanel();
        doneEditing = new JButton("Done");
        doneEditing.addActionListener(e -> switchInInfoPanel());
        JPanel namePanel = new JPanel();
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        nameField.getDocument().addDocumentListener(nameFieldListener);
        typesPanel = new JPanel(new GridLayout(6, 3));
        for (model.Type t : model.Type.values()) {
            JCheckBox checkBox = new JCheckBox(t.name());
            checkBox.addActionListener(e -> updateTypes());
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
     * Sets up and adds movesEditor to editPanel.
     * <p>
     * MODIFIES: this
     */
    private void addMovesEditor() {
        JPanel movesEditor = new JPanel();
        addMove = new JButton("Add Move");
        addMove.addActionListener(e -> addMove());
        removeMove = new JButton("Remove Move");
        removeMove.addActionListener(e -> removeMove());
        movesModel = new DefaultListModel<>();
        movesList = new JList<>(movesModel);
        movesList.addListSelectionListener(e -> {
            removeMove.setEnabled(true);
            if (movesList.getSelectedIndex() == -1) {
                removeMove.setEnabled(false);
            }
        });
        movesList.setFixedCellWidth(300);
        movesList.setFixedCellHeight(15);
        movesList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        movesEditor.add(addMove);
        movesEditor.add(removeMove);
        movesEditor.add(movesList);
        editPanel.add(movesEditor);
    }


    // Helpers


    /**
     * Gets the selected Pokemon from boxModel.
     *
     * @return the Pokemon selected in boxList
     */
    private model.Pokemon getSelectedPokemon() {
        return boxModel.get(boxList.getSelectedIndex());
    }

    /**
     * Reloads movesEditor using moves from the selected Pokemon.
     * <p>
     * MODIFIES: this
     */
    private void reloadMovesEditor() {
        movesModel.clear();
        for (model.Move m : getSelectedPokemon().getMoves()) {
            movesModel.addElement(m);
        }
        addMove.setEnabled(true);
        removeMove.setEnabled(true);
        if (movesModel.size() == 4) {
            addMove.setEnabled(false);
        }
        removeMove.setEnabled(false);
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
        for (model.Move m : getSelectedPokemon().getMoves()) {
            info += "\n" + m;
        }
        info += "</pre></html>";
        infoLabel.setText(info);
    }

    /**
     * Updates the text of insightLabel
     * <p>
     * MODIFIES: this
     */
    private void setInsightLabel() {
        if (rightPane.getSelectedIndex() != 1) {
            return;
        }
        String insight = "<html><pre>Multiplier Insight:\n";
        insight += model.StringUtil.fixCharCount("Type", 16)
                + model.StringUtil.fixCharCount("Defensive", 12) + "Offensive";
        Map<model.Type, Double> defensiveMap;
        Map<model.Type, Double> offensiveMap;
        defensiveMap = model.Type.defensiveMultipliers(getSelectedPokemon().getTypes());
        offensiveMap = model.Type.offensiveMultipliers(getSelectedPokemon().attackingMoveTypes());
        for (model.Type t : model.Type.values()) {
            insight += "\n" + model.StringUtil.fixCharCount(t.toString(), 16)
                    + model.StringUtil.fixCharCount(defensiveMap.get(t).toString(), 12)
                    + offensiveMap.get(t);
        }
        insight += "</pre><p style=\"font-size: smaller\">";
        insight += "Defensive: multipliers when attacked by a move of a certain type.<br>"
                + "Offensive: multipliers (of this Pokémon's most effective move) "
                + "when attacking a Pokémon of a certain type.";
        insight += "</p></html>";
        insightLabel.setText(insight);
    }

    /**
     * Disables unselected checkboxes when two checkboxes are selected.
     * Sets the types of the selected Pokemon and repaints boxList.
     * <p>
     * MODIFIES: this
     */
    private void updateTypes() {
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
     * Prevents empty box by adding a new Pokemon to boxModel when boxModel is empty.
     * <p>
     * MODIFIES: this
     */
    private void preventEmptyBox() {
        if (boxModel.isEmpty()) {
            boxModel.addElement(new model.Pokemon());
        }
    }

    /**
     * Prevents boxList from deselect by its setting selected index to 0 when boxList is deselected.
     * <p>
     * MODIFIES: this
     */
    private void preventBoxDeselect() {
        if (boxList.getSelectedIndex() == -1) {
            boxList.setSelectedIndex(0);
        }
    }


    // Event handlers


    /**
     * Replaces infoPanel with editPanel and updates editPanel with the currently selected Pokemon.
     * <p>
     * MODIFIES: this
     */
    private void switchInEditPanel() {
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
        updateTypes();
        reloadMovesEditor();
        rightPane.remove(0);
        rightPane.add(editPanel, 0);
        rightPane.setTitleAt(0, "Edit Pokémon");
        rightPane.setSelectedIndex(0);
    }

    /**
     * Replaces editPanel with infoPanel while maintaining selected tab.
     * <p>
     * MODIFIES: this
     */
    private void switchInInfoPanel() {
        setInfoLabel();
        int maintainTabIndex = rightPane.getSelectedIndex();
        rightPane.remove(0);
        rightPane.add(infoPanel, 0);
        rightPane.setTitleAt(0, "Basic Info");
        rightPane.setSelectedIndex(maintainTabIndex);
    }

    /**
     * Removes a move from the selected Pokemon and reloads movesEditor.
     * <p>
     * MODIFIES: this
     */
    private void removeMove() {
        getSelectedPokemon().getMoves().remove(movesList.getSelectedIndex());
        reloadMovesEditor();
    }

    /**
     * Shows a series of dialogs to construct a new move.
     * And adds it to the selected Pokemon and reloads movesEditor.
     * <p>
     * MODIFIES: this
     */
    private void addMove() {
        // Get Name
        String name = showInputDialog(
                null, "What is the name of this move?",
                "New Move", OK_CANCEL_OPTION
        );
        if (name == null) {
            return;
        }
        // Get Type
        model.Type type = askMoveType();
        if (type == null) {
            return;
        }
        // Get isStatus
        int isStatus = showConfirmDialog(
                null, "Is this move an attacking move?",
                "New Move", YES_NO_CANCEL_OPTION
        );
        if (isStatus == CANCEL_OPTION) {
            return;
        }
        // Add Move
        getSelectedPokemon().getMoves().add(new model.Move(name, type, (isStatus == 0) ? false : true));
        reloadMovesEditor();
    }

    /**
     * Shows a dialog to get a Type for constructing a move and returns it.
     *
     * @return the Type gotten from the user
     */
    private model.Type askMoveType() {
        JPanel typePanel = new JPanel();
        JComboBox<model.Type> typeBox = new JComboBox<>(model.Type.values());
        typePanel.add(new JLabel("What is the type of your move?"));
        typePanel.add(typeBox);
        int result = showConfirmDialog(
                null, typePanel,
                "New Move", OK_CANCEL_OPTION
        );
        if (result == CANCEL_OPTION) {
            return null;
        }
        return (model.Type) typeBox.getSelectedItem();
    }

    /**
     * Prevents deselect and sets the rightPane to reflect the newly selected Pokemon.
     * <p>
     * MODIFIES: this
     *
     * @param e the ListSelectionEvent
     */
    private void onBoxSelectionChange(ListSelectionEvent e) {
        // Prevent potential event loop caused by switchInInfoPanel()
        if (e.getValueIsAdjusting()) {
            return;
        }
        preventBoxDeselect();
        if (boxList.getSelectedIndex() != -1) {
            switchInInfoPanel();
            setInsightLabel();
        }
    }

    /**
     * Adds a Pokemon to boxModel and jar.getBox().
     * <p>
     * MODIFIES: this
     */
    private void addPokemon() {
        model.Pokemon newPokemon = new model.Pokemon();
        boxModel.addElement(newPokemon);
        jar.getBox().add(newPokemon);
        boxList.setSelectedIndex(boxModel.size() - 1);
    }

    /**
     * Removes a Pokemon from boxModel and jar.getBox() by asking the user to confirm.
     * <p>
     * MODIFIES: this
     */
    private void removePokemon() {
        int result = showConfirmDialog(
                null, "Are you sure you want to remove this Pokémon?",
                "Confirm Removal", YES_NO_OPTION
        );
        if (result == NO_OPTION) {
            return;
        }
        jar.getBox().remove(boxList.getSelectedIndex());
        boxModel.removeElementAt(boxList.getSelectedIndex());
        preventEmptyBox();
        preventBoxDeselect();
    }


    // IO handlers


    /**
     * Reloads boxModel from jar while preventing empty box, deselect, and sets infoLabel.
     * <p>
     * MODIFIES: this
     */
    private void reloadBox() {
        boxModel.clear();
        for (model.Pokemon p : jar.getBox()) {
            boxModel.addElement(p);
        }
        preventEmptyBox();
        preventBoxDeselect();
        setInfoLabel();
    }

    /**
     * Prompts the user for a file and loads that file to the app.
     * <p>
     * MODIFIES: this
     */
    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser("./data/");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON File (*.json)", "json"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                new JsonFile(fileChooser.getSelectedFile().getAbsolutePath()).loadFileToJar(jar);
                reloadBox();
                showMessageDialog(
                        null, "File loaded successfully!",
                        "File Loaded", INFORMATION_MESSAGE
                );
            } catch (IOException | InvalidJarException ex) {
                showMessageDialog(
                        null, "This file cannot be loaded by PokéJar! " + ex.getMessage(),
                        "Load Error", ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Loads from ./data/autosave.json. Prompt user if failed.
     * <p>
     * MODIFIES: this
     */
    private void loadAutosave() {
        jar = new model.Jar();
        try {
            new JsonFile("./data/autosave.json").loadFileToJar(jar);
            reloadBox();
        } catch (IOException | InvalidJarException ex) {
            showMessageDialog(
                    null,
                    "PokéJar cannot be opened due to a corrupted autosave file! " + ex.getMessage(),
                    "Autosave Corrupted",
                    ERROR_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * Prepares save by setting jar's box to the current boxModel.
     * Sets teams to empty list because team management is not implemented and will cause jar to be invalid if saved.
     * <p>
     * MODIFIES: this
     */
    private void prepareSave() {
        // Clears teams because team management is not implemented and will cause jar to be invalid
        jar.setTeams(new model.TeamList());
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
                showMessageDialog(null, "Save failed! " + ex.getMessage(), "Save Error", ERROR_MESSAGE);
            }
        }
    }
}
