package com.gokugame.gokugame;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private double gameSpeed = 0;
    private final Goku goku = new Goku(100, gameSpeed,40, 70);
    private double gokuY = goku.getY();
    private double velocityY = 0;
    private boolean jumping = false;
    private boolean running = true;
    private final double GRAVITY = 0.6;
    private int score = 0;

    private boolean gameOver = false;
    private final List<Obstacle> obstacles = new ArrayList<>();
    private final Enemy enemy = new Enemy(900);
    private PowerUp powerUp = new PowerUp(100,gameSpeed,40,70);;
    private boolean isPowerFull = false;
    private int maxHealth = 100;

    //this one is hard coded for now to test we need to make it dynamic for later
    private int currentHealth = 80;
    private boolean powerUpFlying = false; // Flag to indicate if Goku is flying due to power-up
    private double powerUpDuration = 5; // Duration of the power-up flying state in seconds
    private double powerUpTimer; // Timer for the power-up flying state

    public GameLogic() {
        // Initialize obstacles and power-up
        resetGame();
    }

    public void update(double deltaTime) {
        // Goku's jump logic
        if (jumping) {
            running = false;
            velocityY -= GRAVITY;
            gokuY -= velocityY;
            if (gokuY >= 480) {
                gokuY = 480;
                jumping = false;
                running = true;
                velocityY = 0;
            }
        }
        // Move and reset obstacles

        for (Obstacle obstacle : obstacles) {
            obstacle.move(gameSpeed);
            if (obstacle.isOutOfScreen()) {
                obstacle.resetPosition(1000);
                score++;
            }
        }

        // Power-up logic
        if (powerUpFlying) {
            // Power-up collision with Goku
            if (checkCollision(powerUp)) {
                isPowerFull = false;
                powerUp.resetPosition(100);
                activatePowerUp();
            }
        }

        // Power-up flying logic
        if (powerUpFlying) {
            powerUpTimer += deltaTime;
            if (powerUpTimer >= powerUpDuration) {

                obstacles.forEach(obstacle -> {   //this part check when the character fall down it does not hit the obstacle and die right away
                    if (!checkCollision(obstacle)) {
                        powerUpFlying = false;
                        powerUpTimer = 0.0;
                        jumping = true;
                    }
                });
            }
        }

        // Detect collisions with obstacles
        for (Obstacle obstacle : obstacles) {
            if ( !powerUpFlying && checkCollision(obstacle)) {
                gameOver = true;
                return;
            }
        }

        // Increase game speed progressively
        gameSpeed += 0.0005;
    }

    public boolean checkCollision(GameObject obj) {
        // Simple bounding box collision detection
        if (powerUpFlying) {
            // Calculate a larger hitbox for Goku while flying
            double gokuFlyingHeight = 400; // Adjust this value as
            return obj.getX() < 150 && obj.getX() + 50 > 100 && gokuY + gokuFlyingHeight > 500;
        }

        return obj.getX() < 150 && obj.getX() + 50 > 100 && gokuY + 50 > 500;
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
            Obstacle obstacle = new Obstacle(800 + i * 350,gameSpeed, 50, 50);
            obstacles.add(obstacle);
        }
        isPowerFull = false;
        powerUpFlying = false;
        powerUpTimer = 0.0;
        System.out.println("Reset game is called");
    }

    // Getters for use in View
    public boolean isRunning() { return running; }
    public boolean isJumping() { return jumping; }
    public double getGokuY() { return gokuY; }
    public List<Obstacle> getObstacles() { return obstacles; }
    public PowerUp getPowerUp() { return powerUp; }
    public boolean isGameOver() { return gameOver; }
    public int getScore() { return score; }
    public boolean getIsPowerFull() { return isPowerFull; }
    public Enemy getEnemy() { return enemy; }
    public int getMaxHealth() { return maxHealth; }
    public int getCurrentHealth() { return currentHealth; }
    public Goku getGoku() { return goku; }
    public boolean getPowerUpFlying() { return powerUpFlying; }

    // Setters for use in view
    public void setIsPowerFull(boolean isPowerFull) { this.isPowerFull = isPowerFull; }
    public void activatePowerUp() {
        if(isPowerFull) {
            powerUpFlying = true;
            powerUpTimer = 0.0;
        }else {
            return;
        }
    }
}