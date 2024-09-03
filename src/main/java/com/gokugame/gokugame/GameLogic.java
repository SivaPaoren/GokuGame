package com.gokugame.gokugame;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private double gokuY = 300;
    private double velocityY = 0;
    private boolean jumping = false;
    private final double GRAVITY = 0.5;
    private int score = 0;
    private boolean gameOver = false;
    private List<Obstacle> obstacles = new ArrayList<>();
    private PowerUp powerUp;
    private double gameSpeed = 5;
    private boolean powerUpActive = false;

    public GameLogic() {
        // Initialize obstacles and power-up
        resetGame();
    }

    public void update() {
        // Goku's jump logic
        if (jumping) {
            velocityY -= GRAVITY;
            gokuY -= velocityY;
            if (gokuY >= 300) {
                gokuY = 300;
                jumping = false;
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
        return obj.getX() < 150 && obj.getX() + 50 > 100 && gokuY + 50 > 300;
    }

    public void jump() {
        if (!jumping) {
            jumping = true;
            velocityY = 10;
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
    public double getGokuY() { return gokuY; }
    public List<Obstacle> getObstacles() { return obstacles; }
    public PowerUp getPowerUp() { return powerUp; }
    public boolean isGameOver() { return gameOver; }
    public int getScore() { return score; }
    public boolean isPowerUpActive() { return powerUpActive; }
}

