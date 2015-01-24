package com.groundupcoding.servicenow;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = (EditText)findViewById(R.id.userName);
        EditText password = (EditText)findViewById(R.id.password);

        String userName = Settings.Read(this, "pref_username");

        // Check for username
        if(userName != "username")
        {
            username.setText(userName);
        }

        // Check for password

        // Check for instance(s)
        DataBaseHelper db = new DataBaseHelper(this);
        Cursor cursor = db.getInstances();

        // make an adapter from the cursor
        String[] from = new String[] {"name"};
        int[] to = new int[] {android.R.id.text1};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, from, to);
        // set layout for activated adapter
        sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner instanceList = (Spinner)findViewById(R.id.spinner_instance);
        instanceList.setAdapter(sca);

        // set spinner listener to display the selected item id

        instanceList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(getApplicationContext(), "Selected ID=" + id, Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView<?> parent) {}
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
