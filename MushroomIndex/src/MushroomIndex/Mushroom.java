package MushroomIndex;

import java.io.Serializable;

public class Mushroom extends Mushrooms implements Serializable {
    // Constructor that calls the super class constructor
    public Mushroom(String name, String type) {
        super(name);  // Call the Mushrooms constructor with name and type
    }

    @Override
    public void interact() {
        // Implementation of interaction logic
        System.out.println("Interacting with " + getName());
    }
}

  