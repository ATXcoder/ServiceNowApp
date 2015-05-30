package com.groundupcoding.servicenow;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Thomas on 3/30/2015.
 * IntentService that will check ServiceNow for
 * new tickets assigned to the user
 * <p/>
 * We run this as an Intent Service so that it will not
 * hang the APP UI in the event the intent is called
 * while the app is opened by the user
 */
public class TicketService extends IntentService {

    private String LOG_TAG = "ServiceNowApp";
    public static final String INTENT_URL = "com.groundupcoding.servicenow";
    public static final String INTENT_STATUS_CODE = "INTENT_STATUS_CODE";
    public static final String INTENT_HEADERS = "INTENT_HEADERS";
    public static final String INTENT_DATA = "INTENT_DATA";
    public static final String INTENT_THROWABLE = "INTENT_THROWABLE";

    private String testURL = "http://demo001.service-now.com/incident.do?JSONv2&sysparm_action=getRecords&displayvalue=true&sysparm_record_count=1";
    private String testUser = "admin";
    private String testPWD = "admin";

    private AsyncHttpClient aClient = new SyncHttpClient();

    //SecurePreferences preferences = new SecurePreferences(getApplicationContext(), "Credentials", "TIMSK@2015!", true);

    public TicketService() {
        // Used to name the worker thread
        super("TicketService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Log.d(LOG_TAG,"Starting ticket call...");
        // If the intent is not empty and has a extra called com.groundupcoding.servicenow
        if (intent != null)
        {
            Log.d(LOG_TAG, "Here we go!");
            ServiceNowRestClient.get(testURL, null, new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                    // Pull out the first event on the public timeline

                    try {
                        JSONObject firstEvent = timeline.getJSONObject(0);
                        String desc = firstEvent.getString("description");
                        Log.i(LOG_TAG, "Description: " + desc);
                        Toast.makeText(getApplicationContext(),desc,Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        Log.e(LOG_TAG, "TRY ERR: " + e.toString());
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.e(LOG_TAG,"ERROR: " + statusCode);
                }
            },testUser, testPWD);
        } else
        {
            if(intent == null)
            {
                Log.d(LOG_TAG, "Intent is NULL");
            } else
            {
                Bundle extras = intent.getExtras();
                Log.d(LOG_TAG,"Extras: " + extras.toString());
            }
        }
    }
}

