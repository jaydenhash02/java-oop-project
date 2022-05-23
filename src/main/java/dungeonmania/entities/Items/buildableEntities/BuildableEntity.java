package dungeonmania.entities.Items.buildableEntities;

import dungeonmania.entities.Items.PortableEntity;
import dungeonmania.util.Position;

public abstract class BuildableEntity extends PortableEntity {
    public BuildableEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }
}
