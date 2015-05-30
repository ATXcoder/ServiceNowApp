package com.groundupcoding.servicenow;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TicketServiceReceiver extends WakefulBroadcastReceiver {

    private String LOG_TAG = "ServiceNowApp";
    public static final String INTENT_URL = "com.groundupcoding.servicenow";
    public static final String INTENT_STATUS_CODE = "INTENT_STATUS_CODE";
    public static final String INTENT_HEADERS = "INTENT_HEADERS";
    public static final String INTENT_DATA = "INTENT_DATA";
    public static final String INTENT_THROWABLE = "INTENT_THROWABLE";

    private String testURL = "http://demo001.service-now.com/incident.do?JSONv2&sysparm_action=getRecords&displayvalue=true&sysparm_record_count=1";
    private String testUser = "admin";
    private String testPWD = "admin";

    public TicketServiceReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        // New instance of the SecurePrefs
        SecurePreferences preferences = new SecurePreferences(context,"Credentials","TIMSK@2015!",true);
        // Get our base URL
        String instanceURL = preferences.getString("lastInstance");
        // Get our credentials
        String userName = preferences.getString("username");
        String password = preferences.getString("password");

        // Get intent extras
        Bundle extras = intent.getExtras();
        // Look for the intent extra key "Request" as it will tell us what "Service" to perform
        if(extras.containsKey("Request")) //TODO Make this a SWITCH/CASE
        {
            /**
             * Just log it for now, in the future we will take action
             * based upon the "Request" value
             */
            Log.i(LOG_TAG,"FOUND KEY: " + extras.getString("Request"));
        }
        
        ServiceNowRestClient.get(testURL, null, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.i(LOG_TAG, "JSON Object: " + response.toString());
                Log.i(LOG_TAG, "Headers: " + headers.toString());
                Log.i(LOG_TAG, "Status Code: " + String.valueOf(statusCode));
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
}
