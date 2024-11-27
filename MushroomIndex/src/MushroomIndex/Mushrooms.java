package MushroomIndex;
import java.io.Serializable;

public abstract class Mushrooms implements Serializable {	
	    private static final long serialVersionUID = 1L;  // Ensure backward compatibility
	    private String name;

	    public Mushrooms(String name) {
	        this.name = name;
	    }

	    public String getName() {
	        return name;
	    }

	    public abstract void interact();
	}
