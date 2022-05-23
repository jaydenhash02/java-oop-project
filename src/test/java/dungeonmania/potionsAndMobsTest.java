package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class potionsAndMobsTest {
 
    @Test
    public void spiderMovementWhileInvisible() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("potionsAndSpider", "Standard");
        DungeonResponse response = controller.tick(null, Direction.RIGHT);

        // Use invisibility potion
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("invisibility_potion")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }

        assertEquals(new Position(1, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(1, -2, 3), response.getEntities().get(3).getPosition());

        response = controller.tick(null, Direction.UP); 
        assertEquals(new Position(1, -1, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(1, -1, 3), response.getEntities().get(3).getPosition());

        response = controller.tick(null, Direction.DOWN); 
        assertEquals(new Position(1, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(1, 0, 3), response.getEntities().get(3).getPosition());

        response = controller.tick(null, Direction.LEFT); 
        assertEquals(new Position(0, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(0, 0, 3), response.getEntities().get(3).getPosition());

        response = controller.tick(null, Direction.LEFT); 
        assertEquals(new Position(-1, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(-1, 0, 3), response.getEntities().get(2).getPosition());

        response = controller.tick(null, Direction.UP); 
        assertEquals(new Position(-1, -1, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(-1, -1, 3), response.getEntities().get(2).getPosition());

        response = controller.tick(null, Direction.UP); 
        assertEquals(new Position(-1, -2, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(-1, -2, 3), response.getEntities().get(2).getPosition());

        response = controller.tick(null, Direction.RIGHT); 
        assertEquals(new Position(0, -2, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(0, -2, 3), response.getEntities().get(2).getPosition());

        response = controller.tick(null, Direction.RIGHT); 
        assertEquals(new Position(1, -2, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(1, -2, 3), response.getEntities().get(2).getPosition());  

    }

    @Test
    public void SpiderMovementWhileInvincible() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("potionsAndSpider", "Standard");
        DungeonResponse response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(-2, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(0, -2, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(3, 0, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, 1, 3), response.getEntities().get(5).getPosition());

        // Use invincibility potion
        
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("invincibility_potion")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }        

        assertEquals(new Position(-3, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(1, -2, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(4, 0, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(1, 1, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(-4, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(2, -2, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(5, 0, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(2, 1, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(-5, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(3, -2, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(6, 0, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(3, 1, 3), response.getEntities().get(5).getPosition());

    }

    @Test 
    public void MercenaryMovementWhileInvisible() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("potionsAndMercenary", "Standard");
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.LEFT);

        // Use invisibility potion
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("invisibility_potion")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }        

        assertEquals(new Position(2, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-2, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 2, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -2, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(2, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(2, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-2, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 2, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -2, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(3, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(2, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-2, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 2, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -2, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(2, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(2, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-2, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 2, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -2, 3), response.getEntities().get(5).getPosition());
    }

    @Test
    public void MercenaryMovementWhileInvincible() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("potionsAndMercenary", "Standard");
        DungeonResponse response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.RIGHT);

        // Use invincibility potion
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("invincibility_potion")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }        

        assertEquals(new Position(3, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-3, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 3, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -3, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(0, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(4, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-4, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 4, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -4, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(1, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(5, 0, 3), response.getEntities().get(1).getPosition());
        assertEquals(new Position(-5, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, -5, 3), response.getEntities().get(4).getPosition());

        response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(0, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(5, 0, 3), response.getEntities().get(1).getPosition());
        assertEquals(new Position(-5, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, -5, 3), response.getEntities().get(4).getPosition());
    }

    @Test
    public void ZombieMovementWhileInvincible() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("potionsAndZombies", "Standard");
        DungeonResponse response = controller.tick(null, Direction.LEFT);

        // Use invincibility potion
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("invincibility_potion")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }        

        assertEquals(new Position(5, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-5, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -5, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(5, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-5, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -5, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(5, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-5, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -5, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(5, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-5, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -5, 3), response.getEntities().get(5).getPosition());
    }

    @Test
    public void HydraMovementWhileInvincible() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("potionsAndHydra", "Standard");
        DungeonResponse response = controller.tick(null, Direction.LEFT);

        // Use invincibility potion
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("invincibility_potion")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }        

        assertEquals(new Position(5, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-5, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -5, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(5, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-5, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -5, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(5, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-5, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -5, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(5, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-5, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -5, 3), response.getEntities().get(5).getPosition());
    }

    @Test 
    public void AssassinMovementWhileInvisible() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("potionsAndAssassin", "Standard");
        DungeonResponse response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.LEFT);

        // Use invisibility potion
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("invisibility_potion")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }        

        assertEquals(new Position(2, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-2, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 2, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -2, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(2, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(2, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-2, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 2, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -2, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(3, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(2, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-2, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 2, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -2, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(2, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(2, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-2, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 2, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -2, 3), response.getEntities().get(5).getPosition());
    }

    @Test
    public void AssassinMovementWhileInvincible() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("potionsAndAssassin", "Standard");
        DungeonResponse response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.LEFT);
        response = controller.tick(null, Direction.RIGHT);

        // Use invincibility potion
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("invincibility_potion")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }        

        assertEquals(new Position(3, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-3, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 3, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -3, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(0, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(4, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(-4, 0, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, 4, 3), response.getEntities().get(4).getPosition());
        assertEquals(new Position(0, -4, 3), response.getEntities().get(5).getPosition());

        response = controller.tick(null, Direction.RIGHT);
        assertEquals(new Position(1, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(5, 0, 3), response.getEntities().get(1).getPosition());
        assertEquals(new Position(-5, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, -5, 3), response.getEntities().get(4).getPosition());

        response = controller.tick(null, Direction.LEFT);
        assertEquals(new Position(0, 0, 3), response.getEntities().get(0).getPosition());
        assertEquals(new Position(5, 0, 3), response.getEntities().get(1).getPosition());
        assertEquals(new Position(-5, 0, 3), response.getEntities().get(2).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(3).getPosition());
        assertEquals(new Position(0, -5, 3), response.getEntities().get(4).getPosition());
    }
    @Test
    public void testHealthPotion() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("healthPotion", "standard");
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.RIGHT);
        // battle the spider
        controller.tick(null, Direction.LEFT);
        // player loses 25 health from battle
        assertEquals(controller.getDungeon().getPlayer().getHealth(), 75);
        // drink the potion
        controller.tick("health_potion", null);
        // should be at 100 health
        assertEquals(controller.getDungeon().getPlayer().getHealth(), 100);
    }
}
