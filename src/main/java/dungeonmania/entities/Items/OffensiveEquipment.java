package dungeonmania.entities.Items;

import dungeonmania.entities.movingEntities.Mob;

public interface OffensiveEquipment extends Equipment {
    // increase player's damage to mob
    public double damageIncreaser(Mob enemy);
}
