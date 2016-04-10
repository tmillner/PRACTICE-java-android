package com.example.macbookpro.myapp.filesapp;

import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.macbookpro.myapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExternalFileViewerActivity extends AppCompatActivity {

    class KeyValue {
        public String key;
        public String value;
        public KeyValue(String k, String v) {
            this.key = k;
            this.value = v;
        }

        @Override
        public String toString() {
            return this.key;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_file_viewer);
        final List<KeyValue> keyValueList = Arrays.asList(
                new KeyValue("Alarms", Environment.DIRECTORY_ALARMS),
                new KeyValue("DCIM", Environment.DIRECTORY_DCIM),
                new KeyValue("Downloads", Environment.DIRECTORY_DOWNLOADS),
                new KeyValue("Movies", Environment.DIRECTORY_MOVIES),
                new KeyValue("Music", Environment.DIRECTORY_MUSIC),
                new KeyValue("Notifications", Environment.DIRECTORY_NOTIFICATIONS),
                new KeyValue("Pictures", Environment.DIRECTORY_PICTURES),
                new KeyValue("Podcasts", Environment.DIRECTORY_PODCASTS),
                new KeyValue("Ringtones", Environment.DIRECTORY_RINGTONES)
        );
        ArrayAdapter<KeyValue> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                keyValueList
        );
        ListView fileViewerListView = (ListView) findViewById(R.id.external_file_viewer_list);
        fileViewerListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        fileViewerListView.setAdapter(arrayAdapter);
        fileViewerListView.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showFiles(keyValueList.get(position).value);
            }
        });
    }

    public void showFiles(String dir) {
        // If this app is installed (more recent android versions) then we can read
        // the app needs the permission, though we could still check too
        if (isExternalStorageReadable()) {
            File dirFile = Environment.getExternalStoragePublicDirectory(dir);
            String[] dirFiles;
            if (dirFile == null || dirFile.list() == null) {
                dirFiles = new String[1];
                dirFiles[0] = "Nada";
            }else {
                dirFiles = dirFile.list();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_activated_1,
                    dirFiles
            );
            ListView dirResultsListView = (ListView) findViewById(R.id.external_file_viewer_results);
            dirResultsListView.setAdapter(adapter);
        }
        else {
            showAlert("Permission whoops", "Can't display external memory files!");
        }
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    public void showAlert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.show();
    }
}
