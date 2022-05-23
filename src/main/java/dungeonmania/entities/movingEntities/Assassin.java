package dungeonmania.entities.movingEntities;

import dungeonmania.util.Position;

public class Assassin extends Mercenary {
    
    public Assassin(String id, String type, Position position, boolean isInteractable, int attack) {
        super(id, type, position, isInteractable, attack);
        this.setHealth(60);
        this.setAlly(false);
    }

    public Assassin(String id, String type, Position position, boolean isInteractable, boolean ally, int attack) {
        super(id, type, position, isInteractable, ally, attack);
        this.setAlly(ally);
    }
}