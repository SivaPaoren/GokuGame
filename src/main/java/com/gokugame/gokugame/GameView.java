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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class GameView {

    private Image gokuImage = new Image("file:src/main/resources/goku.png");
    private Image obstacleImage = new Image("file:src/main/resources/obstacle.png");
    private Image powerUpImage = new Image("file:src/main/powerup.png");

    private MediaPlayer mediaPlayer;

    public GameView() {

        String musicFile = "src/main/resources/background.mp3";
        Media backgroundMusic = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(backgroundMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

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

        // Draw scoreboard
        drawScoreboard(gc, logic);

        // Draw "Game Over" if the game ends
        if (logic.isGameOver()) {
            gc.setFill(Color.RED);
            gc.fillText("GAME OVER", 350, 200);
            mediaPlayer.stop(); // Stop the music when the game is over
        }
    }

    // Scoreboard is created here
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
}

