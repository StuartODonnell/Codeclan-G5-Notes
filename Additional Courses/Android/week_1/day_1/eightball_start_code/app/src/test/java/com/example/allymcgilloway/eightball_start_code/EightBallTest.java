package com.example.allymcgilloway.eightball_start_code;

/**
 * Created by allymcgilloway on 12/12/2017.
 */

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

public class EightBallTest {

    EightBall eightBall;
   Answer answers;

    @Before
    public void before(){
        answers = new Answer();
        eightBall = new EightBall(answers);
    }

    @Test
    public void canGetRandomAnswer(){
        String answer = eightBall.randomAnswer();
        assertNotNull(answer);
    }


}

