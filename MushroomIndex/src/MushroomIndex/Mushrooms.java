package MushroomIndex;
import java.io.Serializable;

public abstract class Mushrooms implements Serializable {
    private String name;
    private String type;

    // Constructor to initialize the name and type
    public Mushrooms(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    // Abstract method for interacting with mushrooms
    public abstract void interact();
}