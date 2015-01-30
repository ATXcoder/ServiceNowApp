package com.groundupcoding.servicenow;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    TextView incidentOpenBy;
    TextView incidentActivity;

    Intent intent;

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
        incidentOpenBy = (TextView)findViewById(R.id.incidentOpenBy);
        incidentActivity = (TextView)findViewById(R.id.incidentActivity);

        // Set fields
        incidentNumber.setText(incident.getDv_number());
        incidentOpenDate.setText(incident.getDv_opened_at());
        incidentSeverity.setText(incident.getDv_severity());
        incidentUrgency.setText(incident.getDv_urgency());
        incidentPriority.setText(incident.getDv_priority());
        incidentCustomer.setText(incident.getDv_caller_id());
        incidentAssignee.setText(incident.getDv_assigned_to());
        incidentTitle.setText(incident.getDv_short_description());
        incidentSummary.setText(incident.getDv_description());
        incidentOpenBy.setText(incident.getDv_opened_by());
        incidentActivity.setText(incident.getDv_comments_and_work_notes());

        // Define clickable fields

        incidentCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start activity to get customer's details
                intent = new Intent(IncidentTicketActivity.this, ContactDetailsActivity.class);
                intent.putExtra("ID",incident.getCaller_id());
                startActivity(intent);
            }
        });
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
