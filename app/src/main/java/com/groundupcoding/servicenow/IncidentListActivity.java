package com.groundupcoding.servicenow;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.groundupcoding.servicenow.adapters.IncidentAdapter;
import com.groundupcoding.servicenow.models.Incident;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 1/27/2015.
 */
public class IncidentListActivity extends ActionBarActivity implements AsyncResponse {
    ListView incidentList;
    Button searchButton;
    EditText searchView;
    String instanceURL;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set the layout
        setContentView(R.layout.activity_incident);

        //Find the listview
        incidentList = (ListView)findViewById(R.id.incidentList);
        searchButton = (Button)findViewById(R.id.searchButton);
        searchView = (EditText)findViewById(R.id.searchView);

        DataBaseHelper db = new DataBaseHelper(this);
        instanceURL = db.getCurrentInstance();

        // Get related preferences
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String maxTickets = settings.getString("pref_maxTickets", "50");

        // Get the incidents
        NetworkHelper nh = new NetworkHelper(this);
        nh.delegate = this;
        nh.GET(instanceURL + "/incident.do?JSONv2&sysparm_action=getRecords&displayvalue=all&sysparm_record_count="
                        + maxTickets
                        + "&sysparm_query=active=true"
                        + URLEncoder.encode("^ORDERBYDESCnumber")
        );

        // Search button listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ticketNumber = searchView.getText().toString();
                NetworkHelper nh = new NetworkHelper(IncidentListActivity.this);
                nh.delegate = IncidentListActivity.this;
                nh.GET(instanceURL + "/incident.do?JSONv2&sysparm_action=getRecords&displayvalue=all&sysparm_query=active=true"
                                + URLEncoder.encode("^number=")
                                + ticketNumber
                                + URLEncoder.encode("^ORDERBYDESCnumber")
                );
            }
        });


    }




    @Override
    public void taskComplete(String result) {
        Log.i("ServiceNow", "API Result: " + result);

        final Gson gson = new Gson();

        Incident.Incidents incidents = gson.fromJson(result, Incident.Incidents.class);

        final List<Incident> incidentsList = incidents.getRecords();

        //Incident[] incidentArray = new Incident[incidentsList.size()];
        //incidentArray = incidentsList.toArray(incidentArray);

        IncidentAdapter adapter = new IncidentAdapter(this, incidentsList);

        incidentList.setAdapter(adapter);

        incidentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = incidentList.getItemAtPosition(position);
                Incident incidentObj = (Incident)obj;

                Intent intent = new Intent(IncidentListActivity.this, IncidentTicketActivity.class);
                intent.putExtra("Incident", gson.toJson(incidentObj));
                startActivity(intent);
            }
        });

    }
}
