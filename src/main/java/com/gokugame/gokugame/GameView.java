package com.gokugame.gokugame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class GameView {
    private Image gokuImage = new Image("file:src/main/resources/goku.png");
    private Image obstacleImage = new Image("file:src/main/resources/obstacle.png");
    private Image powerUpImage = new Image("file:src/main/powerup.png");

    public void render(GraphicsContext gc, GameLogic logic) {
        gc.clearRect(0, 0, 800, 400);  // Clear screen

        // Draw Goku
        gc.drawImage(gokuImage, 100, logic.getGokuY(), 50, 50);

        // Draw obstacles
        for (Obstacle obstacle : logic.getObstacles()) {
            gc.drawImage(obstacleImage, obstacle.getX(), obstacle.getY(), 50, 50);
        }

        // Draw power-up if active
        if (logic.getPowerUp() != null) {
            gc.drawImage(powerUpImage, logic.getPowerUp().getX(), logic.getPowerUp().getY(), 50, 50);
        }

        //score board is create here
        drawScoreboard(gc, logic);


        // Draw "Game Over" if the game ends
        if (logic.isGameOver()) {
            gc.setFill(Color.RED);
            gc.fillText("GAME OVER", 350, 200);
        }
    }

    //Score board is created here
    private void drawScoreboard(GraphicsContext gc,GameLogic logic) {

        // Draw background
        gc.setFill(Color.BLACK);
        gc.fillRect(435, 5, 140, 80);

        // Draw border
        gc.setStroke(Color.HOTPINK);
        gc.setLineWidth(5);
        gc.strokeRect(435, 5, 140, 80);

        // Draw title
        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font("Arial", 20));
        gc.fillText("Score", 480, 34);

        // Draw scores
        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font("Arial", 20));
        gc.fillText(String.valueOf(logic.getScore()), 500, 60);


    }
}

