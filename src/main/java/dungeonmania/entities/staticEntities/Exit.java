package dungeonmania.entities.staticEntities;

import dungeonmania.util.Position;

public class Exit extends StaticEntity {
    public Exit(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        setPlayerStandable(true);
    }
}
