package com.groundupcoding.servicenow;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.groundupcoding.servicenow.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Dictionary;
import java.util.List;




public class LoginActivity extends ActionBarActivity implements AsyncResponse {
    String url = null;
    EditText username;
    EditText password;
    CheckBox storeCreds;
    Spinner instanceList;
    Button login;
    DataBaseHelper db;

    private PendingIntent pendingIntent;
    private AlarmManager manager;

    SecurePreferences preferences;

    private final String LOG_KEY = "ServiceNow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Get layout fields, buttons, etc.
        username = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
        storeCreds = (CheckBox)findViewById(R.id.storeCreds);
        instanceList = (Spinner)findViewById(R.id.spinner_instance);
        login = (Button)findViewById(R.id.loginButton);


        preferences = new SecurePreferences(getApplicationContext(),"Credentials","TIMSK@2015!",true);

        String stored_username;
        String stored_password;
        try {
            // Retreive the stored username and password (if there is one)
            stored_username = preferences.getString("username");
            stored_password = preferences.getString("password");
            username.setText(stored_username);
            password.setText(stored_password);
        }
        catch(Exception e)
        {
            // If we can't get the credentials, lets log it
            Log.e(LOG_KEY, "Issue getting credentials: " + e.getMessage()); //TODO: Maybe send to ACRA
        }

        // Check for instance(s)
        db = new DataBaseHelper(this);
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

                //New instance of DatabaseHelper
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
                //Get the url of the selected instance
                url = dataBaseHelper.getInstanceURL(id);
                //Set the 'Last Used Instance' preference - needed for IntentService (ENH-20)
                preferences.put("lastInstance",url);
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        storeCreds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Securely store username and password
                preferences.put("username", username.getText().toString());
                preferences.put("password", password.getText().toString());
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new database instance


                // Login
                login();

                // For now, go straight to incidents
                Intent intent = new Intent(getApplicationContext(), IncidentListActivity.class);
                startActivity(intent);

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
        //db = new DataBaseHelper(this);
        String instanceURL = url;

        db = new DataBaseHelper(getApplicationContext());
        //Reset the credentials table (drop and recreate it)
        db.resetCredentials();
        //INSERT the credetials in the 'username' and 'password' box
        db.setCredentials(username.getText().toString(), password.getText().toString(),url,null);

        // Access Network Helper
        NetworkHelper nh = new NetworkHelper(this);
        nh.delegate = this;

        Log.i(LOG_KEY, "Getting user information");
        nh.GET(instanceURL + "/sys_user.do?JSONv2&sysparm_action=getRecords&displayvalue=true&sysparm_query=user_name=" + username.getText().toString());
    }

    public Dictionary<String, String> GetUserID()
    {
        DataBaseHelper db = new DataBaseHelper(this);
        Cursor cursor = db.getCredentials();
        cursor.moveToFirst();
        int usernameCol = cursor.getColumnIndex("username");
        int passwordCol = cursor.getColumnIndex("password");
        String userName = cursor.getString(usernameCol);
        String password = cursor.getString(passwordCol);
        final Dictionary<String, String> creds = null;
        creds.put("username",userName);
        creds.put("password",password);

        // Get our base URL
        String instanceURL = db.getCurrentInstance();
        creds.put("instance",instanceURL);

        // Get our users ID
        String userURL = instanceURL + "/sys_user.do?JSONv2&sysparm_action=getRecords&displayvalue=true&sysparm_record_count=1&sysparm_query=user_name=" + userName;

        // Set the current instance as the baseURL
        ServiceNowRestClient.setBaseURL(db.getCurrentInstance());

        ServiceNowRestClient.get(userURL,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                String jsonobject = response.toString();
                Log.i(LOG_KEY, "JSON Object: " + jsonobject);
                try {
                    JSONObject object = new JSONObject(jsonobject);
                    creds.put("userID",object.getString("sys_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },userName,password);
        return creds;
    }

    @Override
    public void taskComplete(String result) {

        Log.d(LOG_KEY, result);

        Gson gson = new Gson();
        User.Users users = gson.fromJson(result, User.Users.class);

        List<User> userList = users.getUserList();

        User[] userArray = new User[userList.size()];
        userArray = userList.toArray(userArray);

        User user = (User)userArray[0];

        db = new DataBaseHelper(getApplicationContext());
        //Reset the credentials table (drop and recreate it)
        db.resetCredentials();
        //INSERT the credetials in the 'username' and 'password' box
        db.setCredentials(username.getText().toString(), password.getText().toString(),url,user.getSys_id());

        // Set ticket activity
        Intent alarmIntent = new Intent(getApplicationContext(),
                com.groundupcoding.servicenow.TicketServiceReceiver.class);
        String s = URLEncoder.encode("^");
        String apiurl = "/incident.do?JSONv2&sysparm_action=getRecords&displayvalue=all&sysparm_query=active=true" + s + "assigned_to=" + user.getSys_id();

        alarmIntent.putExtra("Request",apiurl);
        alarmIntent.putExtra("Username",username.getText().toString());
        alarmIntent.putExtra("Password", password.getText().toString());
        alarmIntent.putExtra("BaseURL", url);

        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
                alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        // 1 hour (3600000 ms)
        int interval = 3600000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);



    }
}
