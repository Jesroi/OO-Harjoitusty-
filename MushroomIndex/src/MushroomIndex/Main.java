package MushroomIndex;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Load mushrooms from file when the application starts
        MushroomManager manager = new MushroomManager();
        manager.loadMushroomsFromFile();

        // Make sure GUI runs on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create the main JFrame for the GUI
                JFrame frame = new JFrame("Mushroom Manager");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);  // Set the size of the window

                // Create the MushroomPanel and pass in the manager to handle mushroom operations
                MushroomApp panel = new MushroomApp(manager);  // This now works

                // Add the panel to the frame
                frame.add(panel);
                frame.setVisible(true);  // Make the frame visible
            }
        });
    }
}


