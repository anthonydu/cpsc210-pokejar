package ui;

import com.apple.laf.AquaTabbedPaneUI;
import model.Jar;
import model.Pokemon;
import model.Team;
import org.json.JSONException;
import persistence.InvalidJarException;
import persistence.JsonFile;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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
    // File Chooser
    private JFileChooser fileChooser;
    // Option Pane
    private JOptionPane optionPane;
    // Left Pane
    private JTabbedPane leftPane;
    // Box Panel
    private JPanel boxPanel;
    private JButton newPokemon;
    private JButton removePokemon;
    private DefaultListModel<Pokemon> boxModel;
    private JList boxList;
    // Team Panel
    private JPanel teamPanel;
    private JButton newTeam;
    private JButton removeTeam;
    private DefaultListModel<Team> teamModel;
    private JList teamList;
    // Right Pane
    private JTabbedPane rightPane;
    // Basic Info Panel
    private JPanel basicInfo;
    private JLabel basicInfoLabel;
    // Insights Panel
    private JPanel insights;
    private JLabel insightsLabel;

    public PokeJarGUI() {
        initLoad();
        window();
        menuBar();
        mainPanel();
        leftPane();
        rightPane();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new PokeJarGUI();
    }

    private static <E> DefaultListModel<E> listToModel(List<E> list) {
        DefaultListModel<E> model = new DefaultListModel<>();
        for (E e : list) {
            model.addElement(e);
        }
        return model;
    }

    private void window() {
        this.setTitle("PokéJar");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setResizable(false);
    }

    private void menuBar() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        saveButton = new JMenuItem("Save As...");
        //loadButton.addActionListener(e -> handleSave());
        loadButton = new JMenuItem("Open...");
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
        boxList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        newPokemon = new JButton("New Pokémon");
        removePokemon = new JButton("Remove Pokémon");
        removePokemon.addActionListener(e -> handleRemovePokemon());
        boxPanel.add(newPokemon);
        boxPanel.add(removePokemon);
        boxPanel.add(boxList);
        // Team Panel
        teamPanel = new JPanel();
        teamList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        newTeam = new JButton("New Team");
        teamPanel.add(newTeam);
        teamPanel.add(teamList);
        // Add Tabs
        leftPane.add("Box", boxPanel);
        leftPane.add("Teams", teamPanel);
        main.add(leftPane);
    }

    private void rightPane() {
        rightPane = new JTabbedPane();
        rightPane.setUI(new AquaTabbedPaneUI());
        // Basic Info Panel
        basicInfo = new JPanel();
        basicInfoLabel = new JLabel("Basic Info");
        basicInfo.add(basicInfoLabel);
        // Insights Panel
        insights = new JPanel();
        insightsLabel = new JLabel("Insights");
        basicInfo.add(insightsLabel);
        // Add Tabs
        rightPane.add("Basic Info", basicInfo);
        rightPane.add("Insights", insights);
        main.add(rightPane);
    }

    private void handleRemovePokemon() {
        if (boxList.getSelectedIndices().length == 0) {
            return;
        }
        int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this Pokémon?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            for (Object o : boxList.getSelectedValuesList()) {
                boxModel.removeElement(o);
            }
        }
    }

    private void initLoad() {
        jar = new Jar();
        try {
            new JsonFile("./data/autosave.json").loadFileToJar(jar);
            boxModel = listToModel(jar.getBox());
            teamModel = listToModel(jar.getTeams());
            boxList = new JList(boxModel);
            teamList = new JList(teamModel);
        } catch (IOException | JSONException | InvalidJarException ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "PokéJar cannot be opened due to a corrupted autosave file!",
                    "Autosave Corrupted",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void handleLoad() {
        fileChooser = new JFileChooser("./data/");
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            this.load(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void load(String filePath) {
        try {
            new JsonFile(filePath).loadFileToJar(jar);
            boxModel = listToModel(jar.getBox());
            teamModel = listToModel(jar.getTeams());
            boxList.setModel(boxModel);
            teamList.setModel(teamModel);
            JOptionPane.showMessageDialog(null, "File opened successfully!", "File Loaded", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | JSONException | InvalidJarException ex) {
            JOptionPane.showMessageDialog(null, "This file cannot be opened by PokéJar!", "Load Error", 0);
        }
    }
}
