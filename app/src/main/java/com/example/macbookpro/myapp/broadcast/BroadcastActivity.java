package com.example.macbookpro.myapp.broadcast;

import android.app.Notification;
import android.app.Notification.Action;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import com.example.macbookpro.myapp.R;
import java.util.Calendar;

public class BroadcastActivity extends Activity {

    private static final String INTENT_CANCEL_NOTIFICATION = "cancel_notification";
    private static final int INTENT_CANCEL_NOTIFICATION_ID = 360;

    // More like receiver
    BroadcastReceiver timeBroadcastReceiver;
    BroadcastReceiver notificationReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        notificationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                cancelNotification();      
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(INTENT_CANCEL_NOTIFICATION);
        // Don't unregister this, else, won't be able to cancel using the dismiss button
        // via notification window, only can click (in activity) cancel notification button
        this.registerReceiver(notificationReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setTime();
            }
        };
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        this.registerReceiver(timeBroadcastReceiver, intentFilter);
    }

    // Make sure to clear the receiver
    @Override
    protected void onPause() {
        this.unregisterReceiver(timeBroadcastReceiver);
        super.onPause();
    }

    /**
    @Override
    protected void onStop() {
        this.unregisterReceiver(notificationReceiver);
        super.onStop();
    }
    **/

    private void setTime() {
        Calendar calendar = Calendar.getInstance();
        CharSequence charSequence = DateFormat.format("kk:mm", calendar);
        TextView timeTextView = (TextView) findViewById(R.id.broadcastTime);
        timeTextView.setText(charSequence);
    }

    // Standard way to cancel, coupled with notification setup
    public void cancelNotification(View source) {
        cancelNotification();
    }
    
    private void cancelNotification() {
        NotificationManager notificationManager = (NotificationManager) 
                getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(INTENT_CANCEL_NOTIFICATION_ID);
    }
    
    public void setNotification(View source) {
        Intent cancelIntent = new Intent(INTENT_CANCEL_NOTIFICATION);
        // Create the broadcast the receiver knows about
        PendingIntent cancelIntentPending = PendingIntent.getBroadcast(this, 123, cancelIntent, 0);
        Notification notification;
        // Bind broadcast with the customized notification
        if (Build.VERSION.SDK_INT > 23) {
            Icon icon = Icon.createWithResource(this, android.R.drawable.btn_dialog);
            Action notificationAction = new Action.Builder(icon, "", cancelIntentPending).build();
            notification = new Notification.Builder(this)
                    .setContentTitle("Stop")
                    .setContentText("Cease the madness!")
                    .setSmallIcon(android.R.drawable.star_on)
                    .setAutoCancel(true)
                    .addAction(notificationAction)
                    .build();
        }
        else {
            notification = new Notification.Builder(this)
                    .setContentTitle("Stop")
                    .setContentText("Cease the madness!")
                    .setSmallIcon(android.R.drawable.star_on)
                    .setAutoCancel(true)
                    .addAction(android.R.drawable.btn_dialog, "Dismiss", cancelIntentPending)
                    .build();
        }

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        // Send notification
        notificationManager.notify(INTENT_CANCEL_NOTIFICATION_ID, notification);
    }
}
