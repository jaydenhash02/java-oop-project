package dungeonmania;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Items.OffensiveEquipment;
import dungeonmania.entities.Items.PortableEntity;
import dungeonmania.entities.Items.buildableEntities.*;
import dungeonmania.entities.Items.collectableEntities.*;
import dungeonmania.entities.Items.collectableEntities.rareCollectableEntities.Anduril;
import dungeonmania.entities.Items.collectableEntities.rareCollectableEntities.TheOneRing;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.entities.staticEntities.*;
import dungeonmania.exceptions.*;
import dungeonmania.goals.Goal;
import dungeonmania.playerStates.*;
import dungeonmania.response.models.*;
import dungeonmania.util.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

// creates Instance of a dungeon layout 
public class DungeonLayout {
    private String dungeonId;
    private String dungeonName;
    private List<Entity> allEntities;
    private String gameMode;
    private static int uniqueId = 0;
    private int maxX = 0;
    private int maxY = 0;
    private int potionTick = 11;
    private int id = 0;
    private Goal goalObject;
    private EntityFactory entityFactory;
    /**
     * Loads a new dungeon given the dungeon name, gamemode and dungeon file
     * 
     * @param dungeonName
     * @param gameMode
     * @param dungeonFile
     */
    public void loadNewDungeon(String dungeonName, String gameMode, String dungeonFile) {
        this.entityFactory = new EntityFactory(gameMode, this.id);
        JSONObject jsonMap = new JSONObject(dungeonFile);
        JSONArray jsonEntities = jsonMap.getJSONArray("entities");
        List<Entity> tempEntities = new ArrayList<Entity>();
        for (int i = 0; i < jsonEntities.length(); i++) {
            JSONObject curr = jsonEntities.getJSONObject(i);
            if (curr.has("colour")) {
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y"), curr.getString("colour")));
            } else if (curr.has("key")) {
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y"), curr.getInt("key")));
            } else if (curr.has("movement_factor")) {
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y"), curr.getInt("movement_factor")));
            }
            else {
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y")));
            }
            this.id = entityFactory.getI();
            // get the max x and y bounds for spawning spiders within a region
            if (curr.has("x") && curr.has("y")) {
                setNewMaxX(curr.getInt("x"));
                setNewMaxY(curr.getInt("y"));
            }
        }   
        
        JSONObject jsonGoals = null;
        if (jsonMap.has("goal-condition")) {
            jsonGoals = jsonMap.getJSONObject("goal-condition");
            
        }
        
        if (jsonGoals != null) {
            if (!hasMoreSubgoals(jsonGoals)) {
                Goal dungeonGoal = new Goal(jsonGoals.getString("goal"), this, null, null);
                this.goalObject = dungeonGoal;
            } 
            else { //(hasMoreSubgoals(jsonGoals)) {
                
                Goal subgoal1 = null;
                Goal subgoal2 = null;
                JSONObject jsonSubGoal1 = jsonGoals.getJSONArray("subgoals").getJSONObject(0);
                JSONObject jsonSubGoal2 = jsonGoals.getJSONArray("subgoals").getJSONObject(1);
                
                if (hasMoreSubgoals(jsonSubGoal1)) {
                    JSONObject jsonSubSubGoal1 = jsonSubGoal1.getJSONArray("subgoals").getJSONObject(0);
                    JSONObject jsonSubSubGoal2 = jsonSubGoal1.getJSONArray("subgoals").getJSONObject(1);
                    subgoal1 = new Goal(jsonSubGoal1.getString("goal"), this, 
                                new Goal(jsonSubSubGoal1.getString("goal"), this, null, null), 
                                new Goal(jsonSubSubGoal2.getString("goal"), this, null, null));
                }
                else {
                    subgoal1 = new Goal(jsonSubGoal1.getString("goal"), this, null, null);
                }
                if (hasMoreSubgoals(jsonSubGoal2)) {
                    JSONObject jsonSubSubGoal1 = jsonSubGoal2.getJSONArray("subgoals").getJSONObject(0);
                    JSONObject jsonSubSubGoal2 = jsonSubGoal2.getJSONArray("subgoals").getJSONObject(1);
                    subgoal2 = new Goal(jsonSubGoal2.getString("goal"), this, 
                            new Goal(jsonSubSubGoal1.getString("goal"), this, null, null), 
                            new Goal(jsonSubSubGoal2.getString("goal"), this, null, null));
                }
                else {
                    subgoal2 = new Goal(jsonSubGoal2.getString("goal"), this, null, null);
                }
                Goal dungeonGoal = new Goal(jsonGoals.getString("goal"), this, subgoal1, subgoal2);
                this.goalObject = dungeonGoal;
            }
        }
        //entityFactory.setGameMode(this.gameMode);
        this.dungeonId = dungeonName + uniqueId;
        this.dungeonName = dungeonName;
        this.dungeonId = dungeonName + uniqueId;
        this.allEntities = tempEntities;
        this.gameMode = gameMode;
        uniqueId++;
    }
    /**
     * load a dungeon
     * 
     * @param name
     */
    public void loadDungeon(String name) {
        JSONObject jsonMap = new JSONObject(name);
        this.id = jsonMap.getInt("id");
        this.gameMode = jsonMap.getString("game_mode");
        this.entityFactory = new EntityFactory(gameMode, this.id);
        entityFactory.setGameMode(this.gameMode);
        JSONArray jsonEntities = jsonMap.getJSONArray("entities");
        List<Entity> tempEntities = new ArrayList<Entity>();
        for (int i = 0; i < jsonEntities.length(); i++) {
            JSONObject curr = jsonEntities.getJSONObject(i);
            if (curr.has("colour")) {
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y"), curr.getString("colour")));
            } else if (curr.has("key")) {
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y"), curr.getInt("key")));
            } else if (curr.has("open")) {
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y"), curr.getBoolean("open")));
            } else if (curr.has("ally")) {
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y"), curr.getBoolean("ally")));
            } else if (curr.has("active")) {
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y"), curr.getBoolean("active")));
            } else if (curr.has("onswitch")) {
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y"), curr.getBoolean("onswitch")));
            } else if (curr.has("direction")) {
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y"), curr.getBoolean("direction"), curr.getInt("og_x"), curr.getInt("og_y")));
            } else if (curr.has("placed")) { 
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y"), curr.getBoolean("placed")));
            } else if (curr.has("state")) { 
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y"), curr.getInt("health"), curr.getString("state"), curr.getInt("tick")));
            } else if (curr.has("movement_factor")) {
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y"), curr.getInt("movement_factor")));
            }
            else {
                tempEntities.add(entityFactory.createFromType(curr.getString("type"), 
                                    curr.getInt("x"), curr.getInt("y")));
            }
            this.id = entityFactory.getI();
        }   
        this.allEntities = tempEntities;

        JSONArray jsonInventory = jsonMap.getJSONArray("inventory");
        List<PortableEntity> tempInventory = new ArrayList<PortableEntity>();
        for (int i = 0; i < jsonInventory.length(); i++) {
            JSONObject curr = jsonInventory.getJSONObject(i);
            if (curr.has("key")) {
                tempInventory.add(entityFactory.createFromType(curr.getString("type"), curr.getInt("key")));
            } else if (curr.has("durability")) {
                tempInventory.add(entityFactory.createFromType(curr.getString("type"), curr.getInt("durability")));
            } else {
                tempInventory.add(entityFactory.createFromType(curr.getString("type")));
            }
            this.id = entityFactory.getI();
        }   

        Player player = getPlayer();
        player.setInventory(tempInventory);

        this.dungeonId = dungeonName + uniqueId;
        this.dungeonName = jsonMap.getString("dungeon_name");
        this.dungeonId = dungeonName + uniqueId;
        uniqueId++;
    }
    
    /**
     * save a dungeon
     * 
     * @param name
     */
    public void saveDungeon(String name) {
        JSONObject fileInfo = new JSONObject();
        fileInfo.put("dungeon_name", this.dungeonName);
        fileInfo.put("game_mode", this.gameMode);
        fileInfo.put("id", this.id);

        JSONArray entities = new JSONArray(); 
        for (Entity entity : allEntities) {
            JSONObject object = new JSONObject();
            object.put("x", entity.getPosition().getX());
            object.put("y", entity.getPosition().getY());
            object.put("type", entity.getType());
            if (entity.getType().equals("blue_portal")) {
                object.put("colour", ((Portal)entity).getColour());
            } else if (entity.getType().equals("red_portal")) {
                object.put("colour", ((Portal)entity).getColour());
            } else if (entity.getType().equals("orange_portal")) {
                object.put("colour", ((Portal)entity).getColour());
            } else if (entity.getType().equals("grey_portal")) {
                object.put("colour", ((Portal)entity).getColour());
            } else if (entity.getType().equals("door")) {
                object.put("key", ((Door)entity).getKeyID());
            } else if (entity.getType().equals("key")) {
                object.put("key", ((Key)entity).getDoorID());
            } else if (entity.getType().equals("player")) {
                if (((Player)entity).getPlayerState() instanceof InvincibleState) {
                    object.put("state", "invinciblestate");
                } else if (((Player)entity).getPlayerState() instanceof InvisibleState) {
                    object.put("state", "invisiblestate");
                } else {
                    object.put("state", "normalstate");
                }
                object.put("tick", ((Player)entity).getPlayerTickCount());
                object.put("health", ((Player)entity).getHealth());
            } else if (entity.getType().equals("mercenary")) {
                object.put("ally", ((Mercenary)entity).isAlly());
            } else if (entity.getType().equals("assassin")) {
                object.put("ally", ((Assassin)entity).isAlly());
            } else if (entity.getType().equals("spider")) {
                object.put("direction", ((Spider)entity).getIsMovingClockwise());
                object.put("og_x", ((Spider)entity).getOgPosition().getX());
                object.put("og_y", ((Spider)entity).getOgPosition().getY());
            } else if (entity.getType().equals("switch")) {
                object.put("active", ((FloorSwitch)entity).getActivated());
            } else if (entity.getType().equals("boulder")) {
                object.put("onswitch", ((Boulder)entity).getOnSwitch());
            } else if (entity.getType().equals("bomb")) {
                object.put("placed", ((Bomb)entity).getPlayerStandable());
            } else if (entity.getType().equals("swamp_tile")) {
                object.put("movement_factor", ((SwampTile)entity).getMovement_factor());
            }
            entities.put(object);
        }
        fileInfo.put("entities", entities);

        //Inventory
        JSONArray inventory = new JSONArray(); 
        Player player = getPlayer();
        for (PortableEntity entity : player.getInventory()) {
            JSONObject object = new JSONObject();
            object.put("type", entity.getType());
            if (entity.getType().equals("key")) {
                object.put("key", ((Key)entity).getDoorID());
            } else if (entity.getType().equals("sword")) {
                object.put("durability", ((Sword)entity).getDurability());
            } else if (entity.getType().equals("anduril")) {
                object.put("durability", ((Anduril)entity).getDurability());
            } else if (entity.getType().equals("midnight_armour")) {
                object.put("durability", ((MidnightArmour)entity).getDurability());
            } else if (entity.getType().equals("shield")) {
                object.put("durability", ((Shield)entity).getDurability());
            } else if (entity.getType().equals("armour")) {
                object.put("durability", ((Armour)entity).getDurability());
            } 
            inventory.put(object);
        }
        fileInfo.put("inventory", inventory);
        
        try (FileWriter file = new FileWriter("src/main/resources/savegames/" + name + ".json")) {
            file.write(fileInfo.toString()); 
            file.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * interact with an entity
     * @param entity
     */
    public void interact(Entity entity) {
        Player player = getPlayer();
        if (entity instanceof Mercenary) {
            int mercenary_x  = entity.getPosition().getX();
            int mercenary_y  = entity.getPosition().getY();
            int player_x = player.getPosition().getX();
            int player_y = player.getPosition().getY();
            if (!((mercenary_x == player_x && Math.abs(mercenary_y - player_y) <= 2) || 
                (mercenary_y == player_y && Math.abs(mercenary_x - player_x) <= 2))) {
                throw new InvalidActionException("Player not in range to bribe");
            }
            
            boolean bribed = false;
            List<PortableEntity> inventory = player.getInventory();

            // bribe an assassin (assassin is a type of mercenary)
            // sceptre or 
            // requires (TheOneRing + treasure) or (TheOneRing + SunStone)
            if (entity instanceof Assassin) {
                // try using a sceptre first
                bribed = mindControlSceptre(player, entity);
                if (bribed) {
                    return;
                }
                // try using normal requirements to brine now
                boolean hasOneRing = false;
                boolean hasSunStone = false;
                boolean hasTreasure = false;    
                for (PortableEntity p: inventory) {
                    if (p instanceof TheOneRing) {
                        hasOneRing = true;
                    }

                    if (p instanceof SunStone) {
                        hasSunStone = true;
                    }

                    if (p instanceof Treasure) {
                        hasTreasure = true;
                    }
                }

                if (hasOneRing == true) {
                    if (hasSunStone == true) {
                        for (PortableEntity p: inventory) {
                            if (p instanceof TheOneRing) {
                                inventory.remove(p);
                                break;
                            }
                        }
                        bribed = true;
                        ((Assassin)entity).setAlly(true);
                        player.getAllAllies().add((Assassin)entity);
                    } else if (hasTreasure == true) {
                        for (PortableEntity p: inventory) {
                            if (p instanceof TheOneRing) {
                                inventory.remove(p);
                                break;
                            }
                        }

                        for (PortableEntity p: inventory) {
                            if (p instanceof Treasure) {
                                inventory.remove(p);
                                break;
                            }
                        }          
                        bribed = true;
                        ((Assassin)entity).setAlly(true);
                        player.getAllAllies().add((Assassin)entity);

                    }
                }

                if (bribed == false) {
                    throw new InvalidActionException("Player cannot bribe Assassin");
                }
                return;
            }
            // otherwise bribing a standard Mercenary
            // first check if the player has a sunstone
            for (PortableEntity portable : inventory) {
                if (portable instanceof SunStone) {
                    ((Mercenary)entity).setAlly(true);
                    bribed = true;
                    player.getAllAllies().add((Mercenary)entity);
                    break;
                }
            }
            // then check if player has sceptre
            if (bribed == false) {
                bribed = mindControlSceptre(player, entity);
            }
            // then check if player has a treasure
            if (bribed == false) {
                for (PortableEntity portableEntity : inventory) {
                    if (portableEntity instanceof Treasure) {
                        player.getInventory().remove(portableEntity);
                        ((Mercenary)entity).setAlly(true);
                        bribed = true;
                        player.getAllAllies().add((Mercenary)entity);
                        break;
                    }
                }
            }
            if (bribed == false) {
                throw new InvalidActionException("Player cannot bribe mercenary");
            }

        } else if (entity instanceof ZombieToastSpawner) {
            if (isAdjacent(player.getPosition(), entity.getPosition())) {
                Boolean weapon = false;
                List<PortableEntity> inventory = player.getInventory();
                for (PortableEntity portableEntity : inventory) {
                    if (portableEntity instanceof Sword || portableEntity instanceof Bow ||
                        portableEntity instanceof Anduril) {
                        ((OffensiveEquipment)portableEntity).decreaseDurability();
                        weapon = true;
                        allEntities.remove(entity);
                        break;
                    }
                }
                if (weapon == false) {
                    throw new InvalidActionException("Player has no weapon to destroy spawner");
                }
            } else {
                throw new InvalidActionException("Player not in range to destroy spawner");
            }
        }
    }
    /**
     * mind controls a mercenary or assassin
     * @param player
     * @param entity
     * @return true if player has a sceptre, false otherwise
     */
    public boolean mindControlSceptre(Player player, Entity entity) {
        for (PortableEntity port : player.getInventory()) {
            if (port instanceof Sceptre) {
                ((Mercenary)entity).setAlly(true);
                ((Mercenary)entity).setMindControl(0);
                player.getAllAllies().add((Mercenary)entity);
                return true;
            }
        }
        return false;
    }


    /**
     * check if a goal has subgoals
     * 
     * @param jsonGoals
     * @return
     */
    private boolean hasMoreSubgoals(JSONObject jsonGoals) {
        return (jsonGoals.getString("goal").equals("AND") || jsonGoals.getString("goal").equals("OR"));
    }
    /**
     * gets the entitity matching the id
     * @param entityId
     * @return entity
     */
    public Entity entityExist(String entityId) {
        for (Entity e : allEntities) {
            if (e.getId().equals(entityId)) {
                return e;
            }
        }
        return null;
    }

    /**
     * get the entity at a position
     * 
     * @param position
     * @return
     */
    public List<Entity> entityAtPosition(Position position) {
        List<Entity> entityAtPosition = new ArrayList<Entity>();
        for (Entity e: allEntities) {
            if (e.getPosition().equals(position)) {
                entityAtPosition.add(e);
            }
        }
        return entityAtPosition;
    }

    /**
     * Uses an item or moves the player
     * 
     * @param itemUsed
     * @param movementDirection
     * @throws InvalidActionException
     */
    public void updatePlayerTick(String itemUsed, Direction movementDirection) throws InvalidActionException {
        Player player = getPlayer();
        if (itemUsed != null) {
            boolean isInInventory = player.checkItemTypeExistsInInventory(itemUsed);
            if (!isInInventory) {
                throw new InvalidActionException("Item not in player's inventory.");
            }
            if (itemUsed.contains("invisibility_potion")) {
                potionTick = 0;
            } else if (itemUsed.contains("invincibility_potion")) {
                potionTick = 0;
            }
            player.useItem(itemUsed, this);
        }
        if (movementDirection != null) player.updatePosition(movementDirection, this);
        updatePotionTick();
        player.incrementPlayerTickCount();
        player.updatePotentialBuildables(this);
    }
    /**
     * update potion tick
     */
    public void updatePotionTick() {
        potionTick += 1;
        if (potionTick == 10) {
            getPlayer().setPlayerState(new NormalState(getPlayer()));
            getPlayer().Notify();
        }
    }
    /**
     * update spawn tick
     */
    public void updateMobsSpawnTick() {
        // checking max number of spider
        int spiderCount = 0;
        for (Entity e : allEntities) {
            if (e instanceof Spider) {
                spiderCount++;
            }
        }

        // Spawn spiders
        if (spiderCount < Spider.maxSpiders) {
            if (getPlayer().getPlayerTickCount() % Spider.spawnRate == 0) {
                Random rand = new Random();
                if (maxX == 0) {
                    maxX = 1;
                }
                if (maxY == 0) {
                    maxY = 1;
                }
                int randX = rand.nextInt(Math.abs(maxX));
                int randY = rand.nextInt(Math.abs(maxY));
                Spider spawnedSpider = (Spider)this.entityFactory.createFromType("spider", randX, randY);
                allEntities.add(spawnedSpider);
            }
        }


        // Spawn zombie toasts
        List<ZombieToast> spawnedZombies = new ArrayList<>();
        for (Entity e: allEntities) {
            // spawn zombies
            if (e instanceof ZombieToastSpawner) {
                ((ZombieToastSpawner)e).spawnZombine(this, spawnedZombies);
            }
        }
        allEntities.addAll(spawnedZombies);

        // Spawn mercenary at entryLocation if there is at least 1 mob in map
        if (getPlayer().getPlayerTickCount() % Mercenary.spawnRate == 0) {
            for (Entity e: allEntities) {
                if (e instanceof Mob) {
                    // 30% chance of spawning a assassin
                    if (new Random().nextInt(10) < 3) {
                        Assassin assassin = (Assassin)this.entityFactory
                            .createFromType("assassin", getPlayer().getEntryLocation().getX(), 
                                            getPlayer().getEntryLocation().getY());
                        allEntities.add(assassin);
                    } else {
                        Mercenary mercenary = (Mercenary)this.entityFactory
                            .createFromType("mercenary", getPlayer().getEntryLocation().getX(), 
                                            getPlayer().getEntryLocation().getY());
                        allEntities.add(mercenary);
                    }
                    break;
                }
            }
        }


        // Spawn Hydra if player has moved 50 ticks and gamemode is hardmode
        // spawn it at the player's OG spawn point
        if (this.gameMode.equals("Hard".toLowerCase()) && 
            getPlayer().getPlayerTickCount() % Hydra.spawnRate == 0) {
            Hydra hydra = (Hydra)entityFactory.createFromType("hydra", 
            getPlayer().getEntryLocation().getX(), getPlayer().getEntryLocation().getY());

            allEntities.add(hydra);
        }
    }
    /**
     * update mob tick
     */
    public void updateMobsTick() {
        for (Entity e: getAllEntities()) {
            if (e instanceof Mob) {
                ((Mob) e).mobMovement(this);
            }
        }
    }
    /**
     * add observers
     */
    public void addObservers() {
        Player player = getPlayer();
        for (Entity e: getAllEntities()) {
            if (e instanceof Mob) {
                player.AddObserver((Observer) e);
            }
        }
    }
    /**
     * update battle status
     */
    public void updateBattlesStatus() {
        for (Entity e: getAllEntities()) {
            if (e instanceof Player) {

                ((Player) e).checkBattle(this);
                break;
            }
        }
    }
    /**
     * get player
     * @return Player
     */
    public Player getPlayer() {
        // get player 
        for (Entity e: getAllEntities()) {
            if (e instanceof Player) {
                return ((Player) e);
            }
        }        
        return null;
    }
    /**
     * return whether a player is dead
     * 
     * @return
     */
    public boolean playerIsDead() {
        return (getPlayer().isDead());
    }
    /**
     * get entity response without player
     * @return
     */
    public List<EntityResponse> getEntityResponseNoPlayer() {
        List<EntityResponse> entities = new ArrayList<EntityResponse>();
        for (Entity e: allEntities) {
            if (!(e instanceof Player)) {
                entities.add(new EntityResponse(e.getId(), e.getType(), e.getPosition(), e.isInteractable()));
            }
        }
        return entities;
    }
    /**
     * get item response inventory
     * @return
     */
    public List<ItemResponse> getItemResponseInventory() {
        List<PortableEntity> inventory = getPlayer().getInventory();
        List<ItemResponse> retList = new ArrayList<ItemResponse>();
        for (PortableEntity e: inventory) {
            retList.add(new ItemResponse(e.getId(), e.getType()));
        }

        return retList;
    }
    /**
     * Returns a list of buildables
     * @return
     */
    public List<String> getbuildablesList() {
        return getPlayer().getPotentialBuildables();
    }
    /**
     * Returns a list of entity responses
     * @return
     */
    public List<EntityResponse> getEntityResponseAllEntities() {
        List<EntityResponse> entities = new ArrayList<EntityResponse>();
        for (Entity e: allEntities) {
            entities.add(new EntityResponse(e.getId(), e.getType(), e.getPosition(), e.isInteractable()));
        }
        return entities;
    }

    private void setNewMaxX(int Xcoord) {
        if (Xcoord > this.maxX) {
            this.maxX = Xcoord;
        }
    }

    private void setNewMaxY(int Ycoord) {
        if (Ycoord > this.maxY) {
            this.maxY = Ycoord;
        }
    }
    

    public String getDungeonId() {
        return dungeonId;
    }

    public List<Entity> getAllEntities() {
        return allEntities;
    }

    public String getDungeonName() {
        return dungeonName;
    }

    public String getGameMode() {
        return gameMode;
    }

    public Goal getGoalObject() {
        return goalObject;
    }
    /**
     * return whether a position is adjacent
     * @param a
     * @param b
     * @return boolean
     */
    public boolean isAdjacent(Position a, Position b) {
        int x = a.getX() - b.getX();
        int y = a.getY() - b.getY();

        return Math.abs(x) + Math.abs(y) == 1;
    }
    public EntityFactory getEntityFactory() {
        return entityFactory;
    }
}
