package com.mtetno.updatewidget;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.util.Log;

public class Utility {

    private final String TAG = Utility.class.getSimpleName();
  //  public static boolean UpdateWidget=false;

    public WidgetData getWidgetData() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        Log.d(TAG, dateFormat.format(cal.getTime())); // 2014/08/06 16:00:22
        WidgetData widgetData = new WidgetData("sample", cal.getTime() + "");
        return widgetData;
    }
}
