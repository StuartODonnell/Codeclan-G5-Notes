package com.example.allymcgilloway.wordcounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity {

    EditText editText;
    Button buttonWordCount;
    Button buttonConcordance;
    WordCounter wordCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.text_to_scan);
        buttonWordCount = findViewById(R.id.button_scan);
        buttonConcordance = findViewById(R.id.button_concordance);

        wordCounter = new WordCounter();

    }

    private void showResultsScreen(String result) {
        Intent intent = new Intent(this, CountResultActivity.class);
        intent.putExtra("wordCountResult", result);
        startActivity(intent);
    }

    public void onWordCountButtonClick(View view) {
        String textToScan = editText.getText().toString();
        Log.d("WordCounter", "Counting words in : '" + textToScan + "'");

        WordCounter wordCounter = new WordCounter();
        int numberOfWords = wordCounter.getCount(textToScan);

        String resultString = "Number of words: " + Integer.toString(numberOfWords);

        showResultsScreen(resultString);
    }

    public void onConcordanceButtonClick(View view) {
        String textToScan = editText.getText().toString();
        Log.d("WordCounter", "Creating concordance for : '" + textToScan + "'");

        WordCounterExtended wordCounter = new WordCounterExtended(textToScan);
        String resultString = wordCounter.toString();

        showResultsScreen(resultString);
    }

}
