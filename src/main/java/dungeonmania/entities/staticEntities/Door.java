package dungeonmania.entities.staticEntities;

import dungeonmania.util.Position;

public class Door extends StaticEntity {
    private int keyID;
    private boolean isOpen = false;
    public Door(String id, String type, Position position, boolean isInteractable, int keyID) {
        super(id, type, position, isInteractable);
        this.keyID = keyID;
        setPlayerStandable(false);
    }

    public Door(String id, String type, Position position, boolean isInteractable, boolean isOpen) {
        super(id, type, position, isInteractable);
        if (isOpen) {
            setOpen();
        }
        setPlayerStandable(false);
    }
    /**
     * indicate whether door is open
     * @return
     */
    public boolean isOpen() {
        return isOpen;
    }
    /**
     * Opens the door
     * 
     */
    public void setOpen() {
        this.isOpen = true;
        setType("unlocked_door");
        setInteractable(false);
        setPlayerStandable(true);
    }

    public int getKeyID() {
        return keyID;
    }
}
