package com.gokugame.gokugame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

public class GameView {
    private final Image obstacleImage = new Image("file:src/main/resources/obstacle2.png");

    //background images locations in array to generate randomly
    Random random = new Random();
    String [] backgroundImagesSources = {
                    "file:src/main/resources/tournament.jpeg",
                    "file:src/main/resources/tournament0.jpg",
                    "file:src/main/resources/tournament2.jpeg",
                     "file:src/main/resources/tournament3.jpeg",
                      "file:src/main/resources/tournament4.jpeg"
    };
    int randomNumber = random.nextInt(backgroundImagesSources.length);
    private final Image powerUpImage = new Image("file:src/main/resources/animation6.png"); //power up image is added here
    private final Image backgroundImage = new Image(backgroundImagesSources[randomNumber]);//background imge is set here with random image is sekected
    private final Image enemyImage = new Image("file:src/main/resources/enemy2.png");
    private final Image enemyFace = new Image("file:src/main/resources/enemyface.png");


    public void render(GraphicsContext gc, GameLogic logic) {
        gc.clearRect(0, 0, 1000, 700);  // Clear screen

        // Draw background
        gc.drawImage(backgroundImage, 0, 0, 1000, 700);

        if(logic.getIsEnemyVisible()){
            for (Projectile projectile : logic.getProjectiles()) {
                gc.setFill(projectile.isEnemy() ? Color.RED : Color.GREEN); // Color based on who shot it
                gc.fillRect(projectile.getX(), projectile.getY(), projectile.getWidth(), projectile.getHeight());
            }
        }


        // Draw power-up , width and height are passed as argument to crate image
        if (logic.getPowerUpFlying()) {
            gc.drawImage(powerUpImage, logic.getPowerUp().getX(), logic.getPowerUp().getY(), logic.getPowerUp().getWidth(), logic.getPowerUp().getHeight());
        }else{
            // Draw Goku using CharacterAnimation using CHaracterAnimation CLass inside Goku class
           // gc.gokuAnimation.render(gc, 100, logic.getGokuY(), logic.isJumping());
            logic.getGoku().getGokuAnimation().render(gc, logic.getGoku().getX(), logic.getGokuY(), logic.isJumping());
        }
        double canvasWidth = gc.getCanvas().getWidth();


        //THis condition show final boss after the score hit certain level
        if(!logic.isGameOver()&& logic.getScore() >= 5){
            //Draw enemy using
            gc.drawImage(enemyImage,logic.getEnemy().getX(),logic.getEnemy().getY(),40,100); // x,y,width,height

            // Draw the health bar on the right side
            drawCharacterHealth(gc, logic, canvasWidth - 210, 10, true);


            //set the enemy visibility true here so that he can start shooting
            logic.setEnemyVisible(true);
        }


        // For the right-most side

            // Example for calling the health and power bar methods
    // Draw the health bar on the left side
            drawCharacterHealth(gc, logic, 30, 10, false);

    // Draw the power bar on the left side below the health bar
            drawPowerBar(gc, logic, 30, 135, false);

    // Draw the power bar on the right side below the health bar
            //drawPowerBar(gc, logic, canvasWidth - 210, 10, true);



        // Draw obstacles
        for (Obstacle obstacle : logic.getObstacles()) {
            gc.drawImage(obstacleImage, obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
        }



        // Draw scoreboard
        drawScoreboard(gc, logic);

        // Draw "Game Over" message if the game is over
        if (logic.isGameOver()) {
            gc.setFill(Color.RED);
            gc.setFont(Font.font("Arial", 50));
            gc.fillText("GAME OVER", 350, 200);
        }


        //Draw the power is ready part
        if(logic.getIsPowerFull()){
            gc.setFill(Color.RED);
            gc.setFont(Font.font("Arial", 20));
            gc.fillText("Click P to power up", 350, 640);
            gc.fillText("Click S to shoot enemy", 350, 660);
            gc.fillText("Click R restart the Game", 350, 680);
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

    // Method to draw the character's health bar with a health image
    private void drawCharacterHealth(GraphicsContext gc, GameLogic logic, double x, double y, boolean isRightSide) {
        // Assuming logic has methods to get character health and max health
        double currentHealth = logic.getCurrentHealth();
        double maxHealth = logic.getMaxHealth();

        // Load character image and health image (heart icon)
        Image characterImage = new Image("file:src/main/resources/characterface.png");
        Image healthImage;

        // Window width for right-side alignment
        double canvasWidth = gc.getCanvas().getWidth();

        // Calculate health bar width based on the current health
        double healthBarWidth = 180; // Full width of the health bar
        double healthPercentage = currentHealth / maxHealth;
        double currentHealthBarWidth = healthBarWidth * healthPercentage;

        //color of the bar is set here
        Color healthColorBar;

        //color and icon of health are changed here
        if (healthPercentage >= 0.5 && healthPercentage <= 0.7) {
            healthColorBar = Color.YELLOW;
            healthImage = new Image("file:src/main/resources/heart-yellow.png");
        } else if (healthPercentage < 0.5) {
           healthColorBar = Color.RED;
           healthImage = new Image("file:src/main/resources/heart-red.png");
        } else {
            healthColorBar = Color.LIGHTGREEN;
            healthImage = new Image("file:src/main/resources/heart-green.png");
        }



        // Drawing based on side (left or right)
        if (isRightSide) {
            // Right side drawing
            gc.drawImage(healthImage, gc.getCanvas().getWidth()-20, 110, 20, 20); // Health image
            gc.drawImage(enemyFace, x+80, y, 100, 100); // Character image
        } else {
            // Left side drawing
            gc.drawImage(healthImage, x - 30, 110, 20, 20); // Health image
            gc.drawImage(characterImage, x, y, 100, 100); // Character image
        }

        // Health bar y position
        double healthBarY = y + 100; // Just below the character image


        gc.setFill(Color.DARKCYAN); //setting background color for powerbar
        gc.setStroke(healthColorBar);//adding borders to the power bar

        if (isRightSide) {
            gc.strokeRect(canvasWidth - 210, healthBarY, healthBarWidth, 20);
            gc.fillRect(canvasWidth - 210, healthBarY, healthBarWidth, 20); // Background on the right
        } else {
            gc.strokeRect(x, healthBarY, healthBarWidth, 20);
            gc.fillRect(x, healthBarY, healthBarWidth, 20); // Background on the left
        }

        // Set color of the current health bar based on health percentage
        gc.setFill(healthColorBar);


        // Draw the current health bar (proportional to health)
        if (isRightSide) {
            gc.fillRect(canvasWidth - 210 + healthBarWidth - currentHealthBarWidth, healthBarY, currentHealthBarWidth, 20);
        } else {
            gc.fillRect(x, healthBarY, currentHealthBarWidth, 20);
        }


    }

    private void drawPowerBar(GraphicsContext gc, GameLogic logic, double x, double y, boolean isRightSide) {
        double powerPercentage = getPowerPercentage(logic);
        Color powerBorderColor;
        Color powerColor;
        Image powerImage;

        // Determine colors and images based on power level
        if (powerPercentage < 1.0) {
            powerImage = new Image("file:src/main/resources/power.png");
            powerBorderColor = Color.BLUE;  // Border color set to blue
            powerColor = Color.GOLD;  // Power color set to gold
        } else {
            powerImage = new Image("file:src/main/resources/power-full.png");
            powerBorderColor = Color.YELLOW;
            powerColor = Color.LIGHTCYAN;
            logic.setIsPowerFull(true); // Set power up to full
        }

        // Window width for right-side alignment
        double canvasWidth = gc.getCanvas().getWidth();

        // Draw the power image and bar
        if (isRightSide) {
            gc.drawImage(powerImage, canvasWidth - 210, y, 20, 20); // Power image
            gc.setFill(Color.DARKCYAN);
            gc.fillRect(canvasWidth - 210, y, 180, 20); // Power bar background
            gc.setFill(powerColor);
            gc.fillRect(canvasWidth - 210, y, 180 * powerPercentage, 20); // Current power
        } else {
            gc.drawImage(powerImage, x - 30, y, 20, 20); // Power image
            gc.setFill(Color.DARKCYAN);
            gc.fillRect(x, y, 180, 20); // Power bar background
            gc.setFill(powerColor); // Color of power
            gc.fillRect(x, y, 180 * powerPercentage, 20); // Current power
        }

        // Optional: Draw a border around the power bar
        gc.setStroke(powerBorderColor);
        if (isRightSide) {
            gc.strokeRect(canvasWidth - 210, y, 180, 20);
        } else {
            gc.strokeRect(x, y, 180, 20);
        }
    }

    private static double getPowerPercentage(GameLogic logic) {
        double maxScore = 3; // Max score for full power
        double score = logic.getScore(); // Get the current score from logic

        // Logic to handle full power and reset
        if (logic.getIsPowerFull()) {
            // If power is full, keep it full and do not decrease score
            score = maxScore;
        } else {
            // If power is not full, update the score based on usage
            score = Math.min(score, maxScore); // Ensure it doesn't exceed maxScore

        }

        // Calculate the percentage of power
        return score / maxScore;
    }


}
