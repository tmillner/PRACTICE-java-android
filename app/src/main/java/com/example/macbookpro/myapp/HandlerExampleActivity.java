package com.example.macbookpro.myapp;

import android.media.Image;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class HandlerExampleActivity extends Activity {

    private Handler handler = new Handler();
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_example);
        handler.post(flashTask);
    }

    // Each time this is fired, the runnable executes on 1 same task
    // doesn't spawn madly the thing that changes is the count var
    // hence the flash frequencies
    // Also, Never trust threads will do the right thing!
    public void flash(View source) {
        count = 0;
        handler.post(flashTask);
    }

    // Blink 3 times
    Runnable flashTask = new Runnable() {
        @Override
        public void run() {
            ImageView ghostImage = (ImageView) findViewById(R.id.ghostImage);
            switch(count % 2) {
                case 0:
                    ghostImage.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    ghostImage.setVisibility(View.INVISIBLE);
                    break;
            }
            count++;
            if(count < 7) {
                handler.postDelayed(this, 500);
            }
        }
    };
}
