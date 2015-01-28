package com.groundupcoding.servicenow.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.groundupcoding.servicenow.R;
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
            //viewholder.priorityLabel = (RelativeLayout)convertView.findViewById(R.id.priorityLabel);
            viewholder.ticket = (RelativeLayout)convertView.findViewById(R.id.incidentLayout);

            Log.i("ServiceNow",incident.getPriority());

            // Figure out color
            switch (incident.getPriority())
            {
                case "1 - Critical":
                    viewholder.ticket.setBackgroundColor(convertView.getResources().getColor(R.color.Red));
                    break;
                case "2 - High":
                    viewholder.ticket.setBackgroundColor(convertView.getResources().getColor(R.color.Orange));
                    break;
                case "3 - Medium":
                    viewholder.ticket.setBackgroundColor(convertView.getResources().getColor(R.color.Yellow));
                    break;
                default:
                    viewholder.ticket.setBackgroundColor(convertView.getResources().getColor(R.color.Green));
                    break;
            }
            convertView.setTag(viewholder);
        }
        else
        {
            viewholder = (ViewHolder) convertView.getTag();
        }

        viewholder.incidentNumber.setText(incident.getNumber());
        viewholder.incidentDesc.setText(incident.getShort_description());
        // Figure out color
        switch (incident.getPriority())
        {
            case "1 - Critical":
                viewholder.ticket.setBackgroundColor(convertView.getResources().getColor(R.color.Red));
                break;
            case "2 - High":
                viewholder.ticket.setBackgroundColor(convertView.getResources().getColor(R.color.Orange));
                break;
            case "3 - Medium":
                viewholder.ticket.setBackgroundColor(convertView.getResources().getColor(R.color.Yellow));
                break;
            default:
                viewholder.ticket.setBackgroundColor(convertView.getResources().getColor(R.color.Green));
                break;
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
