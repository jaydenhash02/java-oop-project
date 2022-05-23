package dungeonmania.entities.movingEntities;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity {
    private double attack;
    private double health;

    public MovingEntity(String id, String type, Position position, boolean isInteractable) {
        super(id, type, position, isInteractable);
    }

    public double getAttack() {
        return this.attack;
    }

    public double getHealth() {
        return this.health;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void setHealth(double health) {
        this.health = health;
    }    

    // moving entity's attack dmg
    public abstract double attackDMG();

    // take damage from a movingEntity source
    public abstract void takeDamage(MovingEntity e, double dmgModifier);

}
