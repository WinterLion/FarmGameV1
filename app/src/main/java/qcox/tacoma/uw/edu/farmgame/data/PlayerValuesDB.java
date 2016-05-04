package qcox.tacoma.uw.edu.farmgame.data;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import qcox.tacoma.uw.edu.farmgame.MyscoreRecyclerViewAdapter;
import qcox.tacoma.uw.edu.farmgame.highscore.HighScore;

/**
 * Created by Cox Family on 5/4/2016.
 */
public class PlayerValuesDB {

    private static final String GET_PLAYER_MONEY_URL
            = "http://cssgate.insttech.washington.edu/~_450atm17/playervalues.php?cmd=getmoney&user=";
    private static final String SET_PLAYER_MONEY_URL
            = "http://cssgate.insttech.washington.edu/~_450atm17/playervalues.php?cmd=setmoney&user=";
    private Activity mActivity;

    public void GetUserMoney(Activity theActivity) {
        mActivity = theActivity;
        DownloadUserMoneyTask task = new DownloadUserMoneyTask();
        task.execute(new String[]{GET_PLAYER_MONEY_URL + PlayerValues.getUserName()});

    }

    public void UpdateUserMoney(Activity theActivity, int theNewAmount) {
        mActivity = theActivity;
        DownloadUserMoneyTask task = new DownloadUserMoneyTask();
        task.execute(new String[]{SET_PLAYER_MONEY_URL + PlayerValues.getUserName() + "&money=" + theNewAmount});

    }

    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns amount of item if success.
     * @param amountJSON
     * @return reason or null if successful.
     */
    public static String parseAmountJSONJSON(String amountJSON, int theAmount) {
        String reason = null;
        if (amountJSON != null) {
            try {
                JSONArray arr = new JSONArray(amountJSON);


                    JSONObject obj = arr.getJSONObject(0);
                    int amount = Integer.parseInt(obj.getString("amount"));
                    theAmount = amount;

            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        System.out.print(reason);
        return reason;
    }

    private class DownloadUserMoneyTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(mActivity.getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            int moneyAmount = 0;
            result = PlayerValuesDB.parseAmountJSONJSON(result, moneyAmount);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(mActivity.getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // Everything is good, show the list of courses.
            PlayerValues.setMoney(moneyAmount);
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to download the list of highscores, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


    }
}
