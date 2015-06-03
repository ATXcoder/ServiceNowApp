package com.groundupcoding.servicenow;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.groundupcoding.servicenow.models.Contact;
import com.groundupcoding.servicenow.models.Incident;
import com.groundupcoding.servicenow.models.Record;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.List;

public class TicketServiceReceiver extends WakefulBroadcastReceiver {

    private String LOG_TAG = "ServiceNowApp";
    public static final String INTENT_URL = "com.groundupcoding.servicenow";
    public static final String INTENT_STATUS_CODE = "INTENT_STATUS_CODE";
    public static final String INTENT_HEADERS = "INTENT_HEADERS";
    public static final String INTENT_DATA = "INTENT_DATA";
    public static final String INTENT_THROWABLE = "INTENT_THROWABLE";

    public Context ctx;
    private int count = 0;

    public TicketServiceReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        this.ctx = context;
        // Get intent extras
        Bundle extras = intent.getExtras();
        Log.i(LOG_TAG,"Username: " + extras.get("Username"));
        String userName = extras.getString("Username");
        String password = extras.getString("Password");

        // Look for the intent extra key "Request" as it will tell us what "Service" to perform
        if(extras.containsKey("Request")) //TODO Make this a SWITCH/CASE
        {
            /**
             * Just log it for now, in the future we will take action
             * based upon the "Request" value
             */
            Log.i(LOG_TAG,"FOUND URI: " + extras.getString("Request"));
        }
        ServiceNowRestClient.setBaseURL(extras.getString("BaseURL"));
        ServiceNowRestClient.get(extras.getString("Request"), null, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.i(LOG_TAG, "JSON Object: " + response);

                Gson gson = new Gson();
                Incident.Incidents incidents = gson.fromJson(response.toString(), Incident.Incidents.class);
                List<Incident> incidentsList = incidents.getRecords();
                for(int i=0; i < incidentsList.size(); i++)
                {
                    String id = incidentsList.get(i).getSys_id();
                    Log.i(LOG_TAG,"SYS-ID: " + id);
                    count++;

                }

                Log.i(LOG_TAG, "Status Code: " + String.valueOf(statusCode));
                notifyMe(String.valueOf(count));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

                try {
                    Log.i(LOG_TAG, "API Results: " + timeline.toString());
                    JSONObject firstEvent = timeline.getJSONObject(0);
                    String desc = firstEvent.getString("description");
                    Log.i(LOG_TAG, "Description: " + desc);
                    Toast.makeText(context, desc, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "TRY ERR: " + e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.e(LOG_TAG,"ERROR: " + String.valueOf(statusCode));
            }
        },userName, password);
    }

    public void notifyMe(String msg)
    {
        NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent pendingintent = new Intent(ctx, MyTicketsActivity.class);
        //pendingintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,pendingintent, 0);



        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.drawable.abc_btn_check_to_on_mtrl_015)
                        .setContentTitle("DroidNow")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
