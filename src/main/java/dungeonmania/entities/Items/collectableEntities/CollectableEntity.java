package dungeonmania.entities.Items.collectableEntities;

import dungeonmania.entities.Items.PortableEntity;
import dungeonmania.util.Position;

public abstract class CollectableEntity extends PortableEntity {
    
    public CollectableEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

}
