package com.example.macbookpro.myapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.widget.LinearLayout;

public class Frame_Example extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame__example);
        createLinearLayout(this);

    }

    // Programatically create Layout
    // Useful when there are listeners attached for each of the non-known in advance
    // cells or portions
    private void createLinearLayout(Activity activityContext) {
        LinearLayout rootNode = new LinearLayout(activityContext);
        LinearLayout.LayoutParams matchParentParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        rootNode.setOrientation(LinearLayout.VERTICAL);
        rootNode.setGravity(Gravity.CENTER_VERTICAL);
        addContentView(rootNode, matchParentParams);
    }

}
