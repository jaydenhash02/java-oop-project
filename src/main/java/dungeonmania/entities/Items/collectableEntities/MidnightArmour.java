package dungeonmania.entities.Items.collectableEntities;

import dungeonmania.entities.Items.DefensiveEquipment;
import dungeonmania.entities.Items.OffensiveEquipment;
import dungeonmania.entities.Items.buildableEntities.BuildableEntity;
import dungeonmania.entities.movingEntities.Mob;
import dungeonmania.util.Position;

public class MidnightArmour extends BuildableEntity implements OffensiveEquipment, DefensiveEquipment {
    private int durability;

    public MidnightArmour(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
        this.durability = 30;
    }

    public MidnightArmour(String id, String type, Position position, boolean isInteractable, int durability) {
        super(id, type, position, isInteractable);
        this.durability = durability;
    }
    
    @Override
    public double damageIncreaser(Mob enemy) {
        return 1.6;
    }

    @Override
    public double damageReducer(Mob enemy) {
        return 0.3;
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
