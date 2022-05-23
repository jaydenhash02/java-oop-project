package dungeonmania.entities.Items.collectableEntities;

import dungeonmania.entities.Items.OffensiveEquipment;
import dungeonmania.entities.movingEntities.Mob;
import dungeonmania.util.Position;

public class Sword extends CollectableEntity implements OffensiveEquipment {
    private int durability = 5;

    public Sword(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    public Sword(String id, String type, Position position, boolean isInteractable, int durability) {
        super(id, type, position, isInteractable);
        this.durability = durability;
    }

    @Override
    public double damageIncreaser(Mob enemy) {
        return 1.5;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public void decreaseDurability() {
        this.durability--;
    }

    
}
