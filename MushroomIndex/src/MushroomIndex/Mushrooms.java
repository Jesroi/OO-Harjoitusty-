package MushroomIndex;
import java.io.Serializable;

public abstract class Mushrooms implements Serializable {                  
    private static final long serialVersionUID = 1L;
    private String name;

    public Mushrooms(String name) {
        this.name = name;
    }

                                                     //** Getter for name */
    public String getName() {
        return name;
    }

                                           /** Abstract method for interact*/
    public abstract void interact();

    @Override
    public String toString() {
        return name;
    }
}