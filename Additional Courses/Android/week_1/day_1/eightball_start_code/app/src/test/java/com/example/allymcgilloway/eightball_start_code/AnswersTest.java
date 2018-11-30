package com.example.allymcgilloway.eightball_start_code;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by allymcgilloway on 12/12/2017.
 */

public class AnswersTest {

    Answer answer;

    @Before
    public void before(){
        answer = new Answer();
    }

    @Test
    public void canGetCopyOfAnswers(){
        assertEquals(3, answer.getAnswers().size());
    }

    @Test
    public void canAddAnswer(){
        answer.addAnswer("Drink!");
        assertEquals(4, answer.getAnswers().size());
    }

    @Test
    public void cangetRandomAnswer(){
        assertNotNull(answer.getRandomAnswer());
    }
}
