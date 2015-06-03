package com.groundupcoding.servicenow;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class ListInstancesActivity extends ActionBarActivity {
    private final String LOG_KEY = "ServiceNow";
    private ListView instanceList = null;
    private SimpleCursorAdapter sca = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_instances);

        instanceList = (ListView)findViewById(R.id.instances);

        getInstances();

    }

    @Override
    public void onResume(){
        super.onResume();
        getInstances();
        Log.i(LOG_KEY, "Refresh List of Instances");
    }

    private void getInstances()
    {
        // Check for instance(s)
        DataBaseHelper db = new DataBaseHelper(this);
        Cursor cursor = db.getInstances();

        if(cursor.getCount()>0)
        {
            // make an adapter from the cursor
            String[] from = new String[] {"name","address"};
            int[] to = new int[] {android.R.id.text1, android.R.id.text2};

            sca = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to);
            // set layout for activated adapter
            //sca.setDropDownViewResource(android.R.layout.simple_list_item_2);

            instanceList.setAdapter(sca);
            registerForContextMenu(instanceList);

        }
        else
        {

        }
    }


    // Fires when context menu is requested
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.instances) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.context_menu_instances, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.edit:
                long id = ((AdapterView.AdapterContextMenuInfo)info).id;
                Intent intent = new Intent(getApplicationContext(), EditInstanceActivity.class);
                intent.putExtra("id",String.valueOf(id));
                startActivity(intent);
                return true;
            case R.id.delete:
                 long id2 = ((AdapterView.AdapterContextMenuInfo)info).id;
                DataBaseHelper db = new DataBaseHelper(getApplicationContext());
                db.removeInstance(id2);
                return true;
            default:
                return super.onContextItemSelected(item);
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
