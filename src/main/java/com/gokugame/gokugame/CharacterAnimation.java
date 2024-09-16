package com.gokugame.gokugame;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CharacterAnimation {
    private Image[] runFrames;  // Array to store running frames
    private Image jumpFrame;    // Image for jump frame
    private int frameIndex = 0;  // Index to track which running frame to show
    private int frameCount = 0;  // Counter to control frame rate

    public CharacterAnimation(String[] runImagePaths, String jumpImagePath) {
        // Load running frames
        runFrames = new Image[runImagePaths.length];
        for (int i = 0; i < runImagePaths.length; i++) {
            runFrames[i] = new Image("file:" + runImagePaths[i]);
        }
        // Load jump frame
        jumpFrame = new Image("file:" + jumpImagePath);
    }

    public void render(GraphicsContext gc, double characterX, double characterY, boolean isJumping) {
        if (isJumping) {
            gc.drawImage(jumpFrame, characterX, characterY, 50, 70);  // Show jump frame when jumping
        } else {
            drawRunning(gc, characterX, characterY);  // Show running frames when not jumping
        }
    }

    private void drawRunning(GraphicsContext gc, double characterX, double characterY) {
        // Increment frame counter for smooth animation
        frameCount++;
        if (frameCount % 10 == 0) {  // Change frame every 10 game loops
            frameIndex = (frameIndex + 1) % runFrames.length;
        }
        gc.drawImage(runFrames[frameIndex], characterX, characterY, 50, 70);  // Draw the current running frame
    }
}
