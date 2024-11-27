package MushroomIndex;

public class Mushroom extends Mushrooms {
    // Constructor that calls the super class constructor
    public Mushroom(String name, String type) {
        super(name, type);  // Call the Mushrooms constructor with name and type
    }

    @Override
    public void interact() {
        // Implementation of interaction logic
        System.out.println("Interacting with " + getName());
    }
}

  