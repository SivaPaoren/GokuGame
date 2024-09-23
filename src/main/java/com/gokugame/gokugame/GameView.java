package com.gokugame.gokugame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView {
    private Image obstacleImage = new Image("file:src/main/resources/obstacle.png");
    private Image powerUpImage = new Image("file:src/main/resources/goku_run2.png"); //power up image is added here
    private Image backgroundImage = new Image("file:src/main/resources/background.jpg");
    private Image enemyImage = new Image("file:src/main/resources/enemy.png");


    public void render(GraphicsContext gc, GameLogic logic) {
        gc.clearRect(0, 0, 1000, 700);  // Clear screen

        // Draw background
        gc.drawImage(backgroundImage, 0, 0, 1000, 700);

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
        if(!logic.isGameOver()&& logic.getScore() >= 10){
            //Draw enemy using
            gc.drawImage(enemyImage,logic.getEnemy().getX(),logic.getEnemy().getY(),40,100); // x,y,width,height

            // Draw the health bar on the right side
            drawCharacterHealth(gc, logic, canvasWidth - 210, 10, true);
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
            gc.setFont(Font.font("Arial", 30));
            gc.fillText("Click P to power up", 350, 640);
            gc.fillText("Click S to shoot enemy", 350, 680);
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
        Image characterImage = new Image("file:src/main/resources/characterface.jpg");
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
            gc.drawImage(characterImage, x+80, y, 100, 100); // Character image
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

    // Method to draw the power bar with a power-related image
    private void drawPowerBar(GraphicsContext gc, GameLogic logic, double x, double y, boolean isRightSide) {
        // Assuming logic has a method to get the current score

        double maxScore = 3; // Max score for full power
        //here check the constion if the power is used already or not
        double score = logic.getScore() % maxScore;
        if(logic.getPowerUpFlying() || score == 0 && logic.getScore() != 0){ //if ,mod give 0 means power is full
            score = maxScore;
        }

        double powerPercentage = Math.min(score / maxScore, 1.0); // Ensure it's between 0 and 1
        Color powerBorderColor = null;
        Color powerColor = null;

        double barWidth = 180;
        double barHeight = 20;

        // Load the power image (e.g., lightning bolt icon)
        Image powerImage = null;

        //if power becomes full chan
        // ges the color to full power
        if(powerPercentage < 1.0){
            powerImage = new Image("file:src/main/resources/power.png");
            powerBorderColor = Color.BLUE;  //border color set to yellow
            powerColor = Color.GOLD;  //power color set to gold
        }else{
            powerImage = new Image("file:src/main/resources/power-full.png");
            powerBorderColor = Color.YELLOW;
            powerColor = Color.LIGHTCYAN;
            logic.setIsPowerFull(true);//power up is set true here
        }

        // Window width for right-side alignment
        double canvasWidth = gc.getCanvas().getWidth();

        // Draw the power image and bar
        if (isRightSide) {
            gc.drawImage(powerImage, canvasWidth - 210, y , 20, 20); // Power image
            gc.setFill(Color.DARKCYAN);
            gc.fillRect(canvasWidth - 210, y, barWidth, barHeight); // Power bar background
            gc.setFill(powerColor);
            gc.fillRect(canvasWidth - 210, y, barWidth * powerPercentage, barHeight); // Current power
        } else {
            gc.drawImage(powerImage, x - 30, y , 20, 20); // Power image
            gc.setFill(Color.DARKCYAN);
            gc.fillRect(x, y, barWidth, barHeight); // Power bar background
            gc.setFill(powerColor); // color of power is set here
            gc.fillRect(x, y, barWidth * powerPercentage, barHeight); // Current power
        }

        // Optional: Draw a border around the power bar
        gc.setStroke(powerBorderColor);
        if (isRightSide) {
            gc.strokeRect(canvasWidth - 210, y, barWidth, barHeight);
        } else {
            gc.strokeRect(x, y, barWidth, barHeight);
        }
    }







}
