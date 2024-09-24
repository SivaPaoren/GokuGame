package com.gokugame.gokugame;

public class Projectile extends GameObject {
    private boolean isEnemy; // True if the projectile is shot by the enemy

    public Projectile(double x, double y, double speed, boolean isEnemy) {
        super(x, y, speed, 30, 30); // Set width and height of the projectile
        this.isEnemy = isEnemy;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    @Override
    public void move(double gameSpeed){
        x += gameSpeed;
    }
}
