package com.gokugame.gokugame;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private double gokuY = 480;  // Changed from 300 to 500
    private double velocityY = 0;
    private boolean jumping = false;
    private boolean running = true;
    private final double GRAVITY = 0.6;
    private int score = 0;
    private boolean gameOver = false;
    private final List<Obstacle> obstacles = new ArrayList<>();
    private final Enemy enemy = new Enemy(900);
    private PowerUp powerUp;
    private double gameSpeed = 5;
    private boolean powerUpActive = false;
    private int maxHealth = 100;

    //this one is hard coded for now to test we need to make it dynamic for later
    private int currentHealth = 80;
    public GameLogic() {
        // Initialize obstacles and power-up
        resetGame();
    }

    public void update() {
        // Goku's jump logic
        if (jumping) {
            running = false;
            velocityY -= GRAVITY;
            gokuY -= velocityY;
            if (gokuY >= 480) {  // Changed from 500 to 480
                gokuY = 480;     // Changed from 500 to 480
                jumping = false;
                running = true;
                velocityY = 0;
            }
        }

        // Move and reset obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.move(gameSpeed);
            if (obstacle.isOutOfScreen()) {
                obstacle.resetPosition();
                score++;
            }
        }

        // Move and check power-up
        if (powerUp != null) {
            powerUp.move(gameSpeed);
            if (powerUp.isOutOfScreen()) {
                powerUp.resetPosition();
            }
            // Power-up collision with Goku
            if (checkCollision(powerUp)) {
                powerUpActive = true;
                powerUp.resetPosition();
            }
        }

        // Detect collisions with obstacles
        for (Obstacle obstacle : obstacles) {
            if (checkCollision(obstacle)) {
                gameOver = true;
                return;
            }
        }

        // Increase game speed progressively
        gameSpeed += 0.001;
    }

    public boolean checkCollision(GameObject obj) {
        // Simple bounding box collision detection
        return obj.getX() < 150 && obj.getX() + 50 > 100 && gokuY + 50 > 500;  // Adjusted collision detection
    }

    public void jump() {
        if (!jumping) {
            jumping = true;
            velocityY = 12;
        }
    }

    public void resetGame() {
        score = 0;
        gameOver = false;
        gameSpeed = 5;
        obstacles.clear();
        for (int i = 0; i < 3; i++) {
            obstacles.add(new Obstacle(800 + i * 300));
        }
        powerUp = new PowerUp(1200);
        powerUpActive = false;
    }

    // Getters for use in View
    public boolean isRunning() {return running;}
    public boolean isJumping() {return jumping;}
    public double getGokuY() { return gokuY; }
    public List<Obstacle> getObstacles() { return obstacles; }
    public PowerUp getPowerUp() { return powerUp; }
    public boolean isGameOver() { return gameOver; }
    public int getScore() { return score; }
    public boolean isPowerUpActive() { return powerUpActive; }
    public Enemy getEnemy(){return  enemy;}
    public int getMaxHealth() { return maxHealth; }
    public int getCurrentHealth() { return currentHealth; }
}
