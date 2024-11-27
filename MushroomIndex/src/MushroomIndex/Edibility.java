package MushroomIndex;

public class Edibility extends Mushrooms {
    private static final long serialVersionUID = 1L;
    private boolean isGourmet;

    // Modify the constructor to pass 'name' and 'type' to the superclass constructor
    public Edibility(String name, boolean isGourmet) {
        super(name, "Edible"); // 'Edible' is passed as the type
        this.isGourmet = isGourmet;
    }

    public boolean isGourmet() {
        return isGourmet;
    }

    @Override
    public void interact() {
        System.out.println(getName() + " is edible. Gourmet: " + (isGourmet ? "Yes" : "No"));
    }
}