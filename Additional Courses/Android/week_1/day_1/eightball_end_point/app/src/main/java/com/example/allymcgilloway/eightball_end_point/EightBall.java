package com.example.allymcgilloway.eightball_end_point;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by allymcgilloway on 12/12/2017.
 */

public class EightBall {

    private Answer answers;

    public EightBall(Answer answers){
        this.answers = answers;
    }

    public String randomAnswer(){
         return answers.getRandomAnswer();

    }


}
