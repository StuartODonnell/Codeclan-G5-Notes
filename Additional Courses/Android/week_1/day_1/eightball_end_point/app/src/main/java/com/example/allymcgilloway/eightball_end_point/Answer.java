package com.example.allymcgilloway.eightball_end_point;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by allymcgilloway on 12/12/2017.
 */

public class Answer {

    ArrayList<String> answers;

    public Answer(){
        setUpAnswers();
    }

    public void setUpAnswers(){
        this.answers = new ArrayList();
        answers.add("Yes!");
        answers.add("No!");
        answers.add("That would be an ecumenical matter!");
    }

    public ArrayList<String> getAnswers(){
        return new ArrayList<>(this.answers);
    }

    public String getRandomAnswer(){
        Collections.shuffle(this.answers);
        return this.answers.get(0);
    }

    public void addAnswer(String answer){
        this.answers.add(answer);
    }
}
