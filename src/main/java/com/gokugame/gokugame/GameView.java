package com.gokugame.gokugame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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

        // Draw score
        gc.setFill(Color.BLACK);
        gc.fillText("Score: " + logic.getScore(), 700, 50);

        // Draw "Game Over" if the game ends
        if (logic.isGameOver()) {
            gc.setFill(Color.RED);
            gc.fillText("GAME OVER", 350, 200);
        }
    }
}

