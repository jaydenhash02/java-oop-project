package dungeonmania.entities.Items.collectableEntities.rareCollectableEntities;

import dungeonmania.entities.Items.OffensiveEquipment;
import dungeonmania.entities.movingEntities.Assassin;
import dungeonmania.entities.movingEntities.Hydra;
import dungeonmania.entities.movingEntities.Mob;
import dungeonmania.util.Position;

public class Anduril extends RareCollectableEntity implements OffensiveEquipment {
    private int durability = 10;

    public Anduril(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    public Anduril(String id, String type, Position position, boolean isInteractable, int durability) {
        super(id, type, position, isInteractable);
        this.durability = durability;
    }

    @Override
    public double damageIncreaser(Mob enemy) {
        // triple damage against bosses
        if (enemy instanceof Assassin || enemy instanceof Hydra) {
            return 3.0;
        } 

        // otherwise does double dmg
        return 2.0;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public void decreaseDurability() {
        this.durability--;
    }

    public void suppressHydra(Hydra hydra) {
        hydra.setCanRegrowHead(false);
    }
    
}
