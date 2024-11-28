package MushroomIndex;
import java.io.Serializable;

public class Edibility extends Mushrooms implements Serializable {
    private static final long serialVersionUID = 1L;               //* serializable että tallenus toimii*/
    private boolean isGourmet;

    public Edibility(String name, boolean isGourmet) {              //* edibility class*/
        super(name);
        this.isGourmet = isGourmet;
    }

    public boolean isGourmet() {
        return isGourmet;
    }

    @Override
    public String toString() {
        return "Edible Mushroom: " + getName() + " (Gourmet: " + (isGourmet ? "Yes" : "No") + ")";
    }

    @Override
    public void interact() {
        System.out.println(getName() + " is edible. Gourmet: " + (isGourmet ? "Yes" : "No"));
    }
}