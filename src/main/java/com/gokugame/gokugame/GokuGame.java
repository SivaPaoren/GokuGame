package com.gokugame.gokugame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class GokuGame extends Application {

    private GameLogic logic = new GameLogic();
    private GameView view = new GameView();
    private static AudioClip jumpSound = null;
    private final AudioClip backgroundMusic;
    private long previousTime = System.nanoTime();
    static String playerName = "";  // Store player name here

    public GokuGame() {
        // Load the audio clips
        jumpSound = new AudioClip(getClass().getResource("/jump.mp3").toExternalForm());
        backgroundMusic = new AudioClip(getClass().getResource("/background.mp3").toExternalForm());
    }

    @Override
    public void start(Stage primaryStage) {
        showWelcomePage(primaryStage);
    }

    private void showWelcomePage(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        setBackgroundImage(root);

        Text welcomeText = createWelcomeText();
        root.getChildren().add(welcomeText);

        TextField nameInput = createNameInput();
        root.getChildren().add(nameInput);

        Button playButton = createPlayButton(primaryStage, nameInput);
        root.getChildren().add(playButton);

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Welcome to Goku Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setBackgroundImage(AnchorPane root) {
        String[] src = {
                "file:src/main/resources/background3.png",
                "file:src/main/resources/background1.jpg",
        };

        // Background images are generated randomly
        Random random = new Random();
        int randomNum = random.nextInt(src.length);
        Image backgroundImage = new Image(src[randomNum]);
        ImageView background = new ImageView(backgroundImage);
        background.setFitWidth(1000);
        background.setFitHeight(700);
        root.getChildren().add(background);
    }

    private Text createWelcomeText() {
        Text welcomeText = new Text("Welcome to Goku Game!");
        welcomeText.setFont(Font.font("Arial", 40));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setLayoutX(300);
        welcomeText.setLayoutY(100);
        return welcomeText;
    }

    private TextField createNameInput() {
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter your name");
        nameInput.setLayoutX(430);
        nameInput.setLayoutY(310);
        return nameInput;
    }

    private Button createPlayButton(Stage primaryStage, TextField nameInput) {
        Button playButton = new Button("Play");
        playButton.setLayoutX(480);
        playButton.setLayoutY(340);
        playButton.setStyle("-fx-background-color: linear-gradient(to right, #ff7e5f, #feb47b); " +
                "-fx-text-fill: white; -fx-font-size: 20; -fx-padding: 10 20; -fx-border-radius: 10; -fx-background-radius: 10;");

        playButton.setOnAction(event -> {
            playerName = nameInput.getText();
            if (!playerName.isEmpty()) {
                startGame(primaryStage);
            } else {
                System.out.println("Please enter your name!");
            }
        });

        return playButton;
    }

    private void startGame(Stage primaryStage) {
        // Set up the game canvas
        Canvas canvas = new Canvas(1000, 700);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Pane root = new Pane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(this::handleKeyPress);

        primaryStage.setTitle("Goku Game - Player: " + playerName);
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
                    logic.update(getDeltaTime());
                    view.render(gc, logic);
                } else {
                    view.render(gc, logic);  // Render Game Over screen
                    stop();
                }
            }
        };
        timer.start();
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case SPACE:
                if (!logic.isGameOver()) {
                    logic.jump();
                }
                break;
            case P:  // Press 'P' to activate the power up
                if (!logic.isGameOver()) {
                    logic.activatePowerUp();
                }
                break;
            case S:  // Press 'S' to shoot
                if (!logic.isGameOver()) {
                    logic.shootGoku();
                }
                break;
            case R:  // Press 'R' to restart the game after it's over
                if (logic.isGameOver()) {
                    logic.resetGame();  // Reset game logic
                    Stage stage = (Stage) ((Scene) event.getSource()).getWindow();  // Correct way to get the Stage
                    startGame(stage);  // Restart the game

                }
                break;
            default:
                break;
        }
    }

    public static void playJumpSound() {
        new Thread(() -> {
            try {
                jumpSound.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private double getDeltaTime() {
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - previousTime) / 1000000000.0; // Convert to seconds
        previousTime = currentTime;
        return deltaTime;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
