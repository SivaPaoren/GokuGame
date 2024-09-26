package com.gokugame.gokugame;

import java.util.ArrayList;
import java.util.List;

import static com.gokugame.gokugame.GokuGame.playJumpSound;

public class GameLogic {
    private double gameSpeed = 0;
    private final Goku goku = new Goku(100, gameSpeed,40, 70);
    private double gokuY = goku.getY();
    private double velocityY = 0;
    private boolean jumping = false;
    private boolean running = true;
    private final double GRAVITY = 0.6;
    private int score = 0;
    private final List<Projectile> projectiles = new ArrayList<>();
    private boolean gameOver = false;
    private final List<Obstacle> obstacles = new ArrayList<>();
    private final Enemy enemy = new Enemy(900);
    private PowerUp powerUp = new PowerUp(100,gameSpeed,40,70);;
    private boolean isPowerFull = false;
    private int maxHealth = 100;
    private boolean isEnemyVisible = false;

    //this one is hard coded for now to test we need to make it dynamic for later
    private int currentHealth = 100;
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

        //enemy shooting
        if(Math.random() < 0.01) {
            // Adjust probability as needed
                shootEnemy();

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

        //Update projectiles
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);
            projectile.move(projectile.getSpeed());

            // Check for collisions with Goku or obstacles
            if (checkCollisionWithGoku(projectile) || projectile.isOutOfScreen()) {
                if (checkCollisionWithGoku(projectile)) {
                    // Reduce health
                    currentHealth -= 10; // Adjust damage as needed
                    if (currentHealth <= 0) {
                        gameOver = true;
                    }
                }
                projectiles.remove(i);
                i--; // Adjust index after removal
            }
        }

        if(isGameOver()){
            String playeName = GokuGame.playerName;
            int score = getScore();

            handleGameOver(playeName, score);
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
            playJumpSound();
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
            Obstacle obstacle = new Obstacle(800 + i * 300,gameSpeed, 50, 50);
            obstacles.add(obstacle);
        }
        isPowerFull = false;
        powerUpFlying = false;
        powerUpTimer = 0.0;
    }

    public void shootGoku() {
        if (powerUpFlying) {
            // Create a projectile shot by Goku
            System.out.println("Insdie the shootgoku");
           projectiles.add(new Projectile(powerUp.getX()+powerUp.getWidth(), powerUp.getY()+5, 5, false)); // Adjust speed as needed
        }
    }

    private void handleGameOver(String playerName,int score) {
        ScoreWriter scoreWriter = new ScoreWriter();
        scoreWriter.writeScoreToFile(playerName, score);
    }

    public void shootEnemy() {
        // Create a projectile shot by the enemy
        if(isEnemyVisible) {  //Only if the enemy comes to screen then he will be able to shoot
            projectiles.add(new Projectile(enemy.getX()-enemy.getWidth(), enemy.getY()+20, -6, true)); // Adjust speed as needed
        }
    }

    private boolean checkCollisionWithGoku(Projectile projectile) {
        return projectile.getX() < 150 && projectile.getX() + projectile.getWidth() > goku.getX() && gokuY + 50 > projectile.getY();
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
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
    public boolean getIsEnemyVisible(){return isEnemyVisible;}

    // Setters for use in view
    public void setEnemyVisible(boolean visibility){this.isEnemyVisible = visibility;}
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