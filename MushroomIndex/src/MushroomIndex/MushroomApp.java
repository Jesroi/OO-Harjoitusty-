package MushroomIndex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MushroomApp extends JFrame {
    private MushroomManager manager;
    private DefaultListModel<String> listModel;
    private JList<String> mushroomList;
    private JTextField nameField;
    private JComboBox<String> typeComboBox;

    public MushroomApp(MushroomManager manager) {
    	this.manager = manager;
        listModel = new DefaultListModel<>();
        mushroomList = new JList<>(listModel);
        nameField = new JTextField(15);
        typeComboBox = new JComboBox<>(new String[] {"Gourmet", "Poisonous"});

        // Set up the frame
        setTitle("Mushroom Manager");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up layout
        setLayout(new BorderLayout());

        // List of mushrooms
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JScrollPane(mushroomList), BorderLayout.CENTER);
        add(topPanel, BorderLayout.CENTER);

        // Panel for adding mushrooms
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(typeComboBox);
        add(inputPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Mushroom");
        JButton removeButton = new JButton("Remove Mushroom");
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");

        addButton.addActionListener(e -> addMushroom());
        removeButton.addActionListener(e -> removeMushroom());
        undoButton.addActionListener(e -> undoAction());
        redoButton.addActionListener(e -> redoAction());

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to add mushroom
    private void addMushroom() {
        String name = nameField.getText().trim();
        String type = (String) typeComboBox.getSelectedItem();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a mushroom name.");
            return;
        }

        Mushrooms mushroom = new Mushroom(name, type); 
        manager.addMushroom(mushroom); // Pass the Mushroom object to the manager
        updateMushroomList();
        nameField.setText(""); // Clear the text field after adding
    }

    // Method to remove mushroom
    private void removeMushroom() {
        String name = mushroomList.getSelectedValue();
        if (name == null) {
            JOptionPane.showMessageDialog(this, "Please select a mushroom to remove.");
            return;
        }

        manager.removeMushroom(name.split(" ")[0]);
        updateMushroomList();
    }

    // Undo the last action
    private void undoAction() {
        manager.undo();
        updateMushroomList();
    }

    // Redo the last undone action
    private void redoAction() {
        manager.redo();
        updateMushroomList();
    }

    // Update the JList with the latest mushrooms
    private void updateMushroomList() {
        List<Mushrooms> mushrooms = manager.getMushrooms();
        listModel.clear();
        for (Mushrooms mushroom : mushrooms) {
            listModel.addElement(mushroom.toString());
        }
    }

    public static void main(String[] args) {
        // Initialize the MushroomManager and load mushrooms
        MushroomManager manager = new MushroomManager();
        manager.loadMushroomsFromFile(); // Load mushrooms from file or initialize as needed
        
        // Make sure GUI runs on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Pass the manager instance when creating MushroomApp
            MushroomApp app = new MushroomApp(manager); 
            app.setVisible(true);  // Make the GUI visible
        });
    }
}
