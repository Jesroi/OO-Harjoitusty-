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
    private JLabel detailLabel;
    private JCheckBox gourmetCheckBox;  // For gourmet
    private JTextField toxicityField;   // For toxicity level

    public MushroomApp(MushroomManager manager) {
        this.manager = manager;
        listModel = new DefaultListModel<>();
        mushroomList = new JList<>(listModel);
        nameField = new JTextField(15);
        typeComboBox = new JComboBox<>(new String[]{"Gourmet", "Poisonous"});

        // Set up the frame
        setTitle("Mushroom Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Mushroom list panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JScrollPane(mushroomList), BorderLayout.CENTER);
        add(topPanel, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(typeComboBox);
        add(inputPanel, BorderLayout.NORTH);
        gourmetCheckBox = new JCheckBox();
        inputPanel.add(gourmetCheckBox);

        inputPanel.add(new JLabel("Toxicity Level:"));
        toxicityField = new JTextField(10);
        inputPanel.add(toxicityField);

        // Initialize detail label
        detailLabel = new JLabel("Select a mushroom to see details.");
        detailLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(detailLabel, BorderLayout.EAST); // Add it to the right side

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Mushroom");
        JButton removeButton = new JButton("Remove Mushroom");
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        JButton saveButton = new JButton("Save to File"); // New Save button

        // Add action listeners
        addButton.addActionListener(e -> addMushroom());
        removeButton.addActionListener(e -> removeMushroom());
        undoButton.addActionListener(e -> undoAction());
        redoButton.addActionListener(e -> redoAction());
        saveButton.addActionListener(e -> saveToFile()); // Save button action

        // Add buttons to panel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
        buttonPanel.add(saveButton); // Add Save button to the panel

        add(buttonPanel, BorderLayout.SOUTH);

        // Setup the detail label listener
        setupDetailLabel();
    }

    private void saveToFile() {
        manager.saveMushroomsToFile(); // Trigger the save operation
    }

    // Method to add mushroom
    private void addMushroom() {
        String name = nameField.getText().trim();
        String type = (String) typeComboBox.getSelectedItem();
        boolean isGourmet = gourmetCheckBox.isSelected();
        String toxicity = toxicityField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a mushroom name.");
            return;
        }

        Mushrooms mushroom;
        
        if (type.equals("Poisonous") && !toxicity.isEmpty()) {
            // Create Toxicity mushroom if Toxicity level is provided
            mushroom = new Toxicity(name, toxicity);
        } else if (type.equals("Gourmet")) {
            // Create Edibility mushroom
            mushroom = new Edibility(name, isGourmet);
        } else {
            JOptionPane.showMessageDialog(this, "Please provide valid input for the selected type.");
            return;
        }

        manager.addMushroom(mushroom); // Add the mushroom to the manager
        updateMushroomList();          // Refresh the list
        clearInputFields();            // Clear the fields after adding
    }

    private void clearInputFields() {
        nameField.setText("");
        gourmetCheckBox.setSelected(false);
        toxicityField.setText("");
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
            app.updateMushroomList();
            app.setVisible(true);  // Make the GUI visible
        });
    }
    private void setupDetailLabel() {
        mushroomList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && mushroomList.getSelectedValue() != null) {
                String selectedName = mushroomList.getSelectedValue();
                Mushrooms selectedMushroom = manager.getMushroomByName(selectedName.split(" ")[0]);

                if (selectedMushroom instanceof Edibility) {
                    Edibility edible = (Edibility) selectedMushroom;
                    detailLabel.setText(
                        "<html>Name: " + edible.getName() + 
                        "<br>Type: Edible" + 
                        "<br>Gourmet: " + (edible.isGourmet() ? "Yes" : "No") + "</html>"
                    );
                } else if (selectedMushroom instanceof Toxicity) {
                    Toxicity toxic = (Toxicity) selectedMushroom;
                    detailLabel.setText(
                        "<html>Name: " + toxic.getName() + 
                        "<br>Type: Toxic" + 
                        "<br>Toxicity Level: " + toxic.getToxicityLevel() + "</html>"
                    );
                } else {
                    detailLabel.setText("Unknown mushroom type.");
                }
                
            }
            
        });
   
    
    }
        
    }
