package MushroomIndex;
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
        for (Mushrooms mushroom : mushrooms) {
            System.out.println(mushroom.getName() + " (Poisonous: " + 
                (mushroom instanceof Toxicity) + ")");
        }
    }

    public Mushrooms findMushroom(String name) {
        for (Mushrooms m : mushrooms) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public void interactWithMushroom(String name) {
        Mushrooms mushroom = findMushroom(name);
        if (mushroom != null) {
            mushroom.interact();  // Polymorphism ensures correct `interact()` is called
        } else {
            System.out.println("Mushroom not found.");
        }
        }
        public void saveMushroomsToFile() {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("mushrooms.dat"))) {
                out.writeObject(mushrooms);
            } catch (IOException e) {
                System.out.println("Error saving mushrooms: " + e.getMessage());
            }
        }

        public void loadMushroomsFromFile() {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("mushrooms.dat"))) {
                mushrooms = (List<Mushrooms>) in.readObject();
                System.out.println("Loaded mushrooms:");
                mushrooms.forEach(m -> System.out.println(m.getName()));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();  // Debug for more clarity

                   
                    }
                }

            
        }
                                        
        

        

        

