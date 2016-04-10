package com.example.macbookpro.myapp.filesapp;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.macbookpro.myapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
    }

    public void cancel(View source) {
        finish();
    }

    public void save(View source) {
        TextView noteSummaryTextView = (TextView) findViewById(R.id.note_edit_summary);
        String noteSummary = noteSummaryTextView.getText().toString();
        TextView noteBodyTextView = (TextView) findViewById(R.id.note_edit_body);
        String noteBody = noteBodyTextView.getText().toString();
        File dir = getFilesDir();
        File file = new File(dir, noteSummary);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
            printWriter.write(noteBody);
            finish();
        } catch (FileNotFoundException e) {
            showAlert("Could not save this note! Try again?", e.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    public void showAlert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.show();
    }
}
