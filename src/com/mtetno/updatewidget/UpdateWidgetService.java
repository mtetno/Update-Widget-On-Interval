package com.mtetno.updatewidget;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class UpdateWidgetService extends IntentService {

    private final String TAG = UpdateWidgetService.class.getSimpleName();
    public static final String REQUEST_STRING = "myRequest";
    public static final String RESPONSE_STRING = "myResponse";
    public static final String RESPONSE_MESSAGE = "myResponseMessage";

    private String URL = null;
    private static final int REGISTRATION_TIMEOUT = 3 * 1000;
    private static final int WAIT_TIMEOUT = 30 * 1000;

    public UpdateWidgetService() {
        super("MyWebRequestService");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "inside onCreate");
    //    Utility.UpdateWidget = true;
    //    Log.d(TAG, "inside onHandleIntent : " + Utility.UpdateWidget);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "inside onDestroy");
     //   Utility.UpdateWidget = false;
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "inside onHandleIntent");
        String requestString = "http://private-b19f4-sam18.apiary-mock.com/notes";
        String responseMessage = "";
        // Do some really cool here
        // I am making web request here as an example...
        try {
            URL = requestString;
            HttpClient httpclient = new DefaultHttpClient();
            HttpParams params = httpclient.getParams();
            HttpConnectionParams.setConnectionTimeout(params,
                    REGISTRATION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
            ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);
            HttpGet httpGet = new HttpGet(URL);
            HttpResponse response = httpclient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseMessage = out.toString();
            } else {
                // Closes the connection.
                Log.w(TAG, statusLine.getReasonPhrase());
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            Log.w(TAG, e);
            responseMessage = e.getMessage();
        } catch (IOException e) {
            Log.w(TAG, e);
            responseMessage = e.getMessage();
        } catch (Exception e) {
            Log.w(TAG, e);
            responseMessage = e.getMessage();
        }
        Log.d(TAG, "inside onHandleIntent : responseMessage " + responseMessage);

        // update the widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());
        ComponentName thisWidget = new ComponentName(getApplicationContext(),
                ApplicationWidgetProvider.class);
        int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);
        Log.w(TAG, "Direct" + String.valueOf(allWidgetIds2.length));
        for (int widgetId : allWidgetIds2) {
            // Get the remote views
            RemoteViews remoteViews = new RemoteViews(getApplicationContext()
                    .getPackageName(), R.layout.widget_layout);
            // Set the text with the current time.
            remoteViews.setTextViewText(R.id.widgetTitle, responseMessage);
           appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }

    }
}