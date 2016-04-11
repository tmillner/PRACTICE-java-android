package com.example.macbookpro.myapp.soundapp;

import android.media.MediaRecorder;

import java.io.IOException;

/**
 * Created by macbookpro on 4/11/16.
 */
public class SoundMonitor {

    private MediaRecorder mediaRecorder;
    private boolean started = false;

    public void start() {
        // Instead of sticking the mediaRecorder check inside a large !started block
        // just simplify readability to return on opposite condition (if is started)
        if (started) {
            return;
        }
        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mediaRecorder.setOutputFile("/dev/null"); // <3 <3 <3
            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mediaRecorder.start();
        started = true;
    }

    public void stop() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            started = false;
        }
    }

    public long getAmplitude() {
        if (mediaRecorder == null) {
            return 0;
        }
       return mediaRecorder.getMaxAmplitude() / 100;
    }
}
