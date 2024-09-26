package com.gokugame.gokugame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreWriter {

    // Method to write player name and score to file
    public void writeScoreToFile(String playerName, int score) {
        String fileName = "player_scores.txt";  // The file to store the scores

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("Player: " + playerName + ", Score: " + score);
            writer.newLine();  // Write a new line for each player
        } catch (IOException e) {
            System.out.println("An error occurred while writing the score to the file.");
            e.printStackTrace();
        }
    }
}
