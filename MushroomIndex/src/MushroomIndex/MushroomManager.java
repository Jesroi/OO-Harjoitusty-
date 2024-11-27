package MushroomIndex;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class MushroomManager {
    private List<Mushrooms> mushrooms;
    private Stack<Action> undoStack;
    private Stack<Action> redoStack;

    public MushroomManager() {
        mushrooms = new ArrayList<>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public List<Mushrooms> getMushrooms() {
        return mushrooms;
    }

    public void addMushroom(Mushrooms mushroom) {
        mushrooms.add(mushroom);
        undoStack.push(new Action(Action.ActionType.ADD, mushroom)); // Push the action
        redoStack.clear(); // Clear redo stack after new action
        System.out.println("Mushroom added: " + mushroom.getName());
    }

    public void removeMushroom(String name) {
        Mushrooms mushroom = mushrooms.stream()
                .filter(m -> m.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);

        if (mushroom != null) {
            mushrooms.remove(mushroom);
            undoStack.push(new Action(Action.ActionType.REMOVE, mushroom)); // Push the action
            redoStack.clear(); // Clear redo stack after new action
            System.out.println("Mushroom removed: " + mushroom.getName());
        } else {
            System.out.println("Mushroom \"" + name + "\" not found.");
        }
    }

    // Undo action (move action from undo stack to redo stack)
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("No actions to undo.");
            return;
        }

        Action lastAction = undoStack.pop();
        redoStack.push(lastAction); // Push action to redo stack

        switch (lastAction.getType()) {
            case ADD -> mushrooms.remove(lastAction.getMushroom()); // Undo add (remove the mushroom)
            case REMOVE -> mushrooms.add(lastAction.getMushroom()); // Undo remove (add the mushroom back)
        }

        System.out.println("Undo performed: " + lastAction.getType());
    }

    // Redo action (move action from redo stack to undo stack)
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("No actions to redo.");
            return;
        }

        Action lastUndone = redoStack.pop();
        undoStack.push(lastUndone); // Push action to undo stack

        switch (lastUndone.getType()) {
            case ADD -> mushrooms.add(lastUndone.getMushroom()); // Redo add (add the mushroom)
            case REMOVE -> mushrooms.remove(lastUndone.getMushroom()); // Redo remove (remove the mushroom)
        }

        System.out.println("Redo performed: " + lastUndone.getType());
    }


    public void listMushrooms() {
        if (mushrooms.isEmpty()) {
            System.out.println("No mushrooms in the list.");
        } else {
            mushrooms.forEach(m -> System.out.println(m.getName()));
        }
        List<Mushrooms> sortedMushrooms = mushrooms.stream()
                .sorted(Comparator.comparing(Mushrooms::getName))
                .collect(Collectors.toList());

        // Separate into poisonous and gourmet mushrooms
        List<Mushrooms> poisonous = sortedMushrooms.stream()
                .filter(m -> m instanceof Toxicity)
                .collect(Collectors.toList());

        List<Mushrooms> gourmet = sortedMushrooms.stream()
                .filter(m -> m instanceof Edibility && ((Edibility) m).isGourmet())
                .collect(Collectors.toList());

        System.out.println("\nPoisonous Mushrooms:");
        if (poisonous.isEmpty()) {
            System.out.println("  None");
        } else {
            poisonous.forEach(m -> System.out.println("  - " + m.getName()));
        }

        System.out.println("\nGourmet Mushrooms:");
        if (gourmet.isEmpty()) {
            System.out.println("  None");
        } else {
            gourmet.forEach(m -> System.out.println("  - " + m.getName()));
        }

        System.out.println("\nOther Mushrooms:");
        sortedMushrooms.stream()
                .filter(m -> !(poisonous.contains(m) || gourmet.contains(m)))
                .forEach(m -> System.out.println("  - " + m.getName()));
    }
    public void interactWithMushroom(String name) {
        Mushrooms mushroom = mushrooms.stream()
                .filter(m -> m.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
        if (mushroom != null) {
            mushroom.interact();  
        } else {
            System.out.println("Mushroom \"" + name + "\" not found.");
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
            mushrooms = new ArrayList<>();  
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Class not found. " + e.getMessage());
            mushrooms = new ArrayList<>();
        }
    }

    
   
}

                                        
        

        

        

