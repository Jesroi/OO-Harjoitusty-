package MushroomIndex;
import java.io.Serializable;

public class Toxicity extends Mushrooms implements Serializable {
    private static final long serialVersionUID = 1L;                     //* serializable että tallenus toimii*/
    private String toxicityLevel;

    public Toxicity(String name, String toxicityLevel) {                     //* edibility class*/
        super(name);  // Correctly calls the Mushrooms constructor
        this.toxicityLevel = toxicityLevel;
    }

    public String getToxicityLevel() {                                    
        return toxicityLevel;                                         /**toxicity level ja setteri toxicity asettamiseen*/
    }
    public void setToxicityLevel(String toxicityLevel) {
        this.toxicityLevel = toxicityLevel;
    }

    @Override
    public String toString() {
        return "Toxic Mushroom: " + getName() + " (Toxicity: " + toxicityLevel + ")";
    }

    @Override
    public void interact() {
        System.out.println("Stay away from " + getName() + "! Toxicity: " + toxicityLevel);
    }
}