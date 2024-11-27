package MushroomIndex;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MushroomManager {
    private List<Mushrooms> mushrooms;

    public MushroomManager() {
        mushrooms = new ArrayList<>();
    }

    public void addMushroom(Mushrooms mushroom) {
        mushrooms.add(mushroom);
    }

    public void removeMushroom(String name) {
        boolean removed = mushrooms.removeIf(m -> m.getName().equalsIgnoreCase(name));
        if (removed) {
            System.out.println("Mushroom \"" + name + "\" removed successfully.");
            saveMushroomsToFile();
        } else {
            System.out.println("Mushroom \"" + name + "\" not found.");
        }
    }

    public void listMushrooms() {
        if (mushrooms.isEmpty()) {
            System.out.println("No mushrooms in the list.");
        } else {
            mushrooms.forEach(m -> System.out.println(m.getName()));
        }
    }

    public void saveMushroomsToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("mushrooms.dat"))) {
            out.writeObject(mushrooms);
            System.out.println("Mushrooms saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving mushrooms: " + e.getMessage());
        }
    }

    public void loadMushroomsFromFile() {
        File file = new File("mushrooms.dat");
        if (!file.exists()) {
            System.out.println("No previous data found. Starting fresh.");
            mushrooms = new ArrayList<>();
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            mushrooms = (List<Mushrooms>) in.readObject();
            System.out.println("Mushrooms loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading mushrooms: " + e.getMessage());
            mushrooms = new ArrayList<>();  // Start fresh if error occurs
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Class not found. " + e.getMessage());
            mushrooms = new ArrayList<>();
        }
    }

    public List<Mushrooms> getMushrooms() {
        return mushrooms;
    }
}
                                        
        

        

        

