package com.groundupcoding.servicenow.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.groundupcoding.servicenow.R;
import com.groundupcoding.servicenow.Settings;
import com.groundupcoding.servicenow.SettingsActivity;
import com.groundupcoding.servicenow.models.Incident;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 1/27/2015.
 */
public class IncidentAdapter extends ArrayAdapter<Incident> {

    private static List<Incident> incidentList;

    private static class ViewHolder {
        TextView incidentNumber;
        TextView incidentDesc;
        RelativeLayout priorityLabel;
        RelativeLayout ticket;

        SharedPreferences settings;
        Boolean colorCodeIncidents;
        String critical_label;
        String critical_color;
        String high_label;
        String high_color;
        String medium_label;
        String medium_color;
        String low_label;
        String low_color;

        String incidentPriority;

    }

    public IncidentAdapter(Context context, List<Incident> incidents) {
        super(context,0,incidents);
        incidentList = incidents;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Incident incident = getItem(position);
        ViewHolder viewholder;

        if(convertView == null)
        {
            viewholder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_incident, parent, false);
            viewholder.incidentNumber = (TextView)convertView.findViewById(R.id.incidentNumber);
            viewholder.incidentDesc = (TextView)convertView.findViewById(R.id.incidentDesc);
            viewholder.ticket = (RelativeLayout)convertView.findViewById(R.id.incidentLayout);

            viewholder.settings = PreferenceManager.getDefaultSharedPreferences(getContext());
            viewholder.colorCodeIncidents = viewholder.settings.getBoolean("pref_incident_colors",true);



            if(viewholder.colorCodeIncidents)
            {
                viewholder.incidentNumber.setTextColor(Color.parseColor("#FFFFFF"));
                viewholder.incidentDesc.setTextColor(Color.parseColor("#FFFFFF"));
                // Lets get the labels and colors
                viewholder.critical_label = viewholder.settings.getString("pref_critical_ticket_label","");
                viewholder.critical_color = viewholder.settings.getString("pref_critical_ticket_color","");
                viewholder.high_label = viewholder.settings.getString("pref_high_ticket_label","");
                viewholder.high_color = viewholder.settings.getString("pref_high_ticket_color","");
                viewholder.medium_label = viewholder.settings.getString("pref_medium_ticket_label","");
                viewholder.medium_color = viewholder.settings.getString("pref_medium_ticket_color","");
                viewholder.low_label = viewholder.settings.getString("pref_low_ticket_label","");
                viewholder.low_color = viewholder.settings.getString("pref_low_ticket_color","");


                // Figure out color

                if(incident.getPriority().equals(viewholder.critical_label))
                {
                    viewholder.ticket.setBackgroundColor(Color.parseColor(viewholder.critical_color));
                }

                if(incident.getPriority().equals(viewholder.high_label))
                {
                    viewholder.ticket.setBackgroundColor(Color.parseColor(viewholder.high_color));
                }

                if(incident.getPriority().equals(viewholder.medium_label))
                {
                    viewholder.ticket.setBackgroundColor(Color.parseColor(viewholder.medium_color));
                }

                if(incident.getPriority().equals(viewholder.low_label))
                {
                    viewholder.ticket.setBackgroundColor(Color.parseColor(viewholder.low_color));
                }
            }
            else
            {
                viewholder.incidentNumber.setTextColor(Color.parseColor("#000000"));
                viewholder.incidentDesc.setTextColor(Color.parseColor("#000000"));
            }

            convertView.setTag(viewholder);
        }
        else
        {
            viewholder = (ViewHolder) convertView.getTag();
        }

        viewholder.incidentNumber.setText(incident.getNumber());
        viewholder.incidentDesc.setText(incident.getShort_description());

        viewholder.settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        viewholder.colorCodeIncidents = viewholder.settings.getBoolean("pref_incident_colors", true);




        if(viewholder.colorCodeIncidents)
        {
            viewholder.incidentNumber.setTextColor(Color.parseColor("#FFFFFF"));
            viewholder.incidentDesc.setTextColor(Color.parseColor("#FFFFFF"));

            // Lets get the labels and colors
            viewholder.critical_label = viewholder.settings.getString("pref_critical_ticket_label","");
            viewholder.critical_color = viewholder.settings.getString("pref_critical_ticket_color","");
            viewholder.high_label = viewholder.settings.getString("pref_high_ticket_label","");
            viewholder.high_color = viewholder.settings.getString("pref_high_ticket_color","");
            viewholder.medium_label = viewholder.settings.getString("pref_medium_ticket_label","");
            viewholder.medium_color = viewholder.settings.getString("pref_medium_ticket_color","");
            viewholder.low_label = viewholder.settings.getString("pref_low_ticket_label","");
            viewholder.low_color = viewholder.settings.getString("pref_low_ticket_color","");

            // Figure out color
            if(incident.getPriority().equals(viewholder.critical_label))
            {
                viewholder.ticket.setBackgroundColor(Color.parseColor(viewholder.critical_color));
            }

            if(incident.getPriority().equals(viewholder.high_label))
            {
                viewholder.ticket.setBackgroundColor(Color.parseColor(viewholder.high_color));
            }

            if(incident.getPriority().equals(viewholder.medium_label))
            {
                viewholder.ticket.setBackgroundColor(Color.parseColor(viewholder.medium_color));
            }

            if(incident.getPriority().equals(viewholder.low_label))
            {
                viewholder.ticket.setBackgroundColor(Color.parseColor(viewholder.low_color));
            }
        }
        else
        {
            viewholder.incidentNumber.setTextColor(Color.parseColor("#000000"));
            viewholder.incidentDesc.setTextColor(Color.parseColor("#000000"));
        }
        return convertView;

        //return super.getView(position, convertView, parent);


    }

    @Override
    public Incident getItem(int position) {
        return incidentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
