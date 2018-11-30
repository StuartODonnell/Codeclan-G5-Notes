package com.codeclan.example.rockpaperscissorslizardspock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sandy on 20/10/2016.
 */
public class GameActivity extends AppCompatActivity {

    Game game;
    TextView androidChoiceText;
    TextView userChoiceText;
    TextView resultText;
    TextView userScoreText;
    TextView androidScoreText;
    Button buttonRock;
    Button buttonPaper;
    Button buttonScissors;
    Button buttonLizard;
    Button buttonSpock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        game = new Game();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidChoiceText = (TextView)findViewById(R.id.android_choice_text);
        userChoiceText = (TextView)findViewById(R.id.user_choice_text);
        resultText = (TextView)findViewById(R.id.result_text);
        buttonRock = (Button)findViewById(R.id.btnRock);
        buttonPaper = (Button)findViewById(R.id.btnPaper);
        buttonScissors = (Button)findViewById(R.id.btnScissors);
        buttonLizard = (Button)findViewById(R.id.btnLizard);
        buttonSpock = (Button)findViewById(R.id.btnSpock);
        userScoreText = (TextView)findViewById(R.id.user_score);
        androidScoreText = (TextView)findViewById(R.id.android_score);

    }

    public void onMakeChoiceButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btnRock:
                Log.d("RockPaperScissors", "Rock selected");
                play(Selection.ROCK);
                break;
            case R.id.btnPaper:
                Log.d("RockPaperScissors", "Paper selected");
                play(Selection.PAPER);
                break;
            case R.id.btnScissors:
                Log.d("RockPaperScissors", "Scissors selected");
                play(Selection.SCISSORS);
                break;
            case R.id.btnLizard:
                Log.d("RockPaperScissors", "Lizard selected");
                play(Selection.LIZARD);
                break;
            case R.id.btnSpock:
                Log.d("RockPaperScissors", "Spock selected");
                play(Selection.SPOCK);
                break;

        }
    }

    private void play(Selection user) {
        Selection android = game.getComputerSelection();

        userChoiceText.setText(getString(R.string.userChose) + user.getValue());
        androidChoiceText.setText(getString(R.string.androidChose) + android.getValue());

        Winner winner = game.getWinner(user, android);


        resultText.setText(winner.getValue());
        int userWins = game.getUserNumWins();
        int androidWins = game.getAndroidNumWins();
        String userScore = new Integer(userWins).toString();
        String androidScore = new Integer(androidWins).toString();

        userScoreText.setText(getString(R.string.scoreUser) + userScore);
        androidScoreText.setText(getString(R.string.scoreAndroid) + androidScore);

    }
}
