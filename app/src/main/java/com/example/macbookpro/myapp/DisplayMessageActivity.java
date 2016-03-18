package com.example.macbookpro.myapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.showText();
    }

    public void showText() {
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        // Execute only new functionality on new supported SDKs (just an example)
        // NOTE: xml elements that have specify new functionality (from the attr)
        //   don't need conditional checks, the attr is just ignored
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.CUPCAKE) {
            TextView textView = new TextView(this);
            textView.setTextSize(38);
            textView.setText(message);

            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.content);
            relativeLayout.addView(textView);
        }
    }
}
