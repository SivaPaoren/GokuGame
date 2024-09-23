package com.gokugame.gokugame;

public class Obstacle extends GameObject {
    public Obstacle(double x) {
        super(x, 500, 5);
    }

    public Obstacle(double x,double speed, double width, double height) {
        super(x, 500, speed, width, height);
    }
}
