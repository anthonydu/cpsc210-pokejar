package ui;

import com.apple.laf.AquaTabbedPaneUI;
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

    public PokeJarGUI() {
        window();
        menuBar();
        mainPanel();
        leftPane();
        rightPane();
        editPanel();
        this.setVisible(true);
    }

    private void window() {
        this.setTitle("PokéJar");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(800, 600);
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int canceled = JOptionPane.showConfirmDialog(null, "Are you sure you want to close PokéJar?", "Confirm Close", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (canceled == 0 && autosave()) {
                    System.exit(2);
                }
            }

            private boolean autosave() {
                prepareSave();
                try {
                    new JsonFile("./data/autosave.json").saveJarToFile(jar);
                    return true;
                } catch (IOException ex) {
                    int canceled = JOptionPane.showConfirmDialog(null, "Autosave failed! Continue to close?", "Autosave Error", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (canceled == 0) {
                        return true;
                    }
                    return false;
                }
            }
        });
    }

    private void menuBar() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
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

    private void mainPanel() {
        main = new JPanel(new GridLayout(1, 2));
        main.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.add(main);
    }

    private void leftPane() {
        leftPane = new JTabbedPane();
        leftPane.setUI(new AquaTabbedPaneUI());
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
        boxList.setFixedCellHeight(15);
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

    private void handleListSelection(ListSelectionEvent e) {
        // Prevent event loop and NullPointerException when calling getSelectedPokemon
        if (e.getValueIsAdjusting() || boxList.getSelectedIndex() == -1) {
            return;
        }
        // Sets nameField to name of the selected Pokemon
        nameField.setText(getSelectedPokemon().getName());
        // Sets checkBoxes to represent the selected Pokemon's type
        for (Component checkBox : typesPanel.getComponents()) {
            model.Type checkBoxType = model.Type.fromString(((JCheckBox) checkBox).getText());
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
        // Sets moves to the selected Pokemon's moves
        reloadMoves();
    }

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

    private void handleAddPokemon() {
        Pokemon newPokemon = new Pokemon();
        boxModel.addElement(newPokemon);
        boxList.setSelectedIndex(boxModel.size() - 1);
    }

    private void rightPane() {
        rightPane = new JTabbedPane();
        rightPane.setUI(new AquaTabbedPaneUI());
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

    private void handleEditButton() {
        rightPane.remove(0);
        rightPane.add(editPanel, 0);
        rightPane.setTitleAt(0, "Edit Pokémon");
    }

    private void editPanel() {
        editPanel = new JPanel();
        doneEditing = new JButton("Done");
        doneEditing.addActionListener(e -> handleDoneEditing());
        JPanel namePanel = new JPanel();
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        nameField.getDocument().addDocumentListener(new DocumentListener() {
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
        });
        typesPanel = new JPanel(new GridLayout(6, 3));
        for (model.Type t : model.Type.values()) {
            JCheckBox checkBox = new JCheckBox(t.name());
            checkBox.addActionListener(e -> handleCheckType());
            typesPanel.add(checkBox);
        }
        addMove = new JButton("Add Move");
        addMove.addActionListener(e -> handleAddMove());
        removeMove = new JButton("Remove Move");
        removeMove.addActionListener(e -> handleRemoveMove());
        movesModel = new DefaultListModel<>();
        movesList = new JList<>(movesModel);
        movesList.setFixedCellWidth(300);
        movesList.setFixedCellHeight(15);
        movesList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        editPanel.add(doneEditing);
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        editPanel.add(namePanel);
        editPanel.add(typesPanel);
        editPanel.add(addMove);
        editPanel.add(removeMove);
        editPanel.add(movesList);
    }

    private void handleRemoveMove() {
        getSelectedPokemon().getMoves().remove(movesList.getSelectedIndex());
        movesModel.remove(movesList.getSelectedIndex());
    }

    private void handleDoneEditing() {
        rightPane.remove(0);
        rightPane.add(infoPanel, 0);
        rightPane.setTitleAt(0, "Basic Info");
        setInfoLabel();
    }

    private void handleNameChange() {
        getSelectedPokemon().setName(nameField.getText());
        boxList.repaint();
    }

    /**
     * Runs when add move button is clicked.
     * Shows a series of dialogs to construct a new move and adds it to movesModel and jar.
     * Disables the add move button if there are four moves.
     *
     * MODIFIES: this
     */
    private void handleAddMove() {
        // Get Name
        String name = JOptionPane.showInputDialog(null, "What is the name of this move?", "New Move", JOptionPane.OK_CANCEL_OPTION);
        if (name == null) {
            return;
        }
        // Get Type
        JPanel typePanel = new JPanel();
        JLabel typeLabel = new JLabel("What is the type of your move?");
        JComboBox<model.Type> typeBox = new JComboBox<>(model.Type.values());
        typePanel.add(typeLabel);
        typePanel.add(typeBox);
        int result = JOptionPane.showConfirmDialog(null, typePanel, "New Move", JOptionPane.OK_CANCEL_OPTION);
        model.Type type = null;
        if (result == JOptionPane.OK_OPTION) {
            type = (model.Type) typeBox.getSelectedItem();
        } else {
            return;
        }
        // Get isStatus
        int isStatus = JOptionPane.showConfirmDialog(null, "Is this move an attacking move?", "New Move", JOptionPane.YES_NO_CANCEL_OPTION);
        if (isStatus == JOptionPane.CANCEL_OPTION) {
            return;
        }
        // Add Move
        Move newMove = new Move(name, type, (isStatus == 0) ? false : true);
        getSelectedPokemon().getMoves().add(newMove);
        reloadMoves();
        // Disable addMove
        if (movesModel.size() == 4) {
            addMove.setEnabled(false);
        }
    }

    /**
     * Runs when checkboxes are clicked on.
     * Disables non-active checkboxes when two checkboxes are selected.
     * Sets the types of the selected Pokemon.
     *
     * MODIFIES: this
     */
    private void handleCheckType() {
        int count = 0;
        List<model.Type> types = new ArrayList<>();
        for (Component checkBox : typesPanel.getComponents()) {
            if (((JCheckBox) checkBox).isSelected()) {
                // Sets the Pokemon to the selected types
                types.add(model.Type.fromString(((JCheckBox) checkBox).getText()));
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

    private void handleRemovePokemon() {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this Pokémon?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            boxModel.removeElementAt(boxList.getSelectedIndex());
        }
    }

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

    private void reloadMoves() {
        movesModel.clear();
        for (Move m : getSelectedPokemon().getMoves()) {
            movesModel.addElement(m);
        }
    }

    private Pokemon getSelectedPokemon() {
        return boxModel.get(boxList.getSelectedIndex());
    }

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

    private void handleLoad() {
        JFileChooser fileChooser = new JFileChooser("./data/");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON File (*.json)", "json"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                new JsonFile(fileChooser.getSelectedFile().getAbsolutePath()).loadFileToJar(jar);
                reloadBox();
                JOptionPane.showMessageDialog(null, "File loaded successfully!", "File Loaded", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | JSONException | InvalidJarException ex) {
                JOptionPane.showMessageDialog(null, "This file cannot be loaded by PokéJar!", "Load Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

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
