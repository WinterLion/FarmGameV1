package qcox.tacoma.uw.edu.farmgame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by james on 5/2/2016.
 */
public class Users {

        private String mUsername;
        private String mPassword;
        public static final String USERNAME = "username", PASSWORD = "password";

        Users(String username, String password){
            this.mUsername = username;
            this.mPassword = password;
        }

        String getUsername () {
            return mUsername;
        }
        String getPassword(){
            return mPassword;
        }

        /**
         * Parses the json string, returns an error message if unsuccessful.
         * Returns course list if success.
         * @param usersJSON
         * @return reason or null if successful.
         */
        public static String parseUsersJSON(String usersJSON, List<Users> usersList) {
            String reason = null;
            if (usersJSON != null) {
                try {
                    JSONArray arr = new JSONArray(usersJSON);

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        Users course = new Users(obj.getString(Users.USERNAME), obj.getString(Users.PASSWORD));
                        usersList.add(course);
                    }
                } catch (JSONException e) {
                    reason =  "Unable to parse data, Reason: " + e.getMessage();
                }

            }
            return reason;
        }

}
