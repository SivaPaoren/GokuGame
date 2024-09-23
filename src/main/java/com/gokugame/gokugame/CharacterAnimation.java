package com.gokugame.gokugame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CharacterAnimation {
    private Image[] runFrames;
    private Image jumpFrame;
    private int frameIndex = 0;
    private int frameCount = 0;

    public CharacterAnimation(String[] runImagePaths, String jumpImagePath) {
        runFrames = new Image[runImagePaths.length];
        for (int i = 0; i < runImagePaths.length; i++) {
            runFrames[i] = new Image("file:" + runImagePaths[i]);
        }
        jumpFrame = new Image("file:" + jumpImagePath);
    }

    public void render(GraphicsContext gc, double characterX, double characterY, boolean isJumping) {
        if (isJumping) {
            gc.drawImage(jumpFrame, characterX, characterY, 40, 70);
        } else {
            drawRunning(gc, characterX, characterY);
        }
    }

    private void drawRunning(GraphicsContext gc, double characterX, double characterY) {
        frameCount++;
        if (frameCount % 10 == 0) {
            frameIndex = (frameIndex + 1) % runFrames.length;
        }
        gc.drawImage(runFrames[frameIndex], characterX, characterY, 40, 70);
    }
}
