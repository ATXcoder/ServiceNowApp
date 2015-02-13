package com.groundupcoding.servicenow;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;


@ReportsCrashes(
        formKey="dGVacG0ydVHnaNHjRjVTUTEtb3FPWGc6MQ",
        formUri = "http://atxcoder.ddns.net:3700/acra-bugtest/_design/acra-storage/_update/report",
        reportType = org.acra.sender.HttpSender.Type.JSON,
        httpMethod = org.acra.sender.HttpSender.Method.PUT,
        formUriBasicAuthLogin="acra_report",
        formUriBasicAuthPassword="password",
        mode = ReportingInteractionMode.DIALOG,
        resToastText = R.string.crash_toast_text, // optional, displayed as soon as the crash occurs, before collecting data which can take a few seconds
        resDialogText = R.string.crash_dialog_text,
        resDialogIcon = android.R.drawable.ic_dialog_info, //optional. default is a warning sign
        resDialogTitle = R.string.crash_dialog_title, // optional. default is your application name
        resDialogCommentPrompt = R.string.crash_dialog_comment_prompt, // optional. when defined, adds a user text field input with this text resource as a label
        resDialogOkToast = R.string.crash_dialog_ok_toast // optional. displays a Toast message when the user accepts to send a report.
)
/**
 * Created by Thomas on 1/20/2015.
 */
public class NetworkHelper {
    private String LOG_KEY = "ServiceNowApp";
    private Context context;

    public AsyncResponse delegate = null;

    public NetworkHelper (Context context)
    {
        this.context = context;
    }

    public void GET (String url)
    {
        Log.i(LOG_KEY, "GET: " + url);
        NetworkHelper_GET nh = new NetworkHelper_GET();
        nh.execute(url);
    }

    public void POST (String url)
    {

    }

    private class NetworkHelper_GET extends AsyncTask<String, JSONArray, String> {

        private ProgressDialog dialog = new ProgressDialog(context);

        //JSONArray jsonArray = null;
        String jsonArray = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setTitle("ServiceNow App");
            this.dialog.setMessage("Querying ServiceNow data, please wait...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String...url) {
            try {
                HttpResponse response;
                InputStream inputStream = null;
                String result = null;

                DataBaseHelper db = new DataBaseHelper(context);
                Cursor cred = db.getCredentials();
                cred.moveToFirst();

                String username = cred.getString(0);
                String password = cred.getString(1);

                Log.i(LOG_KEY, "Username: " + username);

                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet(url[0]);

                String creds = username + ":" + password;
                get.addHeader("Authorization", "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP));

                response = client.execute(get);

                int code = response.getStatusLine().getStatusCode();

                if (code != 200) {
                    // Show the user the error
                    //Toast.makeText(ctx, "ERROR", Toast.LENGTH_LONG).show();
                    Log.e(LOG_KEY, "API ERROR: " + code);
                } else {
                    // All good, lets keep going

                    // Get the content of the result
                    inputStream = response.getEntity().getContent();

                    // Read the response from the web service in JSON. JSON is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);

                    //Log.i("KitchenAPI",reader.readLine());

                    StringBuilder sb = new StringBuilder();

                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }

                    // Convert response to a string
                    result = sb.toString();
                    Log.i(LOG_KEY, "API Result: " + result);

                    jsonArray = result;
                }

            } catch (Exception e) {
                // Log exception
                Log.e(LOG_KEY, e.getMessage());
            }
            return jsonArray;
        }

        @Override
        protected void onPostExecute(String jsonArray) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            delegate.taskComplete(jsonArray);
        }
    }
}
