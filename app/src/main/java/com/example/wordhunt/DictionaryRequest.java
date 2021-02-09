package com.example.wordhunt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *This class handles the actual matching of words that we make by choosing the letters.
 * An API of Oxford Dictionary has been used to implement this class with its necessary functionality.
 *
 * @author Samia Islam, 180041237
 */
public class DictionaryRequest extends AsyncTask<String, Integer, String> {

    Context context;
    TextView meaning;
    public mainGrid mngrd;
    DictionaryRequest(Context context, TextView def, mainGrid d)
    {
        this.context = context;
        this.meaning = def;
        this.mngrd = d;
    }

    /**
     *this function does the internal activities to connect with the API using the API key
     *
     * @param params
     * @return string
     */
    @Override
    protected String doInBackground(String... params) {

        final String app_id = "20da6bb7";
        final String app_key = "fe45e6db107546e5afee10c5f75962bc";
        try {
            URL url = new URL(params[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    /**
     * this function finds out the definition of the requested word from the JSON format
     * and shows the definition at the pop up window
     * if the requested word is an invalid word, then that means player fails
     * and so the catch block runs and shows the failure pop up window and decrements a life
     *
     * @author Samia Islam, 180041237
     * @param result
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        String def;
        try{
            JSONObject js = new JSONObject(result);
            JSONArray results = js.getJSONArray("results");

            JSONObject lEntries = results.getJSONObject(0);
            JSONArray laEntry = lEntries.getJSONArray("lexicalEntries");

            JSONObject entries = laEntry.getJSONObject(0);
            JSONArray e = entries.getJSONArray("entries");

            JSONObject jsonObject = e.getJSONObject(0);
            JSONArray sensesArray = jsonObject.getJSONArray("senses");

            JSONObject de = sensesArray.getJSONObject(0);
            JSONArray d = de.getJSONArray("definitions");

            def = d.getString(0);
            meaning.setText(def);
            //mainGrid.countDownTimer.cancel();
            mngrd.showSuccessPopUp(def);

            mainGrid.isAccepted = true;
        } catch (JSONException e) {

            mngrd.showFailurePopUp();
            mngrd.decrementLife();
            //meaning.setText("null");
            e.printStackTrace();
            mainGrid.isAccepted = false;
        }
        mainGrid.isFinished = true;
    }

}
