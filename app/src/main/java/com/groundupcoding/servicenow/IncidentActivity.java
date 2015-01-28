package com.groundupcoding.servicenow;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.groundupcoding.servicenow.adapters.IncidentAdapter;
import com.groundupcoding.servicenow.models.Incident;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 1/27/2015.
 */
public class IncidentActivity extends ActionBarActivity implements AsyncResponse {
    ListView incidentList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set the layout
        setContentView(R.layout.activity_incident);

        //Find the listview
       incidentList = (ListView)findViewById(R.id.incidentList);

        // Get the incidents
        NetworkHelper nh = new NetworkHelper(this);
        nh.delegate = this;
        nh.GET("https://demo002.service-now.com/incident.do?JSONv2&sysparm_action=getRecords&displayvalue=true&sysparm_record_count=50");



    }

    @Override
    public void taskComplete(String result) {
        Log.i("ServiceNow", "API Result: " + result);

        Gson gson = new Gson();

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
                Toast.makeText(getApplicationContext(), incidentObj.getNumber(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}