package dungeonmania.entities.Items.buildableEntities;


import dungeonmania.entities.Items.DefensiveEquipment;
import dungeonmania.entities.movingEntities.Mob;
import dungeonmania.util.Position;

public class Shield extends BuildableEntity implements DefensiveEquipment {
    private int durability = 5;

    public Shield(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    public Shield(String id, String type, Position position, boolean isInteractable, int durability) {
        super(id, type, position, isInteractable);
        this.durability = durability;
    }

    @Override
    public double damageReducer(Mob enemy) {
        return 0.4;
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
