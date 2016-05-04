package qcox.tacoma.uw.edu.farmgame.highscore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 *
 */
public class HighScore implements Serializable {

    private String mUsername;
    private String mHighscore;


    public static final String USERNMAE = "username", HIGHSCORE = "highscore";


    public HighScore(String username, String highscore){
        setUsername(username);
        setHighscore(highscore);

    }
    public void setUsername(String username){
        if(username == null)
            throw new IllegalArgumentException("username must be supplies");
        mUsername = username;

    }
    public void setHighscore(String highscore){
        if(highscore == null)
            throw new IllegalArgumentException("Highscore must be supplies");
        mHighscore = highscore;

    }
    public String getUsername(){
        return mUsername;
    }
    public String getHighscore(){
        return mHighscore;
    }


    @Override
    public String toString(){
        return "edu.UW.fyang88.webServiceLab.model.course{" +
                ", username = " + mUsername + '\'' +
                ", highscore = " + mHighscore + '}';
    }
    /**
     * Parses the json string, returns an error message if unsuccessful.
     * Returns highscore list if success.
     * @param highScoreJSON
     * @return reason or null if successful.
     */
    public static String parseHighscoreJSONJSON(String highScoreJSON, List<HighScore> highScoreList) {
        String reason = null;
        if (highScoreJSON != null) {
            try {
                JSONArray arr = new JSONArray(highScoreJSON);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    HighScore highScore = new HighScore(obj.getString(HighScore.USERNMAE), obj.getString(HighScore.HIGHSCORE));
                    highScoreList.add(highScore);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data, Reason: " + e.getMessage();
            }

        }
        System.out.print(reason);
        return reason;
    }
}
