package dungeonmania.playerStates;

import dungeonmania.entities.movingEntities.Player;

// basically use this class to change the state of player when a invisible/invincible potion is consumed
public abstract class PlayerState {
    private Player player;

    public PlayerState(Player player) {
        this.player = player;
    }
    
    public abstract void drinkInvincibilityPotion();

    public abstract void drinkInvisibilityPotion();

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
