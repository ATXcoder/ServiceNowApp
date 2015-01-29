package com.groundupcoding.servicenow;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.groundupcoding.servicenow.adapters.IncidentAdapter;
import com.groundupcoding.servicenow.models.Incident;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 1/27/2015.
 */
public class IncidentListActivity extends ActionBarActivity implements AsyncResponse {
    ListView incidentList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set the layout
        setContentView(R.layout.activity_incident);

        //Find the listview
        incidentList = (ListView)findViewById(R.id.incidentList);

        DataBaseHelper db = new DataBaseHelper(this);
        String instanceURL = db.getCurrentInstance();

        // Get the incidents
        NetworkHelper nh = new NetworkHelper(this);
        nh.delegate = this;
        nh.GET(instanceURL + "/incident.do?JSONv2&sysparm_action=getRecords&displayvalue=all&sysparm_record_count=50");


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
