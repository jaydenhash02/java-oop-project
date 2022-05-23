package dungeonmania;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Items.buildableEntities.BuildableEntity;
import dungeonmania.entities.movingEntities.*;
import dungeonmania.exceptions.*;
import dungeonmania.response.models.*;
import dungeonmania.util.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DungeonManiaController {
    private DungeonLayout dungeon = new DungeonLayout();
    
    public DungeonLayout getDungeon() {
        return dungeon;
    }
    
    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("standard", "peaceful", "hard");
    }

    /**
     * Returns a list of the dungeons
     * 
     * @return ArrayList
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    /**
     * Starts a new game given the dungeon name and gamemode.
     * 
     * @param dungeonName
     * @param gameMode
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
        if (!getGameModes().contains(gameMode.toLowerCase())) {
            throw new IllegalArgumentException("Not valid game mode.");
        }

        if (!dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException("Dungeon does not exist.");
        }

        String dungeonInfo;
        // load a dungeon from resources/dungeons/
        try {
            dungeonInfo = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        } catch (IOException e) {
            // return null because dungeon was not found
            return null;
        }
        dungeon.loadNewDungeon(dungeonName, gameMode, dungeonInfo);
        return new DungeonResponse(dungeon.getDungeonId(), dungeon.getDungeonName(), 
            dungeon.getEntityResponseAllEntities(), dungeon.getItemResponseInventory(), 
            dungeon.getbuildablesList(), goal(dungeon));
    }
    
    /**
     * Saves the game given the name
     * 
     * @param name
     * @return
     * @throws IllegalArgumentException
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        dungeon.saveDungeon(name);  
        return new DungeonResponse(dungeon.getDungeonId(), dungeon.getDungeonName(), 
            dungeon.getEntityResponseAllEntities(), dungeon.getItemResponseInventory(), 
            dungeon.getbuildablesList(), goal(dungeon));
    }
    /**
     * Loads the game given name
     * 
     * @param name
     * @return
     * @throws IllegalArgumentException
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        String dungeonInfo;
        // load a dungeon from resources/dungeons/
        try {
            dungeonInfo = FileLoader.loadResourceFile("/savegames/" + name + ".json");
        } catch (IOException e) {
            throw new IllegalArgumentException("Dungeon does not exist.");
        }        
        dungeon.loadDungeon(dungeonInfo);
        dungeon.getPlayer().updatePotentialBuildables(dungeon);
        return new DungeonResponse(dungeon.getDungeonId(), dungeon.getDungeonName(), 
            dungeon.getEntityResponseAllEntities(), dungeon.getItemResponseInventory(), 
            dungeon.getbuildablesList(), goal(dungeon));
    }
    /**
     * Returns a list of the games
     * 
     * @return ArrayList
     */
    public List<String> allGames() {
        List<String> allGames = new ArrayList<>();
        File folder = new File("src/main/resources/savegames/");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            allGames.add(file.getName().substring(0, file.getName().length() - 5));
        }
        return allGames;
    }
    /**
     * Uses the given item or moves the player in the given direction
     * 
     * @param itemUsed
     * @param movementDirection
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        if (itemUsed != null) {
            if (!itemUsed.contains("bomb") && !itemUsed.contains("health_potion") 
                && !itemUsed.contains("invincibility_potion") && !itemUsed.contains("invisibility_potion")) {
                throw new IllegalArgumentException("Not valid item to use.");
            }
        }

        // update movement of player
        dungeon.updatePlayerTick(itemUsed, movementDirection);
        dungeon.addObservers();
        
        // update movement of mobs
        dungeon.updateMobsTick();


        // spawn mobs
        dungeon.updateMobsSpawnTick();


        // if there were any battles, update the battle status after all movement
        dungeon.updateBattlesStatus();
        // if the player is dead return the entities not containing the player
        if (dungeon.playerIsDead()) {
            return new DungeonResponse(dungeon.getDungeonId(), dungeon.getDungeonName(), 
            dungeon.getEntityResponseNoPlayer(), dungeon.getItemResponseInventory(), 
            dungeon.getbuildablesList(), goal(dungeon));
        }
        return new DungeonResponse(dungeon.getDungeonId(), dungeon.getDungeonName(), 
        dungeon.getEntityResponseAllEntities(), dungeon.getItemResponseInventory(), 
        dungeon.getbuildablesList(), goal(dungeon));
    }
    /**
     * 
     * @param entityId
     * @return
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Entity entity = dungeon.entityExist(entityId);
        if (entity == null) {
            throw new IllegalArgumentException("Entity does not exist.");
        }
        dungeon.interact(entity);
        return new DungeonResponse(dungeon.getDungeonId(), dungeon.getDungeonName(), 
        dungeon.getEntityResponseAllEntities(), dungeon.getItemResponseInventory(), 
        dungeon.getbuildablesList(), goal(dungeon));
    }


    /**
     * Build a bow, shield, sceptre or midnight armour
     * 
     * @param buildable
     * @return DungeonResponse
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        if (!buildable.equals("bow") && !buildable.equals("shield") && 
            !buildable.equals("sceptre") && !buildable.equals("midnight_armour")) {
            throw new IllegalArgumentException("Can only build bow, shield, sceptre or midnight armour");
        }

        Player player = dungeon.getPlayer();
        BuildableEntity craftEntity = player.craftBuildable(buildable, dungeon);
        if (craftEntity == null) {
            throw new InvalidActionException("Player does not have sufficient items to craft buildable.");
        }

        player.updatePotentialBuildables(dungeon);
        return new DungeonResponse(dungeon.getDungeonId(), dungeon.getDungeonName(), 
        dungeon.getEntityResponseAllEntities(), dungeon.getItemResponseInventory(), 
        dungeon.getbuildablesList(), goal(dungeon));
    }
    /**
     * returns the goal as a string
     * @param dungeon
     * @return goal string
     */
    public String goal(DungeonLayout dungeon) {
        String goal;
        if (dungeon.getGoalObject() == null) {
            goal = "";
        } else {
            goal = dungeon.getGoalObject().nameString();
        }
        return goal;
    }

}