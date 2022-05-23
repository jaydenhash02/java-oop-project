package dungeonmania.entities.Items.collectableEntities;

import dungeonmania.entities.Items.DefensiveEquipment;
import dungeonmania.entities.movingEntities.Mob;
import dungeonmania.util.Position;

public class Armour extends CollectableEntity implements DefensiveEquipment {
    private int durability = 5;

    public Armour(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }
        
    public Armour(String id, String type, Position position, boolean isInteractable, int durability) {
        super(id, type, position, isInteractable);
        this.durability = durability;
    }

    @Override
    public double damageReducer(Mob enemy) {
        return 0.5;
    }


    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public void decreaseDurability() {
        this.durability--;
    }


    public void setDurability(int durability) {
        this.durability = durability;
    }
    
}
