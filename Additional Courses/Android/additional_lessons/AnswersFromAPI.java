//AnswersFromAPI.java

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnswersFromAPI extends Answers  {

    // reference to the URL we’ll be calling. It’s much better to 
    // hold this URL as a static String, so we don’t have to go 
    // searching through your code for it.
    private final static String API_URL="https://craggy-island-8-ball-api.herokuapp.com/";

    public AnswersFromAPI() {
        super();
    }

    public AnswersFromAPI(ArrayList<String> answers) {
        super(answers);
    }

    // this class extends the Answers`class, 
    // so it also has to implement the setUpAnswers method
    
    public void setupAnswers() {
        // create an instance of the AsyncHttpClient class to create 
        // a client  to perform networking with
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(API_URL, new JsonHttpResponseHandler() {
            // if successfull then we should get a JSONObject back
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Log.d("Eightball:", jsonObject.toString());

                // convert JSONObject to JSONArray
                JSONArray jsonAnswers = jsonObject.optJSONArray("answers");
                // go through the JSONArray, getting each element 
                // as a JSONObject
                if (jsonAnswers != null) {
                    for (int i = 0; i < jsonAnswers.length(); i++) {
                        // get the JSON string representing the answer
                        JSONObject jsonAnswer = jsonAnswers.optJSONObject(i);
                        if (jsonAnswer.has("text")) {
                            // get the string from the answer and add it 
                            // to ArrayList of strings of all the answers
                            Log.d("Eightball:", jsonAnswer.toString());
                            String answer = jsonAnswer.optString("text");
                            Log.d("Eightball:", answer);
                            mAnswers.add(answer);
                        }
                    }
                }
            }

            // on failure, log an error message
            @Override
            public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                Log.e("Eightball:", "failure: " + statusCode + " " + throwable.getMessage());
            }
        });
    }
}