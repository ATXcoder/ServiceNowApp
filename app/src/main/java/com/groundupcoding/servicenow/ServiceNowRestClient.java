package com.groundupcoding.servicenow;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.client.params.ClientPNames;

/**
 * Created by Thomas on 5/7/2015.
 * Simpified REST client for ServiceNow
 */
public class ServiceNowRestClient {

    private static String LOG_TAG = "ServiceNowApp";
    private static String baseURL = "";
    public static void setBaseURL(String baseurl){baseURL = baseurl;};

    public ServiceNowRestClient(String userName, String password, String baseURL) {

    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler,
                           String userName, String password) {
        client.setBasicAuth(userName, password);
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        Log.i(LOG_TAG,"Calling URL: " + baseURL + relativeUrl);
        return baseURL + relativeUrl;
    }

}
