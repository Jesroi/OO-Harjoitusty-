package MushroomIndex;

import java.io.Serializable;

public class Mushroom extends Mushrooms implements Serializable {                /** mushroom class*/
    public Mushroom(String name, String type) {
        super(name);                                 
    }

    @Override
    public void interact() {                                                 /** interact */
        System.out.println("Interacting with " + getName());
    }
}

  