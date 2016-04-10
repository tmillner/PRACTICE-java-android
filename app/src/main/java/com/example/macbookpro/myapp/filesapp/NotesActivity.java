package com.example.macbookpro.myapp.filesapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.macbookpro.myapp.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/** Use the internal storage to CRUD notes **/
public class NotesActivity extends AppCompatActivity {

    private String selectedNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ListView notesListView = (ListView) findViewById(R.id.notes_list);
        notesListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        notesListView.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                readNote(position);
            }
        });
        // Since we override onResume, there's no need to populate the list here
        // This state should only be triggered once
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        ListView notesListView = (ListView) findViewById(R.id.notes_list);
        String[] noteTitles = fileList();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_activated_1 /* WRONG -> R.id.notes_list */,
                noteTitles);
        notesListView.setAdapter(arrayAdapter);

    }

    /** addNote & deleteNote can be smashed into the menu as items instead **/
    public void addNote(View source) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivity(intent);
    }

    public void deleteNote(View source) {
        if(selectedNote != null) {
            deleteFile(selectedNote);
            selectedNote = null;
            refresh();
        }
    }

    public void readNote(int position) {
        // The operation must not be randomizing then...
        String[] noteTitles = fileList();
        // Not sure if the negative case can ever happen...
        if (noteTitles.length > position) {
            selectedNote = noteTitles[position];
            StringBuilder sb = new StringBuilder();
            // Application internal directory
            File dir = getFilesDir();
            File file = new File(dir, selectedNote);
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            try {
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                String line = bufferedReader.readLine();
                while(line != null) {
                    sb.append(line);
                    line = bufferedReader.readLine();
                }
                TextView noteBody = (TextView) findViewById(R.id.notes_body);
                noteBody.setText(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Instead of just trying and passing on the exception
                // let's not get sloppy. Try to prevent it in the first place
                if (bufferedReader != null){
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileReader != null){
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
