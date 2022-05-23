package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class GoalTest {
    @Test
    public void testAndAnd() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("goals", "peaceful");
        assertEquals(response.getGoals(), "(:exit AND (:boulders AND :treasure)) ");
        controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getGoals(), "(:exit AND (:boulders AND :treasure)) ");
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getGoals(), "(:exit AND (:treasure)) ");
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);
        assertEquals(response.getGoals(), "(:exit AND (:treasure)) ");
        response = controller.tick(null, Direction.DOWN);
        assertEquals(response.getGoals(), "(:exit) ");
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getGoals(), "(:exit) ");
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getGoals(), "");
    }
    @Test
    public void testAndOr() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("goals2", "peaceful");
        assertEquals(response.getGoals(), "(:exit AND (:boulders OR :treasure)) ");
        controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getGoals(), "(:exit AND (:boulders OR :treasure)) ");
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getGoals(), "(:exit) ");
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);
        assertEquals(response.getGoals(), "(:exit) ");
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getGoals(), "");
    }
    @Test
    public void testOrAnd() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("goals3", "peaceful");
        assertEquals(response.getGoals(), "(:exit OR (:boulders AND :treasure)) ");
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getGoals(), "(:exit OR (:boulders AND :treasure)) ");
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getGoals(), "");
    }
    @Test
    public void testEnemyGoal() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("spiderGoal", "peaceful");
        assertEquals(response.getGoals(), ":enemies ");
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.LEFT);
        assertEquals(response.getGoals(), ":enemies ");
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(response.getGoals(), "");
    }
    /* @Test
    public void testExitLast() {

    } */
    @Test
    public void testCoverage() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("goals4", "peaceful");
        controller.tick(null, Direction.RIGHT);
        DungeonResponse response1 = controller.saveGame("goals4");
        DungeonResponse response2 = controller.loadGame("goals4");
        assertEquals(response1.getGoals(), response2.getGoals());
        controller.tick(null, Direction.LEFT);
        controller.allGames();
    }
}
