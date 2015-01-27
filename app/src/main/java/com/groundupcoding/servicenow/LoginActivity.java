package com.groundupcoding.servicenow;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.gson.Gson;
import com.groundupcoding.servicenow.models.User;
import java.util.List;




public class LoginActivity extends ActionBarActivity implements AsyncResponse {
    String url = null;
    EditText username;
    EditText password;

    private final String LOG_KEY = "ServiceNow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
        Spinner instanceList = (Spinner)findViewById(R.id.spinner_instance);
        final Button login = (Button)findViewById(R.id.loginButton);


        String userName = Settings.Read(this, "pref_username");
        String passWord = Settings.Read(this, "pref_password");

        // Check for username
        if(!userName.equals("username"))
        {
            username.setText(userName);
        }

        // Check for password
        if(!passWord.equals("password"))
        {
            password.setText(passWord);
        }

        // Check for instance(s)
        DataBaseHelper db = new DataBaseHelper(this);
        Cursor cursor = db.getInstances();

        if(cursor.getCount()>0)
        {
            // make an adapter from the cursor
            String[] from = new String[] {"name"};
            int[] to = new int[] {android.R.id.text1};
            SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, from, to);
            // set layout for activated adapter
            sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            instanceList.setAdapter(sca);

        }
        else
        {

        }



        // set spinner listener to display the selected item id

        instanceList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){

                DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
                url = dataBaseHelper.getInstanceURL(id);
                Toast.makeText(getApplicationContext(), "URL: " + url, Toast.LENGTH_LONG).show();

            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper db = new DataBaseHelper(getApplicationContext());
                db.resetCredentials();
                db.setCredentials(username.getText().toString(), password.getText().toString(),url);
                login();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login()
    {
        // Get instance URL
        DataBaseHelper db = new DataBaseHelper(this);
        String instanceURL = db.getCurrentInstance();

        // Access Network Helper
        NetworkHelper nh = new NetworkHelper(this);
        nh.delegate = this;

        nh.GET(instanceURL + "sys_user.do?JSONv2&sysparm_action=getRecords&displayvalue=true&sysparm_record_count=6&sysparm_query=user_name=" + username.getText().toString());
    }

    @Override
    public void taskComplete(String result) {
        Log.i(LOG_KEY, "API Result returned: " + result);

        Gson gson = new Gson();
        User.Users users = gson.fromJson(result, User.Users.class);

        List<User> userList = users.getUserList();

        User[] userArray = new User[userList.size()];
        userArray = userList.toArray(userArray);

        Log.i(LOG_KEY,"Username = " + userArray[0].getUser_name());
        Log.i(LOG_KEY,"Full Name = " + userArray[0].getName());

    }
}
