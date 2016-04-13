package com.example.macbookpro.myapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Activity;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.example.macbookpro.myapp.R;
import java.util.Calendar;

public class BroadcastActivity extends Activity {

    BroadcastReceiver timeBroadcastReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeBroadcastReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setTime();
            }
        };
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        this.registerReceiver(timeBroadcastReciever, intentFilter);
    }

    // Make sure to clear the reciever
    @Override
    protected void onPause() {
        this.unregisterReceiver(timeBroadcastReciever);
        super.onPause();
    }

    private void setTime() {
        Calendar calendar = Calendar.getInstance();
        CharSequence charSequence = DateFormat.format("kk:mm", calendar);
        TextView timeTextView = (TextView) findViewById(R.id.broadcastTime);
        timeTextView.setText(charSequence);
    }
}
