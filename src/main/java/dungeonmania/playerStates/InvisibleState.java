package dungeonmania.playerStates;

import dungeonmania.entities.movingEntities.Player;

public class InvisibleState extends PlayerState {

    public InvisibleState(Player player) {
        super(player);
    }

    @Override
    public void drinkInvincibilityPotion() {
        getPlayer().setPlayerState(new InvincibleState(getPlayer()));
        
    }

    @Override
    public void drinkInvisibilityPotion() {
        getPlayer().setPlayerState(new InvisibleState(getPlayer()));
        
    }
    
}
