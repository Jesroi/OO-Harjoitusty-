package MushroomIndex;

public class Toxicity extends Mushrooms {
    private static final long serialVersionUID = 1L;
    private String toxicityLevel;

    public Toxicity(String name, String toxicityLevel) {
        super(name);
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

