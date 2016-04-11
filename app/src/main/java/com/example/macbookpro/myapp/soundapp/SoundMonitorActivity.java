package com.example.macbookpro.myapp.soundapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import com.example.macbookpro.myapp.R;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SoundMonitorActivity extends Activity {

    private SoundMonitor soundMonitor = new SoundMonitor();
    private Handler handler = new Handler();
    private final static int POLL_TIME = 150; // ms
    private static final int RECORD_PERMISSION_CODE = 1235; // Can't be Integer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_monitor);
        checkAudioPermissions();
    }

    // This is better than onResume -- guarantees it will always get called
    // on both resume and start cases.
    @Override
    public void onStart() {
        super.onStart();
        soundMonitor.start();
        handler.postDelayed(pollTask, POLL_TIME);
    }

    @Override
    public void onPause() {
        // Don't just put super on first line! We should make sure to clean up
        // our mess first
        soundMonitor.stop();
        super.onPause();
    }

    @Override
    public void onStop() {
        soundMonitor.stop();
        super.onStop();
    }

    private Runnable pollTask = new Runnable() {
        @Override
        public void run() {
            double amp = soundMonitor.getAmplitude();
            TextView ampTextView = (TextView) findViewById(R.id.soundMonitorText);
            ampTextView.setText("LAST AMPLITUDE: " + amp);
            Button ampButtonView = (Button) findViewById(R.id.soundMonitorButton);
            ampButtonView.setWidth((int) amp);
            // Rerun another thread, the longer audio occurs, the more finer the data points accumulate
            handler.postDelayed(pollTask, POLL_TIME);
        }
    };

    private void checkAudioPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.RECORD_AUDIO}, RECORD_PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] results) {
        switch(requestCode){
            case (RECORD_PERMISSION_CODE): {
                if (results.length > 0 && results[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "App was denied permission :(", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    private void startMonitor() {
    }
}
