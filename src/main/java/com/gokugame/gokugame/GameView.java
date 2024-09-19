package com.gokugame.gokugame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView {

    private CharacterAnimation gokuAnimation;  // Use the new reusable animation class
    private Image obstacleImage = new Image("file:src/main/resources/obstacle.png");
    private Image powerUpImage = new Image("file:src/main/resources/powerup.png");
    private Image backgroundImage = new Image("file:src/main/resources/background.png");
    private Image enemyImage = new Image("file:src/main/resources/enemy.png");

    public GameView() {
        // Pass the file paths of the Goku running and jumping images

        //here we can later add different character like vegeta and other players
        //goku character is created here
        String[] gokuRunImages = {
                "src/main/resources/goku_run1.png",
                "src/main/resources/goku_run2.png",
                "src/main/resources/goku_run3.png"
        };

        String gokuJumpImage = "src/main/resources/goku_jump.jpeg";



        gokuAnimation = new CharacterAnimation(gokuRunImages, gokuJumpImage);  // Initialize the animation

    }

    public void render(GraphicsContext gc, GameLogic logic) {
        gc.clearRect(0, 0, 1000, 700);  // Clear screen

        // Draw background
        gc.drawImage(backgroundImage, 0, 0, 1000, 700);

        // Draw Goku using CharacterAnimation
        gokuAnimation.render(gc, 100, logic.getGokuY(), logic.isJumping());

        //Draw enemy using
        gc.drawImage(enemyImage,logic.getEnemy().getX(),logic.getEnemy().getY(),40,90);


        // For the left-most side
        drawCharacterHealth(gc, logic, 10, 10, false);

        // For the right-most side
        double canvasWidth = gc.getCanvas().getWidth();
        drawCharacterHealth(gc, logic, canvasWidth - 210, 10, true);


        // Draw obstacles
        for (Obstacle obstacle : logic.getObstacles()) {
            gc.drawImage(obstacleImage, obstacle.getX(), obstacle.getY(), 50, 50);
        }

        // Draw power-up
        if (logic.getPowerUp() != null) {
            gc.drawImage(powerUpImage, logic.getPowerUp().getX(), logic.getPowerUp().getY(), 50, 50);
        }

        // Draw scoreboard
        drawScoreboard(gc, logic);

        // Draw "Game Over" message if the game is over
        if (logic.isGameOver()) {
            gc.setFill(Color.RED);
            gc.setFont(Font.font("Arial", 50));
            gc.fillText("GAME OVER", 350, 200);
        }
    }

    private void drawScoreboard(GraphicsContext gc, GameLogic logic) {
        // Draw background
        gc.setFill(Color.BLACK);
        gc.fillRect(435, 5, 140, 80);

        // Draw border
        gc.setStroke(Color.HOTPINK);
        gc.setLineWidth(5);
        gc.strokeRect(435, 5, 140, 80);

        // Draw title
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 20));
        gc.fillText("Score", 480, 34);

        // Draw scores
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 20));
        gc.fillText(String.valueOf(logic.getScore()), 500, 60);
    }

    private void drawCharacterHealth(GraphicsContext gc, GameLogic logic, double x, double y, boolean isRightSide) {
        // Assuming logic has methods to get character health and max health
        double currentHealth = logic.getCurrentHealth();
        double maxHealth = logic.getMaxHealth();

        //width of the window
        double canvasWidth = gc.getCanvas().getWidth();

        // Calculate health bar width based on the current health
        double healthBarWidth = 200; // Width of the full health bar
        double healthPercentage = currentHealth / maxHealth;
        double currentHealthBarWidth = healthBarWidth * healthPercentage;

        // Draw character image
        Image characterImage = new Image("file:src/main/resources/characterface.jpg");
        // Update with actual image path
        if(isRightSide){
            gc.drawImage(characterImage, canvasWidth - 110, y, 100, 100);
        }else{
            gc.drawImage(characterImage, x, y, 100, 100);
        }

        // Health bar y position, adjust as needed
        double healthBarY = y + 100; // Align health bar just below the character image

        // Draw the background of the health bar (black)
        gc.setFill(Color.BLACK);
        if (isRightSide) {
            // Draw background from the right
            gc.fillRect(x, healthBarY, healthBarWidth, 20); // Position background on the right side
        } else {
            // Draw background from the left
            gc.fillRect(x, healthBarY, healthBarWidth, 20); // Position background on the left side
        }

        // Draw the current health bar with color changes based on health percentage
        if (healthPercentage >= 0.5 && healthPercentage <= 0.7) {
            gc.setFill(Color.YELLOW);
        } else if (healthPercentage < 0.5) {
            gc.setFill(Color.RED);
        } else {
            gc.setFill(Color.LIGHTGREEN);
        }
        if (isRightSide) {
            // Draw health bar from the right side
            gc.fillRect(x + healthBarWidth - currentHealthBarWidth, healthBarY, currentHealthBarWidth, 20); // Draw reverse health bar
        } else {
            // Draw health bar from the left side
            gc.fillRect(x, healthBarY, currentHealthBarWidth, 20); // Draw proportional to current health
        }

        // Optionally, you can also draw the health value text if needed

    }




}
