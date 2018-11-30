package com.example.allymcgilloway.eightball_end_point;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EightBallActivity extends AppCompatActivity {

    EditText questionEditText;
    TextView answerText;
    Button shakeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eight_ball);
        questionEditText = (EditText)findViewById(R.id.question_text);
        answerText = (TextView)findViewById(R.id.answer_text);
        shakeButton = (Button)findViewById(R.id.shake_button);
    }

    public void onShakeButtonClicked(View button) {
        String question = questionEditText.getText().toString();
        Log.d(getClass().toString(), "onShakeButtonClicked was called");
        Log.d(getClass().toString(), "The question asked was '" + question + "'");
        EightBall eightball = new EightBall(new Answer());
        String answer = eightball.randomAnswer();
        answerText.setText(answer);
    }

}
