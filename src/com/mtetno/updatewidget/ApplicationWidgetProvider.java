package com.mtetno.updatewidget;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

public class ApplicationWidgetProvider extends AppWidgetProvider {

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Toast.makeText(context, "TimeWidgetRemoved id(s):" + appWidgetIds,
                Toast.LENGTH_SHORT).show();
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context, "onDisabled():last widget instance removed",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent
                .getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        super.onDisabled(context);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        AlarmManager am = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        // set context, requestCode, intent, flags
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        // // After after 3 seconds
        // am.setRepeating(AlarmManager.RTC_WAKEUP,
        // System.currentTimeMillis() + 1000 * 60, 1000, pi);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        int time = 60 * 15;// 60 sec * 15 means 15 mins
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                time * 1000, pi);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
            int[] appWidgetIds) {
        ComponentName thisWidget = new ComponentName(context,
                ApplicationWidgetProvider.class);
        for (int widgetId : appWidgetManager.getAppWidgetIds(thisWidget)) {
            // Get the remote views
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);
            // Set the text with the current time.
            remoteViews.setTextViewText(R.id.widgetTitle, new Utility()
                    .getWidgetData().getWidgetDesc());
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context,
            AppWidgetManager appWidgetManager, int appWidgetId,
            Bundle newOptions) {
        // Do some operation here, once you see that the widget has change its
        // size or position.
        Toast.makeText(context, "onAppWidgetOptionsChanged() called",
                Toast.LENGTH_SHORT).show();
    }

}