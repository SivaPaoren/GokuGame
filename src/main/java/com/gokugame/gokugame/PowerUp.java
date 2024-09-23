package com.gokugame.gokugame;

public class PowerUp extends GameObject {
    public PowerUp(double x) {
        super(x, 410, 5);
    }

    public PowerUp(double x, double speed,double width, double height) {
        super(x, 410, speed, width, height);
    }
}
