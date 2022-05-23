package dungeonmania;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Items.collectableEntities.*;
import dungeonmania.entities.Items.collectableEntities.potions.HealthPotion;
import dungeonmania.entities.Items.collectableEntities.potions.InvincibilityPotion;
import dungeonmania.entities.Items.collectableEntities.potions.InvisibilityPotion;
import dungeonmania.entities.Items.collectableEntities.rareCollectableEntities.Anduril;
import dungeonmania.entities.Items.collectableEntities.rareCollectableEntities.TheOneRing;
import dungeonmania.entities.Items.PortableEntity;
import dungeonmania.entities.Items.buildableEntities.*;
import dungeonmania.entities.Items.buildableEntities.Shield;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.entities.staticEntities.*;
import dungeonmania.gamemodes.Context;
import dungeonmania.gamemodes.Hard;
import dungeonmania.gamemodes.Peaceful;
import dungeonmania.gamemodes.Standard;
import dungeonmania.util.*;



import java.util.HashMap;


public class EntityFactory {
    private int attack;
    private int spawnRate;
    private int playerHealth;
    private boolean invincibilityPotions;
    private int i;
    public EntityFactory(String gameMode, int i) {
        setGameMode(gameMode);
        this.i = i;
    }
    /**
     * set game mode
     * @param gameMode
     */
    public void setGameMode(String gameMode) {
        Context context;
        if (gameMode.equals("peaceful")) {
            context = new Context(new Peaceful());		
        } else if (gameMode.equals("hard")) {
            context = new Context(new Hard());		
        } else {
            context = new Context(new Standard());		
        } 
        this.attack = context.executeGameModeMobAttack();
        this.spawnRate = context.executeGameModeZombieSpawnRate();
        this.playerHealth = context.executeGameModePlayerHealth();
        this.invincibilityPotions = context.executeGameModeInvincibilityPotions();
    }

    public void setID(int i) {
        this.i = i;
    }
    /**
     * Creates an entity given the type and spawns it at the given position
     * 
     * @param type
     * @param x
     * @param y
     * @return the entity created
     */
    public Entity createFromType(String type, int x, int y) {
        Entity creation = null;
        if (type.equals("wall")) {
            creation = new Wall(type + i, type, new Position(x, y), false);
        } else if (type.equals("unlocked_door")) {
            creation = new Door(type+i, type, new Position(x,y), true, true);
        } else if (type.equals("exit")) {
            creation = new Exit(type + i, type, new Position(x,y), false);
        } else if (type.equals("boulder")) {
            creation = new Boulder(type+i, type, new Position(x,y), false);
        } else if (type.equals("switch")) {
            creation = new FloorSwitch(type+i, type, new Position(x,y), false);
        } else if (type.equals("zombie_toast_spawner")) {
            creation = new ZombieToastSpawner(type+i, type, new Position(x,y), true, this.spawnRate);
        } else if (type.equals("spider")) {
            creation = new Spider(type+i, type, new Position(x,y), true, this.attack);
        } else if (type.equals("zombie_toast")) {
            creation = new ZombieToast(type+i, type, new Position(x,y), true, this.attack);
        } else if (type.equals("hydra")) {
            creation = new Hydra(type+i, type, new Position(x,y), true, this.attack);
        } else if (type.equals("mercenary")) {
            creation = new Mercenary(type+i, type, new Position(x,y), true, this.attack);
        } else if (type.equals("assassin")) {
            creation = new Assassin(type+i, type, new Position(x,y), true, this.attack);
        } else if (type.equals("treasure")) {
            creation = new Treasure(type+i, type, new Position(x,y), true);
        } else if (type.equals("health_potion")) {
            creation = new HealthPotion(type+i, type, new Position(x,y), true);
        } else if (type.equals("invincibility_potion")) {
            creation = new InvincibilityPotion(type+i, type, new Position(x,y), true);
        } else if (type.equals("invisibility_potion")) {
            creation = new InvisibilityPotion(type+i, type, new Position(x,y), true);
        } else if (type.equals("wood")) {
            creation = new Wood(type+i, type, new Position(x,y), true);
        } else if (type.equals("arrow")) {
            creation = new Arrow(type+i, type, new Position(x,y), true);
        } else if (type.equals("bomb")) {
            creation = new Bomb(type+i, type, new Position(x,y), true);
        } else if (type.equals("sword")) {
            creation = new Sword(type+i, type, new Position(x,y), true);
        } else if (type.equals("armour")) {
            creation = new Armour(type+i, type, new Position(x,y), true);
        } else if (type.equals("one_ring")) {
            creation = new TheOneRing(type+i, type, new Position(x,y), true);
        } else if (type.equals("bow")) {
            creation = new Bow(type+i, type, new Position(x,y), true);
        } else if (type.equals("shield")) {
            creation = new Shield(type+i, type, new Position(x,y), true);
        } else if (type.equals("player")) {
            creation = new Player(type+i, type, new Position(x,y), true, this.playerHealth, this.invincibilityPotions);
        } else if (type.equals("sun_stone")) {
            creation = new SunStone(type+i, type, new Position(x,y), true);
        } else if (type.equals("sceptre")) {
            creation = new Sceptre(type+i, type, new Position(x,y), true);
        } else if (type.equals("midnight_armour")) {
            creation = new MidnightArmour(type+i, type, new Position(x,y), true);
        } else if (type.equals("anduril")) {
            creation = new Anduril(type+i, type, new Position(x,y), true);
        }
        i++;
        return creation;
    }   


    /**
     * Creating colour
     * 
     * @param type
     * @param x
     * @param y
     * @param colour
     * @return
     */
    public Entity createFromType(String type, int x, int y, String colour) {
        Entity creation = null;
        HashMap<String, String> colourMap = new HashMap<>();
        colourMap.put("BLUE", "blue_portal");
        colourMap.put("RED", "red_portal");
        colourMap.put("ORANGE", "orange_portal");
        colourMap.put("GREY", "grey_portal");
          
        if (type.equals("portal")) {    
            creation = new Portal(type+i, colourMap.get(colour), new Position(x,y), true, colour);
        } else {
            creation = new Portal(type+i, type, new Position(x,y), true, colour);
        }

        i++;
        return creation;

    }
    /**
     * Creating a door or key OR swamp tile
     * 
     * @param type
     * @param x
     * @param y
     * @param id
     * @return
     */
    public Entity createFromType(String type, int x, int y, int id) {
        Entity creation = null;    
        if (type.equals("door")) {    
            creation = new Door(type+i, type, new Position(x,y), true, id);
        } else if (type.equals("key")) {
            creation = new Key(type+i, type, new Position(x,y), true, id);
        } else if (type.equals("swamp_tile")) {
            creation = new SwampTile(type + i, type, new Position(x, y, 1), false, id);
        }
        i++;
        return creation;
    }
    /**
     * creating a floor switch, mercenary, assassin, boulder or bomb
     * 
     * @param type
     * @param x
     * @param y
     * @param bool
     * @return
     */
    public Entity createFromType(String type, int x, int y, boolean bool) {
        Entity creation = null;    
        if (type.equals("switch")) {
            creation = new FloorSwitch(type+i, type, new Position(x,y), false, bool);
        } else if (type.equals("mercenary")) {
            creation = new Mercenary(type+i, type, new Position(x,y), true, bool, attack);
        } else if (type.equals("assassin")) {
            creation = new Assassin(type+i, type, new Position(x,y), true, bool, attack);
        } else if (type.equals("boulder")) {
            creation = new Boulder(type+i, type, new Position(x,y), false, bool);
        } else if (type.equals("bomb")) {
            creation = new Bomb(type+i, type, new Position(x,y), false, bool);
        }
        i++;
        return creation;
    }
    /**
     * create a spider
     * 
     * @param type
     * @param x
     * @param y
     * @param direction
     * @param og_x
     * @param og_y
     * @return
     */
    public Entity createFromType(String type, int x, int y, Boolean direction, int og_x, int og_y) {
        Entity creation = null;    
        if (type.equals("spider")) {    
            creation = new Spider(type+i, type, new Position(x,y), true, new Position(og_x, og_y), attack);
        } 
        i++;
        return creation;
    }
    /**
     * create a key, sword, shield or armour
     *  with a durability
     * @param type
     * @param num
     * @return
     */
    public PortableEntity createFromType(String type, int num) {
        PortableEntity creation = null;    
        if (type.equals("key")) {    
            creation = new Key(type+i, type, new Position(0, 0), true, num);
        } else if (type.equals("sword")) {    
            creation = new Sword(type+i, type, new Position(0, 0), true, num);
        } else if (type.equals("shield")) {
            creation = new Shield(type+i, type, new Position(0,0), true, num);
        } else if (type.equals("armour")) {
            creation = new Armour(type+i, type, new Position(0,0), true, num);
        } else if (type.equals("bow")) {
            creation = new Bow(type+i, type, new Position(0,0), true, num);
        } else if (type.equals("anduril")) {
            creation = new Anduril(type+i, type, new Position(0,0), true, num);
        } else if (type.equals("midnight_armour")) {
            creation = new MidnightArmour(type+i, type, new Position(0, 0), true, num);
        } 
        i++;
        return creation;
    }
    /**
     * create a treasure, healthpotion, invincibility potion, invisibility potion, wood, arrow, bomb, onering or bow
     *  which is in a player's inventory and has not been used
     * @param type
     * @return
     */
    public PortableEntity createFromType(String type) {
        PortableEntity creation = null;    
        if (type.equals("treasure")) {
            creation = new Treasure(type+i, type, new Position(0,0), false);
        } else if (type.equals("health_potion")) {
            creation = new HealthPotion(type+i, type, new Position(0,0), true);
        } else if (type.equals("invincibility_potion")) {
            creation = new InvincibilityPotion(type+i, type, new Position(0,0), true);
        } else if (type.equals("invisibility_potion")) {
            creation = new InvisibilityPotion(type+i, type, new Position(0,0), true);
        } else if (type.equals("wood")) {
            creation = new Wood(type+i, type, new Position(0,0), false);
        } else if (type.equals("arrow")) {
            creation = new Arrow(type+i, type, new Position(0,0), false);
        } else if (type.equals("bomb")) {
            creation = new Bomb(type+i, type, new Position(0,0), true);
        } else if (type.equals("one_ring")) {
            creation = new TheOneRing(type+i, type, new Position(0,0), false);
        } else if (type.equals("sword")) {    
            creation = new Sword(type+i, type, new Position(0, 0), false);
        } else if (type.equals("shield")) {
            creation = new Shield(type+i, type, new Position(0,0), false);
        } else if (type.equals("armour")) {
            creation = new Armour(type+i, type, new Position(0,0), false);
        } else if (type.equals("bow")) {
            creation = new Bow(type+i, type, new Position(0,0), false);
        } else if (type.equals("anduril")) {
            creation = new Anduril(type+i, type, new Position(0,0), false);
        } else if (type.equals("midnight_armour")) {
            creation = new MidnightArmour(type+i, type, new Position(0,0), false);
        } else if (type.equals("sceptre")) {
            creation = new Sceptre(type+i, type, new Position(0,0), true);
        } else if (type.equals("sun_stone")) {
            creation = new SunStone(type+i, type, new Position(0,0), true);
        } 
        i++;
        return creation;
    }
    /**
     * create a player
     * @param type
     * @param x
     * @param y
     * @param health
     * @param state
     * @param tick
     * @return
     */
    public Entity createFromType(String type, int x, int y, int health, String state, int tick) {
        return new Player(type+i, type, new Position(x,y), true, health, this.invincibilityPotions, state, tick);
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
    
}
