package com.gokugame.gokugame;

public class GameObject {
    protected double x, y;
    protected double speed;

    public GameObject(double x, double y, double speed) {
        this.x = x;
        this.y = y;  // The Y position will be initialized when the object is created (e.g., obstacles).
        this.speed = speed;
    }

    public void move(double gameSpeed) {
        x -= gameSpeed;
    }

    public boolean isOutOfScreen() {
        return x < -50;
    }

    public void resetPosition() {
        x = 1000;
        // The Y position can also be reset if necessary in specific obstacles or power-ups.
    }

    public double getX() { return x; }
    public double getY() { return y; }
}
