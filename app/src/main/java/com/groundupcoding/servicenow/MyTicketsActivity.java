package com.groundupcoding.servicenow;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.groundupcoding.servicenow.adapters.IncidentAdapter;
import com.groundupcoding.servicenow.models.Incident;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Thomas on 6/1/2015.
 */
public class MyTicketsActivity extends ActionBarActivity {

    private ListView incidentList;
    Context context;

    private String LOG_TAG = "ServiceNowApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        // Set layout to incident list layout
        setContentView(R.layout.activity_incident);

        //Find the listview
        incidentList = (ListView)findViewById(R.id.incidentList);

        // Get the user credetials from the database
        DataBaseHelper db = new DataBaseHelper(this);
        Cursor user = db.getCredentials();
        user.moveToFirst();

        // Get username from user cursor
        String username = user.getString(0);
        String password = user.getString(1);
        String userID = user.getString(2);

        user.close();
        // Get the last used instance
        String instance = db.getCurrentInstance();
        Log.i(LOG_TAG, "Instance: " + instance);
        Log.i(LOG_TAG, "Username: " + username);
        Log.i(LOG_TAG, "UserID: " + userID);

        // Build API URL
        String s = URLEncoder.encode("^");
        String url = "/incident.do?JSONv2&sysparm_action=getRecords&displayvalue=all&sysparm_query=active=true" + s + "assigned_to=" + userID;



        // Call API
        ServiceNowRestClient.get(url,null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i(LOG_TAG, response.toString());
                Log.i(LOG_TAG, "Stat: " + String.valueOf(statusCode));
                final Gson gson = new Gson();

                Incident.Incidents incidents = gson.fromJson(response.toString(), Incident.Incidents.class);

                final List<Incident> incidentsList = incidents.getRecords();

                //Incident[] incidentArray = new Incident[incidentsList.size()];
                //incidentArray = incidentsList.toArray(incidentArray);

                IncidentAdapter adapter = new IncidentAdapter(context, incidentsList);

                incidentList.setAdapter(adapter);

                incidentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Object obj = incidentList.getItemAtPosition(position);
                        Incident incidentObj = (Incident)obj;

                        Intent intent = new Intent(context, IncidentTicketActivity.class);
                        intent.putExtra("Incident", gson.toJson(incidentObj));
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                Log.e(LOG_TAG,"Status Code: " + String.valueOf(statusCode));
                Log.e(LOG_TAG,throwable.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(LOG_TAG,"Status Code: " + String.valueOf(statusCode));
                Log.e(LOG_TAG,throwable.toString());

            }
        },username, password);



    }
}
