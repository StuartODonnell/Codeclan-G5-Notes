package com.codeclan.example.rockpaperscissorslizardspock;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    Selection computerChoosesRock = Selection.ROCK;
    Selection computerChoosesScissors = Selection.SCISSORS;
    Selection computerChoosesPaper = Selection.PAPER;
    Selection computerChoosesLizard = Selection.LIZARD;
    Selection computerChoosesSpock = Selection.SPOCK;

    Selection userChoosesRock = Selection.ROCK;
    Selection userChoosesScissors = Selection.SCISSORS;
    Selection userChoosesPaper = Selection.PAPER;
    Selection userChoosesLizard = Selection.LIZARD;
    Selection usersChoosesSpock = Selection.SPOCK;

    Game game;

    @Before
    public void before() {
        game = new Game();
    }

    @Test
    public void testGetComputerSelectionReturnsSelectionType() {
        Selection computerChoice = game.getComputerSelection();
        assertNotNull(computerChoice);
    }

    //USER CHOOSES ROCK

    @Test
    public void testRockBeatsScissors(){
        Winner winner = game.getWinner(userChoosesRock, computerChoosesScissors);
        assertEquals(Winner.USER, winner);
    }

    @Test
    public void testRockLosesToPaper(){
        Winner winner = game.getWinner(userChoosesRock, computerChoosesPaper);
        assertEquals(Winner.ANDROID, winner);
    }

    @Test
    public void testRockDrawsWithRock(){
        Winner winner = game.getWinner(userChoosesRock, computerChoosesRock);
        assertEquals(Winner.DRAW, winner);
    }

    //EXTENSION

    @Test
    public void testRockBeatsLizard(){
        Winner winner = game.getWinner(userChoosesRock, computerChoosesLizard);
        assertEquals(Winner.USER, winner);
    }

    @Test
    public void testRockLosesToSpock(){
        Winner winner = game.getWinner(userChoosesRock, computerChoosesSpock);
        assertEquals(Winner.ANDROID, winner);
    }

    //USER CHOOSES PAPER

    @Test
    public void testPaperBeatsRock(){
        Winner winner = game.getWinner(userChoosesPaper, computerChoosesRock);
        assertEquals(Winner.USER, winner);
    }

    @Test
    public void testPaperLosesToScissors(){
        Winner winner = game.getWinner(userChoosesPaper, computerChoosesScissors);
        assertEquals(Winner.ANDROID, winner);
    }

    @Test
    public void testPaperDrawsWithPaper(){
        Winner winner = game.getWinner(userChoosesPaper, computerChoosesPaper);
        assertEquals(Winner.DRAW, winner);
    }

    //EXTENSION

    @Test
    public void testPaperBeatsSpock(){
        Winner winner = game.getWinner(userChoosesPaper, computerChoosesSpock);
        assertEquals(Winner.USER, winner);
    }

    @Test
    public void testPaperLosesToLizard(){
        Winner winner = game.getWinner(userChoosesPaper, computerChoosesLizard);
        assertEquals(Winner.ANDROID, winner);
    }

    //USER CHOOSES PAPER

    @Test
    public void testScissorsBeatsPaper(){
        Winner winner = game.getWinner(userChoosesScissors, computerChoosesPaper);
        assertEquals(Winner.USER, winner);
    }

    @Test
    public void testScissorsLosesToRock(){
        Winner winner = game.getWinner(userChoosesScissors, computerChoosesRock);
        assertEquals(Winner.ANDROID, winner);
    }

    @Test
    public void testScissorsDrawsWithScissors(){
        Winner winner = game.getWinner(userChoosesScissors, computerChoosesScissors);
        assertEquals(Winner.DRAW, winner);
    }

    //EXTENSION

    @Test
    public void testScissorsBeatsLizard(){
        Winner winner = game.getWinner(userChoosesScissors, computerChoosesLizard);
        assertEquals(Winner.USER, winner);
    }

    @Test
    public void testScissorsLosesToSpock(){
        Winner winner = game.getWinner(userChoosesScissors, computerChoosesSpock);
        assertEquals(Winner.ANDROID, winner);
    }

    // USER CHOOSES LIZARD

    @Test
    public void testLizardBeatsSpock(){
        Winner winner = game.getWinner(userChoosesLizard, computerChoosesPaper);
        assertEquals(Winner.USER, winner);
    }

    @Test
    public void testLizardBeatsPaper(){
        Winner winner = game.getWinner(userChoosesLizard, computerChoosesPaper);
        assertEquals(Winner.USER, winner);
    }

    @Test
    public void testLizardLosesToRock(){
        Winner winner = game.getWinner(userChoosesLizard, computerChoosesRock);
        assertEquals(Winner.ANDROID, winner);
    }

    @Test
    public void testLizardLosesToScissors(){
        Winner winner = game.getWinner(userChoosesLizard, computerChoosesScissors);
        assertEquals(Winner.ANDROID, winner);
    }

    @Test
    public void testScissorsDrawsWithLizard(){
        Winner winner = game.getWinner(userChoosesLizard, computerChoosesLizard);
        assertEquals(Winner.DRAW, winner);
    }

    // USER CHOOSES SPOCK

    @Test
    public void testSpockBeatsScissors(){
        Winner winner = game.getWinner(usersChoosesSpock, computerChoosesScissors);
        assertEquals(Winner.USER, winner);
    }

    @Test
    public void testSpockBeatsRock(){
        Winner winner = game.getWinner(usersChoosesSpock, computerChoosesRock);
        assertEquals(Winner.USER, winner);
    }

    @Test
    public void testSpockLosesToPaper(){
        Winner winner = game.getWinner(usersChoosesSpock, computerChoosesPaper);
        assertEquals(Winner.ANDROID, winner);
    }

    @Test
    public void testSpockLosesToLizard(){
        Winner winner = game.getWinner(usersChoosesSpock, computerChoosesLizard);
        assertEquals(Winner.ANDROID, winner);
    }

    @Test
    public void testSpockDrawsWithSpock(){
        Winner winner = game.getWinner(usersChoosesSpock, computerChoosesSpock);
        assertEquals(Winner.DRAW, winner);
    }
}
