package com.mtetno.updatewidget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
    private final String TAG = AlarmManagerBroadcastReceiver.class
            .getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        // PowerManager pm = (PowerManager) context
        // .getSystemService(Context.POWER_SERVICE);
        // PowerManager.WakeLock wl = pm.newWakeLock(
        // PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        // // Acquire the lock
        // wl.acquire();
      // Log.d(TAG, "inside onReceive : " + Utility.UpdateWidget);
//        if (true == Utility.UpdateWidget) {
//            
//        } else if (false == Utility.UpdateWidget) {
            Log.d(TAG, "onReceive");
            Intent serviceIntent = new Intent(context,
                    UpdateWidgetService.class);
            context.startService(serviceIntent);
     //   }
        // // Release the lock
        // wl.release();
    }
}