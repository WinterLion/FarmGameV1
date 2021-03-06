package qcox.tacoma.uw.edu.farmgame;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import qcox.tacoma.uw.edu.farmgame.data.PlayerValues;

public class LoginActivity extends AppCompatActivity {

    private static final String LOGIN_URL
            = "http://cssgate.insttech.washington.edu/~_450atm17/james.php?cmd=users";
    private Users mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editText_username = (EditText) findViewById(R.id.editText_username);
        final EditText editText_password = (EditText) findViewById(R.id.editText_password);
        final Button button_login = (Button) findViewById(R.id.button_login);
        final Button button_goToRegister = (Button) findViewById(R.id.button_goToRegister);


        if (button_login != null) {
            button_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = editText_username.getText().toString();
                    String password = editText_password.getText().toString();

                    if (TextUtils.isEmpty(username))  {
                        Toast.makeText(v.getContext(), "Enter username", Toast.LENGTH_SHORT).show();
                        editText_username.requestFocus();
                        return;
                    }
                    if (!username.contains("@") || !username.contains(".")) {
                        Toast.makeText(v.getContext(), "Enter a valid email address", Toast.LENGTH_SHORT).show();
                        editText_username.requestFocus();
                        return;
                    }

                    if (TextUtils.isEmpty(password))  {
                        Toast.makeText(v.getContext(), "Enter password", Toast.LENGTH_SHORT).show();
                        editText_password.requestFocus();
                        return;
                    }
                    if (password.length() < 6) {
                        Toast.makeText(v.getContext(), "Enter password of at least 6 characters", Toast.LENGTH_SHORT).show();
                        editText_password.requestFocus();
                        return;
                    }
                    mUsers = new Users(username, password);
                    new LoginTask().execute(new String[]{LOGIN_URL.toString()});

                }
            });


            if (button_goToRegister != null) {
                button_goToRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
            }

        }


    }

    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            Log.i("444 start: ", response);
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
                    response = "Unable to download the list of users, Reason: "
                            + e.getMessage();
                }
                finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            List<Users> usersList = new ArrayList<Users>();
            result = Users.parseUsersJSON(result, usersList);
            // Something wrong with the JSON returned.
            if (result != null) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            // Everything is good, show the list of courses.
            boolean found = false;
            if (!usersList.isEmpty()) {
                for (Users user : usersList){
                    if (user.getUsername().equals(mUsers.getUsername())
                            && user.getPassword().equals(mUsers.getPassword())){
                        Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_LONG).show();
                        found = true;
                        PlayerValues.setUserName(mUsers.getUsername());
                        Intent intent = new Intent(getApplicationContext(), FarmActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
            if (!found){
                Toast.makeText(getApplicationContext(), "Login fail, try again", Toast.LENGTH_LONG).show();
            }
        }
    }
}
