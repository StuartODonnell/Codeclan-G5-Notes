package com.codeclan.example.rockpaperscissorslizardspock;

import java.util.Random;

/**
 * Created by sandy on 20/10/2016.
 */
public class Game {

    private int userWins;
    private int androidWins;

    public Game() {
        userWins = 0;
        androidWins = 0;
    }

    public Selection getComputerSelection() {
        Selection[] options = Selection.values();
        Random rand = new Random();
        int index = rand.nextInt(options.length);
        return options[index];
    }

    public Winner getWinner(Selection userChoice, Selection androidChoice) {

        Winner result = Winner.DRAW;

        if (userChoice == androidChoice) {
            result =  Winner.DRAW;
            return result;
        }

        switch (userChoice) {
            case ROCK:
                result = ((androidChoice == Selection.SCISSORS) || (androidChoice == Selection.LIZARD)) ? Winner.USER : Winner.ANDROID;
                break;
            case PAPER:
                result = ((androidChoice == Selection.ROCK) || (androidChoice == Selection.SPOCK)) ? Winner.USER : Winner.ANDROID;
                break;
            case SCISSORS:
                result = ((androidChoice == Selection.PAPER) || (androidChoice == Selection.LIZARD)) ? Winner.USER : Winner.ANDROID;
                break;
            case LIZARD:
                result = ((androidChoice == Selection.PAPER) || (androidChoice == Selection.SPOCK)) ? Winner.USER : Winner.ANDROID;
                break;
            case SPOCK:
                result = ((androidChoice == Selection.ROCK) || (androidChoice == Selection.SCISSORS)) ? Winner.USER : Winner.ANDROID;
        }

        updateScores(result);
        return result;
    }

    private void updateScores(Winner winner) {
        if (winner ==  Winner.ANDROID) {
            androidWins++;
        } else if (winner == Winner.USER) {
            userWins++;
        }
    }

    public int getUserNumWins() {
        return this.userWins;
    }

    public int getAndroidNumWins() {
        return this.androidWins;
    }
}
