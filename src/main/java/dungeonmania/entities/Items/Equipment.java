package dungeonmania.entities.Items;

public interface Equipment {
    
    // all equipment have a durability 
    public int getDurability();

    public void decreaseDurability();
}
