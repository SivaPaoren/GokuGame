package com.gokugame.gokugame;

public class GameObject {
    protected double x, y;
    protected double speed;

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        this.speed = 5;
    }

    public void move(double gameSpeed) {
        x -= gameSpeed;
    }

    public boolean isOutOfScreen() {
        return x < -50;
    }

    public void resetPosition() {
        x = 800;
    }

    public double getX() { return x; }
    public double getY() { return y; }
}





