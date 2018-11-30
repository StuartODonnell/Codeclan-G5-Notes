package com.codeclan.example.rockpaperscissors;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    Selection computerChoosesRock = Selection.ROCK;
    Selection computerChoosesScissors = Selection.SCISSORS;
    Selection computerChoosesPaper = Selection.PAPER;

    Selection userChoosesRock = Selection.ROCK;
    Selection userChoosesScissors = Selection.SCISSORS;
    Selection userChoosesPaper = Selection.PAPER;
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

}
