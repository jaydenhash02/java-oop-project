package dungeonmania.goals;
import java.util.ArrayList;
import java.util.List;
import dungeonmania.DungeonLayout;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Items.collectableEntities.Treasure;
import dungeonmania.entities.movingEntities.Mob;
import dungeonmania.entities.staticEntities.Exit;
import dungeonmania.entities.staticEntities.FloorSwitch;
import dungeonmania.entities.staticEntities.ZombieToastSpawner;
public class Goal {
    private String type;
    private DungeonLayout dungeon;
    private List<Goal> subgoals = new ArrayList<Goal>(); // limited to 2

    public Goal(String type, DungeonLayout dungeon, Goal subgoal1, Goal subgoal2) {
        this.type = type;
        this.dungeon = dungeon;
        if (type.equals("AND") || type.equals("OR")) {
            this.subgoals.add(subgoal1);
            this.subgoals.add(subgoal2);
        }
    }
    /**
     * indicate whether a goal is satisfied
     * @return
     */
    public boolean isSatisfied() {
        if (this.type.equals("AND")) {
            for (Goal goal : this.subgoals) {
                if (goal.isSatisfied() == false) {
                    return false;
                }
            }
            return true;
        }
        else if (this.type.equals("OR")) {
            for (Goal goal : this.subgoals) {
                if (goal.isSatisfied() == true) {
                    return true;
                }
            }
            return false;
        }
        else if (this.type.equals("exit")) {
            for (Entity entity : dungeon.getAllEntities()) {
                if (entity instanceof Exit) {
                    Exit exit = (Exit) entity;
                    if (exit.getPosition().equals(dungeon.getPlayer().getPosition())) { // just the x and y coordinates have to be the same
                        return true;
                    }
                }
            }
            return false;
        }
        else if (this.type.equals("enemies")) {
            for (Entity entity : dungeon.getAllEntities()) {
                if (entity instanceof Mob || entity instanceof ZombieToastSpawner) { // except bribed mercenaries
                    return false;
                }
            }
            return true;
        }
        else if (this.type.equals("boulders")) {
            for (Entity entity : dungeon.getAllEntities()) {
                if (entity instanceof FloorSwitch) {
                    FloorSwitch s = (FloorSwitch) entity;
                    if (s.getActivated() == false) {
                        return false;
                    }
                }
            }
            return true;
        }
        else { // if (this.type.equals("treasure")) 
            for (Entity entity : dungeon.getAllEntities()) {
                if (entity instanceof Treasure) {
                    return false;
                }
            }
            return true;
        }
    }
    public List<Goal> getSubgoals() {
        return subgoals;
    }
    /**
     * return the string of the goal
     * @return
     */
    public String nameString() {
        String name = "";
        if (this.isSatisfied() == false) {
            if (!this.type.equals("AND") && !this.type.equals("OR")) {
                name = name + ":" + this.type + " ";
            }
            else {
                boolean connectorUsed = false;
                name = name + "(";
                for (Goal goal : this.subgoals) {
                    name = name + goal.nameString();
                    if (connectorUsed == false && !this.subgoals.get(0).isSatisfied() && !this.subgoals.get(1).isSatisfied()) {
                        name = name + this.type + " ";
                        connectorUsed = true;
                    }
                }
                name = name.substring(0,name.length()-1);
                name = name + ") ";
            }
        }
        return name;
    }
}