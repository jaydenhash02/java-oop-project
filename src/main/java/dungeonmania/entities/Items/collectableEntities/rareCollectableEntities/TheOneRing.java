package dungeonmania.entities.Items.collectableEntities.rareCollectableEntities;

import dungeonmania.entities.movingEntities.Player;
import dungeonmania.util.Position;

public class TheOneRing extends RareCollectableEntity {
    public static int luckBound = 20;


    public TheOneRing(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }


    public void revivePlayer(Player player) {
        player.setHealth(100);
        player.getInventory().remove(this);
    }
    
}
