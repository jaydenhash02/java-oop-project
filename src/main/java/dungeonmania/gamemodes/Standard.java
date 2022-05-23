package dungeonmania.gamemodes;

public class Standard implements GameMode{
    @Override
    public int setMobAttack() {
        return 10;
    }

    @Override
    public int setZombieSpawnRate() {
        return 20;
    }

    @Override
    public int setPlayerHealth() {
        return 100;
    }

    @Override
    public boolean invincibilityPotions() {
        return true;
    }
}