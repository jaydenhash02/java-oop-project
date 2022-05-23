package dungeonmania.gamemodes;

public class Hard implements GameMode{
    @Override
    public int setMobAttack() {
        return 15;
    }

    @Override
    public int setZombieSpawnRate() {
        return 15;
    }

    @Override
    public int setPlayerHealth() {
        return 80;
    }

    @Override
    public boolean invincibilityPotions() {
        return false;
    }
}