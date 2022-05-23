package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntities.Assassin;
import dungeonmania.entities.movingEntities.Hydra;
import dungeonmania.entities.movingEntities.Mob;
import dungeonmania.entities.movingEntities.Player;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class BossesTest {
    @Test
    public void hypotheticalBattleAssassinWithAnduril() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("assassinFix", "standard");
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        // our guy should have the anduril now
        DungeonLayout currDungeon = controller.getDungeon();
        for (Entity e: currDungeon.getAllEntities()) {
            if (e instanceof Assassin) {
                Player p = currDungeon.getPlayer();
                // should do 3 times damage to an Assassin
                assertEquals(p.findDamageModification((Mob)e)[0], 3.0);
                break;
            }
        }

    }


    @Test
    public void testHydraSpawnAndHypotheticalBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("test_battles", "hard");
        for (int i = 0; i < 40; i++) {
            controller.tick(null, Direction.UP);
        }

        for (int i = 0; i < 10; i++) {
            controller.tick(null, Direction.DOWN);
        }

        List<EntityResponse> allEntity = controller.getDungeon().getEntityResponseAllEntities();
        for (EntityResponse er: allEntity) {
            if (er.getType().equals("hydra")) {
                Entity hydra = controller.getDungeon().entityExist(er.getId());

                assertTrue(hydra != null);
            }
        }

        DungeonLayout currDungeon = controller.getDungeon();
        for (Entity e: currDungeon.getAllEntities()) {
            if (e instanceof Hydra) {
                Player p = currDungeon.getPlayer();
                // should do 1 damage since does not have a weapon
                assertEquals(p.findDamageModification((Mob)e)[0], 1.0);
                break;
            }
        }
        
    }
}
