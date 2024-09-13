  package com.gokugame.gokugame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;

public class GokuGame extends Application {

    private GameLogic logic = new GameLogic();
    private GameView view = new GameView();
    private final AudioClip jumpSound;
    private final AudioClip backgroundMusic;

    public GokuGame() {
        // Load the audio clips
        jumpSound = new AudioClip(getClass().getResource("/jump.mp3").toExternalForm());
        backgroundMusic = new AudioClip(getClass().getResource("/background.mp3").toExternalForm());
    }

    @Override
    public void start(Stage primaryStage) {
        // Set up the game canvas
        Canvas canvas = new Canvas(1000, 700);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Pane root = new Pane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(this::handleKeyPress);

        primaryStage.setTitle("Goku Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Play background music
        backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        backgroundMusic.play();

        // Game loop using AnimationTimer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!logic.isGameOver()) {
                    logic.update();
                    view.render(gc, logic);
                } else {
                    view.render(gc, logic);
                    stop();  // Stop the game loop if game over
                }
            }
        };
        timer.start();
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case SPACE:
                logic.jump();
                jumpSound.play();  // Play jump sound
                break;
            case R:  // Press 'R' to reset the game after game over
                if (logic.isGameOver()) {
                    logic.resetGame();
                }
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
