package dungeonmania.entities.staticEntities;

import dungeonmania.util.Position;

public class Wall extends StaticEntity {
    public Wall(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        setPlayerStandable(false);
    }
}
