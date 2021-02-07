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
 * A dictionary API has been used to implement this class with its necessary functionality.
 *
 * @author Samia Islam, 1800412227
 * @version 1.0
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
     *This method retrieves the word from the online dictionary
     *
     * @param params
     * @return string This returns a string that contains the word taken from the online database
     */
    @Override
    protected String doInBackground(String... params) {

        final String app_id = "43119cad";
        final String app_key = "71bfe84ff9459be7953c871c192a4653";
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
     * converts the retrived word into json format
     *
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
            try {
                mngrd.showFailurePopUp();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            mngrd.decrementLife();
            //meaning.setText("null");
            e.printStackTrace();
            mainGrid.isAccepted = false;
        }
        mainGrid.isFinished = true;
    }

}
