package dungeonmania.gamemodes;

public class Context {
    private GameMode gameMode; 

    public Context(GameMode gameMode){
        this.gameMode = gameMode;
    }

    public int executeGameModeMobAttack() {
        return gameMode.setMobAttack();
    }

    public int executeGameModeZombieSpawnRate() {
        return gameMode.setZombieSpawnRate();
    }
    
    public int executeGameModePlayerHealth() {
        return gameMode.setPlayerHealth();
    }

    public boolean executeGameModeInvincibilityPotions() {
        return gameMode.invincibilityPotions();
    }
}
