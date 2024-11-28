package MushroomIndex;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Load mushrooms from file when the application starts
        MushroomManager manager = new MushroomManager();
        manager.loadMushroomsFromFile();

        // Ensure GUI runs on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create the main JFrame
                MushroomApp app = new MushroomApp(manager); // Correct constructor
                app.setVisible(true);  // Show the JFrame
            }
        });
    }
}


