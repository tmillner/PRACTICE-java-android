package com.example.macbookpro.myapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

// If the activitity is composed of ONLY a list, mine as well just extend ListActivity
public class BasicStringList extends Activity {
    public final static String TAG = "BasicStringList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_string_list);
        String[] levels = getResources().getStringArray(R.array.list_levels);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                /* Could use android list (android.R.layout.*list*) */
                R.layout.listitem_basic_string_list,
                levels);
        ListView listView = (ListView) findViewById(R.id.basic_string_list);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE /* Default single select */);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(BasicStringList.this);
                String msg = "Choose " + item;
                alertDialog.setTitle("Clicked on an item");
                alertDialog.setMessage(msg);
                alertDialog.create().show();
                Log.d(TAG, msg);
            }
        });
    }

}
