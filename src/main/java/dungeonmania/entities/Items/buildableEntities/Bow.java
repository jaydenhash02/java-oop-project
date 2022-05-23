package dungeonmania.entities.Items.buildableEntities;


import dungeonmania.entities.Items.OffensiveEquipment;
import dungeonmania.entities.movingEntities.Mob;
import dungeonmania.util.Position;

public class Bow extends BuildableEntity implements OffensiveEquipment {
    private int durability = 5;

    public Bow(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    public Bow(String id, String type, Position position, boolean isInteractable, int durability) {
        super(id, type, position, isInteractable);
        this.durability = durability;
    }


    @Override
    public double damageIncreaser(Mob enemy) {
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
}
