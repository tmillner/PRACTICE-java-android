package com.example.macbookpro.myapp;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

/** No need to create this activity from scratch, Android has
 * templates
 */
public class SettingsActivity extends AppCompatActivity {

    // Link the ids of the preferences from (xml fragments)
    public static String MULTIPLE_ACCOUNTS = "multiple_accounts";
    public static String ENVIORNMENT = "enviornment";
    public static String ENABLED_ZONES = "enabled_zones";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(
                android.R.id.content, new SettingsPreferencesFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
