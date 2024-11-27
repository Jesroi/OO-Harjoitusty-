package MushroomIndex;

public class Toxicity extends Mushrooms {
    private static final long serialVersionUID = 1L;
    private String toxicityLevel;

    // Modify the constructor to pass 'name' and 'type' to the superclass constructor
    public Toxicity(String name, String toxicityLevel) {
        super(name, "Toxic"); // Here, 'Toxic' is passed as the type
        this.toxicityLevel = toxicityLevel;
    }

    public String getToxicityLevel() {
        return toxicityLevel;
    }

    @Override
    public void interact() {
        System.out.println("Stay away from " + getName() + "! Toxicity: " + toxicityLevel);
    }
}