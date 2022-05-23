package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Items.collectableEntities.Armour;

public interface CanWearArmour {
    public boolean hasArmour();

    public Armour getArmour();

    public void setArmour(Armour armour);
}
