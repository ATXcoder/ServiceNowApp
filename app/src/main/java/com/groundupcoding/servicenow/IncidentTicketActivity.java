package com.groundupcoding.servicenow;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.groundupcoding.servicenow.models.Incident;

import org.w3c.dom.Text;


public class IncidentTicketActivity extends ActionBarActivity {
    TextView incidentNumber;
    TextView incidentOpenDate;
    TextView incidentSeverity;
    TextView incidentUrgency;
    TextView incidentPriority;
    TextView incidentCustomer;
    TextView incidentAssignee;
    TextView incidentTitle;
    TextView incidentSummary;

    Incident incident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_ticket);

        // Get the Incident object
        String passedIncident;
        Bundle extras = getIntent().getExtras();
        passedIncident = extras.getString("Incident");
        incident = new Gson().fromJson(passedIncident, Incident.class);

        // Get fields
        incidentNumber = (TextView)findViewById(R.id.incidentNumber);
        incidentOpenDate = (TextView)findViewById(R.id.incidentOpenDate);
        incidentSeverity = (TextView)findViewById(R.id.incidentSeverity);
        incidentUrgency = (TextView)findViewById(R.id.incidentUrgency);
        incidentPriority = (TextView)findViewById(R.id.incidentPriority);
        incidentCustomer = (TextView)findViewById(R.id.incidentCustomer);
        incidentAssignee = (TextView)findViewById(R.id.incidentAssignee);
        incidentTitle = (TextView)findViewById(R.id.incidentTitle);
        incidentSummary = (TextView)findViewById(R.id.incidentSummary);

        // Set fields
        incidentNumber.setText(incident.getNumber());
        incidentOpenDate.setText(incident.getOpened_at());
        incidentSeverity.setText(incident.getSeverity());
        incidentUrgency.setText(incident.getUrgency());
        incidentPriority.setText(incident.getPriority());
        incidentCustomer.setText(incident.getOpened_by());
        incidentAssignee.setText(incident.getAssigned_to());
        incidentTitle.setText(incident.getShort_description());
        incidentSummary.setText(incident.getDescription());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_incident_ticket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id)
        {
            case R.id.ITM_add_comment:
                return true;

            case R.id.ITM_close_ticket:
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
