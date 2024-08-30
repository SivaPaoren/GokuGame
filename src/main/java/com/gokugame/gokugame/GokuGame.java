package com.gokugame.gokugame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class GokuGame extends Application {

    private double gokuY = 300;
    private double velocityY = 0;
    private boolean jumping = false;
    private final double GRAVITY = 0.5;
    private int score = 0;

    private Image gokuImage = new Image("file:src/main/resources/goku.png");
    private Image obstacleImage = new Image("file:src/main/resources/obstacle.png");

    private double obstacleX = 600;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(800, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Pane root = new Pane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(this::handleKeyPress);
        scene.setOnKeyReleased(this::handleKeyRelease);

        primaryStage.setTitle("Goku Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw(gc);
            }
        };
        timer.start();
    }

    private void update() {
        if (jumping) {
            velocityY -= GRAVITY;
            gokuY -= velocityY;
            if (gokuY >= 300) {
                gokuY = 300;
                jumping = false;
                velocityY = 0;
            }
        }

        obstacleX -= 5;
        if (obstacleX < -50) {
            obstacleX = 800;
        }
    }

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 400);
        gc.drawImage(gokuImage, 100, gokuY, 50, 50);
        gc.drawImage(obstacleImage, obstacleX, 300, 50, 50);
        gc.drawImage(obstacleImage, obstacleX, 290, 40, 30);
        drawScoreBoard(gc);
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case SPACE:
                if (!jumping) {
                    jumping = true;
                    velocityY = 10;
                }
                break;
            default:
                break;
        }
    }

    private void handleKeyRelease(KeyEvent event) {
        // You can handle key releases if needed
    }


    //currently the width and height of score board is fixed here
    private void drawScoreBoard(GraphicsContext gc){
        //position of the text box
        double x = 8;
        double y = 3;
        double width = 120;
        double height = 100;

        //Draw the text box background
        gc.setFill(Color.AQUAMARINE);  //background color is set here
        gc.fillRect(x,y,width,height);

        //Optionally , draw a border around the text box
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(x,y,width,height);

        //Draw text inside the box
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 20));
        gc.fillText("Score: " + score,x+30,y+30);


    }

    public static void main(String[] args) {
        launch(args);
    }
}
