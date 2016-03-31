package com.example.macbookpro.myapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.PopupMenu;

public class MenusActivity extends Activity {

    private static final String TAG = "MenusActivity";
    PopupMenu popupMenu;
    PopupMenu.OnMenuItemClickListener onMenuItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);
        Button contextMenuButton = (Button) findViewById(R.id.context_menu_button);
        registerForContextMenu(contextMenuButton); // <- Integeral for creating Context Menu
        onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String msg;
                switch (item.getItemId()) {
                    case (R.id.popup_menu_quit):
                        msg = "You just clicked " + getString(R.string.popup_menu_quit);
                        Log.d(TAG, msg);
                        showAlert(msg);
                        return true;
                    case (R.id.popup_menu_restart):
                        msg = "You just clicked " + getString(R.string.popup_menu_restart);
                        Log.d(TAG, msg);
                        showAlert(msg);
                        return true;
                    default:
                        return false;
                }
            }
        };
        Button popupMenuView = (Button) findViewById(R.id.popup_menu_button);
        popupMenu = new PopupMenu(this, popupMenuView);
        popupMenu.setOnMenuItemClickListener(this.onMenuItemClickListener);
        popupMenu.inflate(R.menu.popup_menu);
        /* The above operations are not enough... Need to bind popupMenu to something
           (ex on click of button show it)
         */
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu,
                                    View v,
                                    ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, v, contextMenuInfo);
        getMenuInflater().inflate(R.menu.context_menu, contextMenu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        String msg = "";
        switch(item.getItemId()) {
            case(R.id.context_menu_delete):
                // getString can be replaced by getResources().getString
                msg = "You just clicked " + getString(R.string.context_menu_delete);
                Log.d(TAG, msg);
                showAlert(msg);
                return true;
            case(R.id.context_menu_shrink):
                msg = "You just clicked " + getString(R.string.context_menu_shrink);
                Log.d(TAG, msg);
                showAlert(msg);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showAlert(String message) {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("ALERT!");
        alert.setMessage(message);
        alert.show();
    }

    /* This needs to be public for views in xml view to invoke it */
    public void showPopup(View v) {
        popupMenu.show();
    }

    public void showBasicListView(View v) {
        Intent intent = new Intent(this, BasicStringList.class);
        startActivity(intent);
    }
}
