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
    private JCheckBox gourmetCheckBox;  
    private JTextField toxicityField;   

    public MushroomApp(MushroomManager manager) {                     /**manager set,listat*/
        this.manager = manager;
        listModel = new DefaultListModel<>();
        mushroomList = new JList<>(listModel);
        nameField = new JTextField(15);
        typeComboBox = new JComboBox<>(new String[]{"Gourmet", "Poisonous"});

        
        setTitle("Mushroom Manager");                                      /** frame*/
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

                                                                        /**lista paneelit*/
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JScrollPane(mushroomList), BorderLayout.CENTER);
        add(topPanel, BorderLayout.CENTER);

        
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

       
        detailLabel = new JLabel("Select a mushroom to see details.");                     /** details label*/
        detailLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(detailLabel, BorderLayout.EAST);                                      /** east*/

        
        JPanel buttonPanel = new JPanel();                        /** buttons */
        JButton addButton = new JButton("Add Mushroom");
        JButton removeButton = new JButton("Remove Mushroom");
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        JButton saveButton = new JButton("Save to File"); /**tallennus*/
        JButton interactButton = new JButton("Interact with Mushroom");
       
        addButton.addActionListener(e -> addMushroom());               /** listenerit*/ 
        removeButton.addActionListener(e -> removeMushroom());
        interactButton.addActionListener(e -> interactWithSelectedMushroom());
        undoButton.addActionListener(e -> undoAction());
        redoButton.addActionListener(e -> redoAction());
        saveButton.addActionListener(e -> saveToFile()); 

      
        buttonPanel.add(addButton);                           /** napit paneeliin*/
        buttonPanel.add(removeButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
        buttonPanel.add(saveButton); 
        buttonPanel.add(interactButton);

        add(buttonPanel, BorderLayout.SOUTH);

        /**stup the detail label listener*/
        setupDetailLabel();
    }
    private void interactWithSelectedMushroom() {
        String selectedName = mushroomList.getSelectedValue();
        if (selectedName == null) {
            JOptionPane.showMessageDialog(this, "Please select a mushroom to interact with.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Mushrooms selectedMushroom = manager.getMushroomByName(selectedName);
        if (selectedMushroom != null) {
            selectedMushroom.interact();                  /** Call  interact/popup */
        } else {
            JOptionPane.showMessageDialog(this, "Mushroom not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void saveToFile() {                       /**quick save to file*/
        manager.saveMushroomsToFile(); 
    }

    private void addMushroom() {                               /** add mush*/
        String name = nameField.getText().trim();
        String type = (String) typeComboBox.getSelectedItem();
        boolean isGourmet = gourmetCheckBox.isSelected();
        String toxicity = toxicityField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a mushroom name.");
            return;
        }

        Mushrooms mushroom;
        
        if (type.equals("Poisonous") && !toxicity.isEmpty()) {        /**toxic tai gourmet tai ei ollenkaan sini*/
            mushroom = new Toxicity(name, toxicity);
        } else if (type.equals("Gourmet")) {
            mushroom = new Edibility(name, isGourmet);
        } else {
            JOptionPane.showMessageDialog(this, "Please provide valid input for the selected type.");
            return;
        }

        manager.addMushroom(mushroom); 
        updateMushroomList();          
        clearInputFields();            
    }

    private void clearInputFields() {                      /**clear*/
        nameField.setText("");
        gourmetCheckBox.setSelected(false);
        toxicityField.setText("");
    }
    private void removeMushroom() {                           /**remove*/
        String name = mushroomList.getSelectedValue();
        if (name == null) {
            JOptionPane.showMessageDialog(this, "Please select a mushroom to remove.");
            return;
        }

        manager.removeMushroom(name.split(" ")[0]);
        updateMushroomList();
    }

    private void undoAction() {                 /** undo*/
        manager.undo();
        updateMushroomList();
    }

    private void redoAction() {                   /**redo*/
        manager.redo();
        updateMushroomList();
    }

    private void updateMushroomList() {              /** update */
        List<Mushrooms> mushrooms = manager.getMushrooms();
        listModel.clear();
        for (Mushrooms mushroom : mushrooms) {
            listModel.addElement(mushroom.getName());  /*ainoastaan nimi listassa*/
        }
    }

    public static void main(String[] args) {
        MushroomManager manager = new MushroomManager();                         /**manager auki*/
        manager.loadMushroomsFromFile();                                      /**lataa filestä*/
        SwingUtilities.invokeLater(() -> {
            MushroomApp app = new MushroomApp(manager); 
            app.updateMushroomList();
            app.setVisible(true);  
        });
    }

    private void setupDetailLabel() {                                 /** yksityiskohdat sivu listaan*/
        mushroomList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && mushroomList.getSelectedValue() != null) {
                String selectedName = mushroomList.getSelectedValue();
                Mushrooms selectedMushroom = manager.getMushroomByName(selectedName);

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
