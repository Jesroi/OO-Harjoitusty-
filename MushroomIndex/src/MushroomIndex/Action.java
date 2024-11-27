package MushroomIndex;

public class Action {
    public enum ActionType {
        ADD, REMOVE
    }

    private ActionType type;
    private Mushrooms mushroom;

    public Action(ActionType type, Mushrooms mushroom) {
        this.type = type;
        this.mushroom = mushroom;
    }

    public ActionType getType() {
        return type;
    }

    public Mushrooms getMushroom() {
        return mushroom;
    }
}