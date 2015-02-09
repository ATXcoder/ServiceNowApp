package com.groundupcoding.servicenow;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class ListInstancesActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_instances);

        ListView instanceList = (ListView)findViewById(R.id.instances);

        // Check for instance(s)
        DataBaseHelper db = new DataBaseHelper(this);
        Cursor cursor = db.getInstances();

        if(cursor.getCount()>0)
        {
            // make an adapter from the cursor
            String[] from = new String[] {"name","address"};
            int[] to = new int[] {android.R.id.text1, android.R.id.text2};

            SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to);
            // set layout for activated adapter
            //sca.setDropDownViewResource(android.R.layout.simple_list_item_2);

            instanceList.setAdapter(sca);

        }
        else
        {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_instances, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_listInstances_add) {
            Intent intent = new Intent(getApplicationContext(), AddInstanceActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
