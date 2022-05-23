package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

public class potionBattleTest {
    
    @Test
    public void noBattleWhileInvisible() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("potionBattleMap", "Standard");
        DungeonResponse response = controller.tick(null, Direction.DOWN);

        // Use invisibility potion
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("invisibility_potion")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }
        
        response = controller.tick(null, Direction.DOWN); 
        response = controller.tick(null, Direction.DOWN); 
        assertEquals(new Position(0, 3, 3), response.getEntities().get(5).getPosition());
        assertEquals(new Position(0, 3, 3), response.getEntities().get(6).getPosition());
        assertEquals(new Position(0, 4, 3), response.getEntities().get(7).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(8).getPosition());
        assertEquals(new Position(0, 6, 3), response.getEntities().get(9).getPosition());
        assertEquals(new Position(0, 7, 3), response.getEntities().get(10).getPosition());


        response = controller.tick(null, Direction.DOWN); 
        assertEquals(new Position(0, 4, 3), response.getEntities().get(5).getPosition());
        assertEquals(new Position(0, 3, 3), response.getEntities().get(6).getPosition());
        assertEquals(new Position(0, 4, 3), response.getEntities().get(7).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(8).getPosition());
        assertEquals(new Position(0, 6, 3), response.getEntities().get(9).getPosition());
        assertEquals(new Position(0, 7, 3), response.getEntities().get(10).getPosition());

        response = controller.tick(null, Direction.DOWN); 
        assertEquals(new Position(0, 5, 3), response.getEntities().get(5).getPosition());
        assertEquals(new Position(0, 3, 3), response.getEntities().get(6).getPosition());
        assertEquals(new Position(0, 4, 3), response.getEntities().get(7).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(8).getPosition());
        assertEquals(new Position(0, 6, 3), response.getEntities().get(9).getPosition());
        assertEquals(new Position(0, 7, 3), response.getEntities().get(10).getPosition());

        response = controller.tick(null, Direction.DOWN); 
        assertEquals(new Position(0, 6, 3), response.getEntities().get(5).getPosition());
        assertEquals(new Position(0, 3, 3), response.getEntities().get(6).getPosition());
        assertEquals(new Position(0, 4, 3), response.getEntities().get(7).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(8).getPosition());
        assertEquals(new Position(0, 6, 3), response.getEntities().get(9).getPosition());
        assertEquals(new Position(0, 7, 3), response.getEntities().get(10).getPosition());

        response = controller.tick(null, Direction.DOWN); 
        assertEquals(new Position(0, 7, 3), response.getEntities().get(5).getPosition());
        assertEquals(new Position(0, 3, 3), response.getEntities().get(6).getPosition());
        assertEquals(new Position(0, 4, 3), response.getEntities().get(7).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(8).getPosition());
        assertEquals(new Position(0, 6, 3), response.getEntities().get(9).getPosition());
        assertEquals(new Position(0, 7, 3), response.getEntities().get(10).getPosition());

        response = controller.tick(null, Direction.DOWN); 
        assertEquals(new Position(0, 8, 3), response.getEntities().get(5).getPosition());
        assertEquals(new Position(0, 3, 3), response.getEntities().get(6).getPosition());
        assertEquals(new Position(0, 4, 3), response.getEntities().get(7).getPosition());
        assertEquals(new Position(0, 5, 3), response.getEntities().get(8).getPosition());
        assertEquals(new Position(0, 6, 3), response.getEntities().get(9).getPosition());
        assertEquals(new Position(0, 7, 3), response.getEntities().get(10).getPosition());
    }

    @Test
    public void BattleWhileInvincible() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("potionBattleMap", "Standard");
        DungeonResponse response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN); 

        // Use invincibility potion
        List<ItemResponse> allItems = response.getInventory();
        for (ItemResponse item : allItems) {
            if (item.getType().equals("invincibility_potion")) {
                response = controller.tick(item.getId(), null);
                break;
            }
        }
        
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN); 
        response = controller.tick(null, Direction.DOWN); 
        response = controller.tick(null, Direction.DOWN); 
        response = controller.tick(null, Direction.DOWN); 
        response = controller.tick(null, Direction.DOWN); 

        List<EntityResponse> entities = response.getEntities();
        int numMobs = 0;
        for (EntityResponse entity : entities) {
            if (entity.getType().equals("spider") || entity.getType().equals("zombie_toast") || entity.getType().equals("mercenary") ||
                entity.getType().equals("assassin") || entity.getType().equals("hydra")) {
                numMobs += 1;
            }
        } 
        assertEquals(0, numMobs);
    }
}
