package dungeonmania.entities.movingEntities;

import java.util.ArrayList;
import java.util.List;
import dungeonmania.DungeonLayout;
import dungeonmania.entities.*;
import dungeonmania.entities.Items.DefensiveEquipment;
import dungeonmania.entities.Items.Equipment;
import dungeonmania.entities.Items.OffensiveEquipment;
import dungeonmania.entities.Items.PortableEntity;
import dungeonmania.entities.Items.buildableEntities.*;
import dungeonmania.entities.Items.collectableEntities.*;
import dungeonmania.entities.Items.collectableEntities.rareCollectableEntities.*;
import dungeonmania.entities.staticEntities.*;
import dungeonmania.playerStates.*;
import dungeonmania.util.*;


public class Player extends MovingEntity implements Subject {

    // add a id to an item when crafting
    private int uniqueId = 0;

    private boolean invincibilityPotion;

    // represents the state of the player when a invisible/invincible potion is used
    private PlayerState playerState = new NormalState(this);

    // list of items the player holds 
    private List<PortableEntity> inventory = new ArrayList<PortableEntity>();

    // any buildables that can be crafted
    private List<String> potentialBuildables = new ArrayList<String>();

    // a list of all observers 
    private List<Observer> observers = new ArrayList<Observer>();

    // tracks the tick count of the player for spawning mobs
    private int playerTickCount = 0;

    // stores the entry location
    private Position entryLocation;

    // any allied mercenaries the player currently has
    private List<Mercenary> allAllies = new ArrayList<>();


    public Player(String id, String type, Position position, boolean isInteractable, int playerHealth, boolean invincibilityPotion) {
        super(id, type, position, isInteractable);
        this.invincibilityPotion = invincibilityPotion;
        setAttack(15);
        setHealth(playerHealth);
        setEntryLocation(position);
    }

    public Player(String id, String type, Position position, boolean isInteractable, int playerHealth, boolean invincibilityPotion, String state, int tick) {
        super(id, type, position, isInteractable);
        this.invincibilityPotion = invincibilityPotion;
        this.playerTickCount = tick;
        setAttack(15);
        setHealth(playerHealth);
        setEntryLocation(position);
        if (state.equals("normalstate")) {
            setPlayerState(new NormalState(this));
        } else if (state.equals("invinciblestate")) {
            setPlayerState(new InvincibleState(this));
        } else if (state.equals("invisiblestate")) {
            setPlayerState(new InvisibleState(this));
        }
    }

    /**
     * Updates the position of the player given the direction
     * 
     * @param direction
     * @param dungeon
     */
    public void updatePosition(Direction direction, DungeonLayout dungeon) {
        // check battles before player has moved
        checkBattle(dungeon);
        if (isDead()) return;
        int currentX = getPosition().getX();
        int currentY = getPosition().getY();
        // UP
        Position position;
        if (direction.equals(Direction.UP)) {
            position = new Position(currentX, currentY - 1);
        // DOWN
        } else if (direction.equals(Direction.DOWN)) {
            position = new Position(currentX, currentY + 1);
        // LEFT
        } else if (direction.equals(Direction.LEFT)) {
            position = new Position(currentX - 1, currentY);
        // RIGHT 
        } else if (direction.equals(Direction.RIGHT)) {
            position = new Position(currentX + 1, currentY);
        // NONE
        } else {
            return;
        }
        validSetPosition(position, dungeon, direction);
        if (isDead()) return;
        // check any battles after player has moved too
        checkBattle(dungeon);
        
    }
    /**
     * Sets the position if valid
     * 
     * @param position
     * @param dungeon
     * @param direction
     */
    public void validSetPosition(Position position, DungeonLayout dungeon, Direction direction) {
        List<Entity> entityAtPosition = dungeon.entityAtPosition(position);
        boolean valid = true;
        for (Entity e : entityAtPosition) {
            if(e instanceof Door) {
                if (!((Door)e).isOpen()) {
                    valid = openDoor(e, dungeon);
                }
            }

            if (e instanceof Portal) {
                ((Portal)e).setLinkPortals(dungeon, this, direction);
                valid = false;
            }

            if (e instanceof Boulder) {
                if (((Boulder)e).newBoulderPosition(direction, dungeon)) {
                    setPosition(position);
                }
            }
            if (e instanceof PlayerStandable && !((PlayerStandable)e).getPlayerStandable()) {
                return;
            } 

            if (e instanceof PortableEntity) {
                pickupPortable(((PortableEntity)e), dungeon);
            }
        }

        if (valid) setPosition(new Position(position.getX(), position.getY(), 5));
    }
    /**
     * check a battle
     * 
     * @param dungeon
     */
    public void checkBattle(DungeonLayout dungeon) {
        if (playerState instanceof InvisibleState) {
            return;
        }
        List<Entity> entityAtPosition = dungeon.entityAtPosition(this.getPosition());
        for (Entity e : entityAtPosition) {
            if (e instanceof Mob && !allAllies.contains(e)) {
                battleWithEnemy((Mob)e, dungeon);
            }
        }
    }

    // Character Health = Character Health - ((Enemy Health * Enemy Attack Damage) / 10)
    // Enemy Health = Enemy Health - ((Character Health * Character Attack Damage) / 5)
    /**
     * battle with an enemy
     * @param enemy
     * @param dungeon
     * @return
     */
    public void battleWithEnemy(Mob enemy, DungeonLayout dungeon) {
        if (getPlayerState() instanceof InvincibleState) {
            dungeon.getAllEntities().remove(enemy);
            return;
        }

        double[] DMGmodifications = findDamageModification(enemy);
        double damageIncreaseModifier = DMGmodifications[0];
        double damageReductionModifier = DMGmodifications[1];

        // check if any mercenaries are nearby and will take advantage during a battle
        checkRadiusOfMercenaries(dungeon);

        // take damage from enemy
        this.takeDamage(enemy, damageReductionModifier);
        if (this.getHealth() <= 0) {
            // check if the player has the one ring
            // if has the one ring reset health to 100 and keep battling
            if (getOneRing() != null) {
                TheOneRing ring = getOneRing();
                ring.revivePlayer(this);
                battleWithEnemy(enemy, dungeon);
            } else {
                return;
            }
        }

        // do damage to enemy
        enemy.takeDamage(this, damageIncreaseModifier);
        // now let your allies do damage to the enemy too
        for (Mercenary e: allAllies) {
            enemy.takeDamage((Mercenary)e, 1.0);
        }
        if (enemy.getHealth() <= 0) {
            // check if the slain mob can drop any equipment 
            enemy.dropEquipment(this, dungeon);
            dungeon.getAllEntities().remove(enemy);
            return;
        }

        battleWithEnemy(enemy, dungeon);
    }

    /**
     * open a door if player has a sunstone or key
     * @param e
     * @param dungeon
     * @return
     */
    public boolean openDoor(Entity e, DungeonLayout dungeon) {
        // first check if player has a sunstone
        for (PortableEntity ent1 : inventory) {
            if (ent1 instanceof SunStone) {
                ((Door)e).setOpen();
                return true;
            }
        }
        int doorID = ((Door)e).getKeyID();
        // then check if inventory has key
        for (PortableEntity ent: inventory) {
            if (ent instanceof Key && ((Key)ent).getDoorID() == doorID) {
                ((Door)e).setOpen();
                inventory.remove(ent);
                return true;
            }
        }
        return false;
    }
    /**
     * Updates the list of potential buildables
     * 
     */
    public void updatePotentialBuildables(DungeonLayout dungeon) {
        List<String> potential = new ArrayList<String>();

        int woodCounter = 0;
        int arrowCounter = 0;
        int treasureCounter = 0;
        int keyCounter = 0;
        int sunStoneCounter = 0;
        int armourCounter = 0;
        for (PortableEntity e: inventory) {
            if (e instanceof Wood) woodCounter++;
            if (e instanceof Arrow) arrowCounter++;
            if (e instanceof Treasure) treasureCounter++;
            if (e instanceof Key) keyCounter++;
            if (e instanceof SunStone) sunStoneCounter++;
            if (e instanceof Armour) armourCounter++;
        }

        // if a bow can be crafted
        if (woodCounter >= 1 && arrowCounter >= 3) {
            potential.add("bow");
        }

        if (woodCounter >= 2) {
            if (treasureCounter >= 1 || keyCounter >= 1) {
                potential.add("shield");
            }
        }

        if (sunStoneCounter >= 1) {
            if (treasureCounter >= 1 || keyCounter >= 1) {
                if (woodCounter >= 1 || arrowCounter >= 2) {
                    potential.add("sceptre");
                }
            }
        }
        if (sunStoneCounter >= 1 && armourCounter >= 1) {
            int zombieCount = 0;
            for (Entity e: dungeon.getAllEntities()) {
                if (e.getType().equals("zombie_toast")) zombieCount++;
            }
            if (zombieCount < 1) {
                potential.add("midnight_armour");
            }
        }

        this.potentialBuildables = potential;
    }
    /**
     * Craft a bow, shield, sceptre or midnight armour
     * 
     * @param buildable
     * @return
     */
    public BuildableEntity craftBuildable(String buildable, DungeonLayout dungeon) {
        BuildableEntity buildableEntity = null;

        if (buildable.equals("bow")) {
            buildableEntity = buildBow();
        } else if (buildable.equals("shield")) {
            buildableEntity = buildShield();
        }
        else if (buildable.equals("sceptre")) {
            buildableEntity = buildSceptre();
        }
        else if (buildable.equals("midnight_armour")) {
            buildableEntity = buildMidnightArmour(dungeon);
        }

        return buildableEntity;
    }
    /**
     * pick up a portable entity
     * @param ent
     * @param dungeon
     */
    public void pickupPortable(PortableEntity ent, DungeonLayout dungeon) {
        if (ent instanceof Key) {
            for (PortableEntity entity : inventory) {
                if (entity instanceof Key) {
                    return;
                }
            }
        }
        inventory.add(ent);
        dungeon.getAllEntities().remove(ent);
    }
    /**
     * Checks if an item type exists in the players inventory
     * 
     * @param itemId
     * @return
     */
    public boolean checkItemTypeExistsInInventory(String itemId) {
        for (PortableEntity p: inventory) {
            if (p.getId().contains(itemId)) {
                return true;
            }
        }

        return false;
    }   
    /**
     * Uses a given item
     * 
     * @param itemId
     * @param dungeon
     */
    public void useItem(String itemId, DungeonLayout dungeon) {
        if (itemId.contains("bomb")) {
            setDownBomb(itemId, dungeon);
        } else if (itemId.contains("health_potion")) {
            drinkHealthPotion(itemId);
        } else if (itemId.contains("invincibility_potion")) {
            drinkInvincibilityPotion(itemId);
        } else if (itemId.contains("invisibility_potion")) {
            drinkInvisibilityPotion(itemId);
        }
    }

    /**
     * Drink a health potion
     * 
     * @param itemId
     */
    public void drinkHealthPotion(String itemId) {
        this.setHealth(100);
        for (PortableEntity p: inventory) {
            if (p.getId().equals(itemId)) {
                inventory.remove(p);
                break;
            }
        }
    }

    /**
     * Drink an invincibility potion
     * 
     * @param itemId
     */
    public void drinkInvincibilityPotion(String itemId) {
        if (this.invincibilityPotion) {
            playerState.drinkInvincibilityPotion();
        }
        for (PortableEntity p: inventory) {
            if (p.getId().equals(itemId)) {
                inventory.remove(p);
                Notify();
                break;
            }
        }
    }

    /**
     * Drink an invisibility potion
     * 
     * @param itemId
     */
    public void drinkInvisibilityPotion(String itemId) {
        playerState.drinkInvisibilityPotion();
        for (PortableEntity p: inventory) {
            if (p.getId().equals(itemId)) {
                inventory.remove(p);
                Notify();
                break;
            }
        }
    }

    /**
     * Place the bomb in the current position
     * 
     * @param itemId
     * @param dungeon
     */
    public void setDownBomb(String itemId, DungeonLayout dungeon) {
        for (PortableEntity p: inventory) {
            if (p.getId().equals(itemId)) {
                Bomb setDownBomb = new Bomb(p.getId(), p.getType(), this.getPosition(), false);
                inventory.remove(p);
                setDownBomb.setPlayerStandable(false);
                dungeon.getAllEntities().add(setDownBomb);
                break;
            }
        }
        List<Entity> allEntities = dungeon.getAllEntities();
        for (Entity entity : allEntities) {
            if (Position.isAdjacent(this.getPosition(), entity.getPosition()) && entity instanceof FloorSwitch && ((FloorSwitch)entity).getActivated()) {
                ((FloorSwitch)entity).detonateAnyAdjacentBombs(dungeon);
                break;
            }
        }
    }

    /**
     * build a bow
     * @return the bow
     */
    public BuildableEntity buildBow() {
        BuildableEntity buildableEntity = null;
        int woodCounter = 0;
        int arrowCounter = 0;
        List<PortableEntity> usedForCrafting = new ArrayList<PortableEntity>();
        for (PortableEntity e: inventory) {
            if (e instanceof Wood && woodCounter < 1) {
                woodCounter++;
                usedForCrafting.add(e);
            } else if (e instanceof Arrow && arrowCounter < 3) {
                arrowCounter++;
                usedForCrafting.add(e);
            }
        }
        // if crafting requirements are met
        if (woodCounter == 1 && arrowCounter == 3) {
            // we can remove instance of wood and arrows from inv now
            inventory.removeAll(usedForCrafting);
            buildableEntity = new Bow("bow" + uniqueId, "bow", null, true);
            inventory.add(buildableEntity);
            uniqueId++;
        }

        return buildableEntity;
    }
    /**
     * build a shield
     * @return the shield
     */
    public BuildableEntity buildShield() {
        BuildableEntity buildableEntity = null;
        int woodCounter = 0;
        int treasureCounter = 0;
        int keyCounter = 0;
        List<PortableEntity> usedForCrafting = new ArrayList<PortableEntity>();
        for (PortableEntity e: inventory) {
            if (e instanceof Wood && woodCounter < 2) {
                woodCounter++;
                usedForCrafting.add(e);
            } else if (e instanceof Treasure && treasureCounter < 1) {
                treasureCounter++;
                usedForCrafting.add(e);
            }
        }
        if (treasureCounter == 0) {
            for (PortableEntity ent : inventory) {
                if (ent instanceof Key && keyCounter < 1) {
                    keyCounter++;
                    usedForCrafting.add(ent);
                }
            }
        }
        if ((woodCounter == 2 && treasureCounter == 1)
            || (woodCounter == 2 && keyCounter == 1)) {
            inventory.removeAll(usedForCrafting);
            buildableEntity = new Shield("shield" + uniqueId, "shield", null, true);
            inventory.add(buildableEntity);
            uniqueId++;
        }

        return buildableEntity;
    }
    /**
     * build a midnight armour
     * @param dungeon
     * @return the midnight armour
     */
    public BuildableEntity buildMidnightArmour(DungeonLayout dungeon) {
        // can't build if there are zombies
        for (Entity e: dungeon.getAllEntities()) {
            if (e instanceof ZombieToast) {
                return null;
            }
        }

        BuildableEntity buildableEntity = null;
        int sunStoneCounter = 0;
        int ArmourCounter = 0;
        List<PortableEntity> usedForCrafting = new ArrayList<PortableEntity>();
        for (PortableEntity e : inventory) {
            if (e instanceof SunStone && sunStoneCounter < 1) {
                sunStoneCounter++;
                usedForCrafting.add(e);
            }
            else if (e instanceof Armour && ArmourCounter < 1) {
                ArmourCounter++;
                usedForCrafting.add(e);
            }
        }
        if (sunStoneCounter == 1 && ArmourCounter == 1) {
            inventory.removeAll(usedForCrafting);
            buildableEntity = new MidnightArmour("midnight_armour" + uniqueId, "midnight_armour", null, true);
            inventory.add(buildableEntity);
            uniqueId++;
        }
        return buildableEntity;
    }
    /**
     * build sceptre
     * @return the sceptre
     */
    public BuildableEntity buildSceptre() {
        BuildableEntity buildableEntity = null;
        int woodCounter = 0;
        int arrowCounter = 0;
        int keyCounter = 0;
        int treasureCounter = 0;
        int sunStoneCounter = 0;
        List<PortableEntity> usedForCrafting = new ArrayList<PortableEntity>();
        for (PortableEntity e: inventory) {
            if (e instanceof SunStone && sunStoneCounter < 1) {
                sunStoneCounter++;
                usedForCrafting.add(e);
            } else if (e instanceof Wood && woodCounter < 1) {
                woodCounter++;
                usedForCrafting.add(e);
            } else if (e instanceof Treasure && treasureCounter < 1) {
                treasureCounter++;
                usedForCrafting.add(e);
            }
        }
        if (woodCounter == 0) {
            for (PortableEntity e: inventory) {
                if (e instanceof Arrow && arrowCounter < 2) {
                    arrowCounter++;
                    usedForCrafting.add(e);
                }
            }
        }
        if (treasureCounter == 0) {
            for (PortableEntity ent : inventory) {
                if (ent instanceof Key && keyCounter < 1) {
                    keyCounter++;
                    usedForCrafting.add(ent);
                }
            }
        }
        if (sunStoneCounter == 1) {
            if (treasureCounter == 1 || keyCounter == 1) {
                if (woodCounter == 1 || arrowCounter == 2) {
                    inventory.removeAll(usedForCrafting);
                    buildableEntity = new Sceptre("sceptre" + uniqueId, "sceptre", null, true);
                    inventory.add(buildableEntity);
                    uniqueId++;
                }
            }
        }
        return buildableEntity;
    }


    public double[] findDamageModification(Mob enemy) {
        double damageIncreaseModifier = 1.0;
        double damageReductionModifier = 1.0;
        List<PortableEntity> noMoreDurabilityItems = new ArrayList<PortableEntity>();
        for (PortableEntity p: inventory) {
            if (p instanceof Equipment) {

                if (p instanceof OffensiveEquipment) {
                    damageIncreaseModifier *= ((OffensiveEquipment)p).damageIncreaser(enemy);
                } 

                if (p instanceof DefensiveEquipment) {
                    damageReductionModifier *= ((DefensiveEquipment)p).damageReducer(enemy);
                }

                ((Equipment)p).decreaseDurability();
                if (((Equipment)p).getDurability() <= 0) {
                    noMoreDurabilityItems.add(p);
                }
            }
        }

        // remove all items that have no more durability from the inventory
        inventory.removeAll(noMoreDurabilityItems);
        
        return new double[] {damageIncreaseModifier, damageReductionModifier};
    }

    @Override
    public double attackDMG() {
        return (this.getHealth() * this.getAttack()) / 5;
    }

    @Override
    public void takeDamage(MovingEntity e, double dmgModifier) {
        this.setHealth(this.getHealth() - e.attackDMG() * dmgModifier);
    }


    public void checkRadiusOfMercenaries(DungeonLayout dungeon) {
        for (Entity e: dungeon.getAllEntities()) {
            if (e instanceof Mercenary) {
                if (((Mercenary) e).checkBattleRadius(dungeon, this)) {
                    ((Mercenary) e).mobMovement(dungeon);
                }
            }
        }

    }

    private TheOneRing getOneRing() {
        for (PortableEntity p: inventory) {
            if (p instanceof TheOneRing) {
                return ((TheOneRing)p);
            }
        }

        return null;
    }

    public void AddObserver(Observer observer) {
        observers.add(observer);
    }

    public void RemoveObserver(Observer observer) {
        observers.remove(observer);
    }
    /**
     * notify observers
     */
    public void Notify() {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(this);
        }
    }

    public void incrementPlayerTickCount() {
        this.playerTickCount++;
    }

    public int getPlayerTickCount() {
        return playerTickCount;
    }
    /**
     * indicate whether the player is dead
     * @return
     */
    public boolean isDead() {
        return (getHealth() <= 0);
    }

    public PlayerState getPlayerState() {
        return playerState;
    }   


    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public List<PortableEntity> getInventory() {
        return inventory;
    }


    
    public void setInventory(List<PortableEntity> inventory) {
        this.inventory = inventory;
    }

    public List<String> getPotentialBuildables() {
        return potentialBuildables;
    }

    public List<Mercenary> getAllAllies() {
        return allAllies;
    }

    public void setEntryLocation(Position entryLocation) {
        this.entryLocation = entryLocation;
    }

    public Position getEntryLocation() {
        return entryLocation;
    }
    public void removeAlly(Mob mob) {
        this.allAllies.remove(mob);
    }

}
