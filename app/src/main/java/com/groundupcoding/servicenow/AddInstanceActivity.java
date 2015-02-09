package com.groundupcoding.servicenow;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.groundupcoding.servicenow.models.Instance;

import org.acra.ACRA;


public class AddInstanceActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instance);

        Button saveButton = (Button)findViewById(R.id.saveButton);

        final EditText name = (EditText)findViewById(R.id.instanceName);
        final EditText address = (EditText)findViewById(R.id.instanceURL);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Instance instance = new Instance();
                    instance.setName(name.getText().toString());
                    instance.setAddress(address.getText().toString());
                    DataBaseHelper db = new DataBaseHelper(getApplicationContext());
                    db.addInstance(instance);
                    Toast.makeText(getApplicationContext(),"Instance added",Toast.LENGTH_LONG).show();
                }catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Error adding instance",Toast.LENGTH_SHORT).show();
                    ACRA.getErrorReporter().handleException(e);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_instance, menu);
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
