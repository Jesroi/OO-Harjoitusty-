package MushroomIndex;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {                                
 
        MushroomManager manager = new MushroomManager();
        manager.loadMushroomsFromFile();

        SwingUtilities.invokeLater(new Runnable() {                            
            @Override
            public void run() {                                               
                MushroomApp app = new MushroomApp(manager); 
                app.setVisible(true);                                         
            }
        });
    }
}


