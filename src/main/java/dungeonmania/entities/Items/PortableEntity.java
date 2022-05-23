package dungeonmania.entities.Items;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;


// this class represents an object that the player can add to their inventory 
public class PortableEntity extends Entity {

    public PortableEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);

    }
    
}
