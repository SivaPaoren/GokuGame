package com.gokugame.gokugame;

public class GameObject {
    protected double x, y;
    protected double speed;
    protected double width, height;

    public GameObject(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public GameObject(double x, double y, double speed, double width, double height) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    public void move(double gameSpeed) {
        x -= gameSpeed;
    }

    public boolean isOutOfScreen() {
        return x < -50;
    }

    public void resetPosition(double resetPositionX) {
        x = resetPositionX;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getSpeed() { return speed; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
