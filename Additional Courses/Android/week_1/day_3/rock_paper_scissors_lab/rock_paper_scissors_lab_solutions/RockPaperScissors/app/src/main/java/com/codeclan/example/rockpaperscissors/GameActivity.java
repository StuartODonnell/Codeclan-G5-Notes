package com.codeclan.example.rockpaperscissors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.codeclan.example.rockpaperscissors.R.string.android;
import static com.codeclan.example.rockpaperscissors.R.string.androidChose;
import static com.codeclan.example.rockpaperscissors.R.string.draw;
import static com.codeclan.example.rockpaperscissors.R.string.paper;
import static com.codeclan.example.rockpaperscissors.R.string.rock;
import static com.codeclan.example.rockpaperscissors.R.string.scissors;
import static com.codeclan.example.rockpaperscissors.R.string.user;
import static com.codeclan.example.rockpaperscissors.R.string.userChose;

/**
 * Created by sandy on 20/10/2016.
 */
public class GameActivity extends AppCompatActivity {

    Game game;
    TextView androidChoiceText;
    TextView userChoiceText;
    TextView resultText;
    Button buttonRock;
    Button buttonPaper;
    Button buttonScissors;

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
        }
    }

    private void play(Selection user) {
        Selection android = game.getComputerSelection();

        userChoiceText.setText(getString(R.string.userChose)+ user.getValue());
        androidChoiceText.setText(getString(R.string.androidChose) + android.getValue());

        Winner winner = game.getWinner(user, android);

        resultText.setText(winner.getValue());
    }
}
