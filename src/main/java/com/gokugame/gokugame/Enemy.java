package com.gokugame.gokugame;

public class Enemy extends GameObject {
    public Enemy(double x) {
        super(x, 380, 0);
    }

    public Enemy(double x, double width, double height) {
        super(x, 380, 0, width, height);
    }
}
