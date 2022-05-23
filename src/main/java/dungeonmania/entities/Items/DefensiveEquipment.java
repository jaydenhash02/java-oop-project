package dungeonmania.entities.Items;

import dungeonmania.entities.movingEntities.Mob;

public interface DefensiveEquipment extends Equipment {
    // reduce mob's damage to player 
    public double damageReducer(Mob enemy);
}
