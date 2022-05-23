package dungeonmania.entities.Items.collectableEntities;

import dungeonmania.util.Position;

public class Key extends CollectableEntity {
    private int doorID;
    public Key(String id, String type, Position position, boolean isInteractable, int doorID) {
        super(id, type, position, isInteractable);
        this.doorID = doorID;
    }

    public int getDoorID() {
        return doorID;
    }

    public void setDoorID(int doorID) {
        this.doorID = doorID;
    }
}
