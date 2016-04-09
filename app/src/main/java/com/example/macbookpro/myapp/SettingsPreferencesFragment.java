package com.example.macbookpro.myapp;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsPreferencesFragment extends PreferenceFragment {

    public SettingsPreferencesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preferences);
    }
}
