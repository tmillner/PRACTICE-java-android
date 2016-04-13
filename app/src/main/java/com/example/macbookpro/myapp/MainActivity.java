package com.example.macbookpro.myapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.macbookpro.myapp.broadcast.BroadcastActivity;
import com.example.macbookpro.myapp.cameraapp.CameraActivity;
import com.example.macbookpro.myapp.filesapp.ExternalFileViewerActivity;
import com.example.macbookpro.myapp.filesapp.NotesActivity;
import com.example.macbookpro.myapp.soundapp.SoundMonitorActivity;

/* Activities are a subclass of Context class */
public class MainActivity extends AppCompatActivity {

    /* Define keys with a namespace as best practice against collisions */
    public final static String EXTRA_MESSAGE = "com.example.macbookpro.myapp.MESSAGE";

    public final static String LAST_VIEW_TIME = "com.example.macbookpro.myapp.LAST_VIEW_TIME";

    /**
     * When the user clicks on the send button from
     * the Button view in content_main layout
     * 当第二个Activity创建好后，当前Activity便停止。
     * 如果用户之后按了返回按钮，第一个Activity(MainActivity)会重新开始。
     **/
    public void sendMessage(View initiatedFromViewContext) {
        Intent intent = new Intent(this /* MainActivity */,
                DisplayMessageActivity.class /* App component to be performed */);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent); /* start new instance of DisplayMessageActivity */
    }

    public void openFragment(View v) {
        Intent intent = new Intent(this, NewsFragment.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* 可以把从onSaveInstanceState方法存的信息参考
           但是,更好把代码放在onRestoreInstanceState方法
        if(savedInstanceState != null) {
            int lastViewTime = savedInstanceState.getInt(LAST_VIEW_TIME);
        }
        */
        setContentView(R.layout.activity_main); /* Necessary for interface changes */
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

        // Can't just attach onClick to a FAB in the layout xml
        FloatingActionButton menuFab = (FloatingActionButton) findViewById(R.id.menus);
        menuFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MenusActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton listFab = (FloatingActionButton) findViewById(R.id.listFragments);
        listFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CreaturesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case (R.id.action_settings):
                return true;
            case (R.id.search):
                showAlert("You selected " + getString(R.string.action_search));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlert(String message) {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("ALERT!");
        alert.setMessage(message);
        alert.show();
    }

    public void showBitmapActivity(View v) {
        Intent intent = new Intent(getBaseContext(), BitmapActivity.class);
        startActivity(intent);
    }

    public void showAnimations(View v) {
        Intent intent = new Intent(getBaseContext(), AnimationActivity.class);
        startActivity(intent);
    }

    public void showNotesApp(View v) {
        Intent intent = new Intent(getBaseContext(), NotesActivity.class);
        startActivity(intent);
    }

    public void showDirectoryFolders(View v) {
        Intent intent = new Intent(getBaseContext(), ExternalFileViewerActivity.class);
        startActivity(intent);
    }

    public void showCameraApp(View v) {
        Intent intent = new Intent(getBaseContext(), CameraActivity.class);
        startActivity(intent);
    }

    public void showSoundMonitorApp(View v) {
        Intent intent = new Intent(getBaseContext(), SoundMonitorActivity.class);
        startActivity(intent);
    }

    public void showSimpleHandler(View v) {
        Intent intent = new Intent(getBaseContext(), HandlerExampleActivity.class);
        startActivity(intent);
    }

    public void showAsyncTask(View v) {
        Intent intent = new Intent(getBaseContext(), AsyncActivity.class);
        startActivity(intent);
    }

    public void showBroadcastTask(View v) {
        Intent intent = new Intent(getBaseContext(), BroadcastActivity.class);
        startActivity(intent);
    }

    /**
     * 因为onStop调用onDestroy或onRestart,其次调用onStart,所以
     * onStart的代码经常使用状态检查(if !null) 但是第一次创建时
     * 也会调用onStart(从onCreate)作为初始化
     @Override
     public void onStart() {super.onStart();}
     **/

    /**
     * 系统在停止时会将您的 Activity 实例保留在系统内存中
     * 所以不用老是实现
     @Override
     public void onStop() {}
     **/

    /**
     * 如果真要把termiating中的状态做更多保存操作,可以通过父母类的
     * onSaveInstanceState
     * 在创造该Activity的时候要是想获取包里面的信息可以
     * 1) 在onCreate方法试着通过Bundle获取键值
     * 2) 在onRestoreInstanceState方法获取键值
     * 最好选择2, 更清楚,也不用更多逻辑判断(if null)
     *
     *
     @Override
     public void onSaveInstanceState(Bundle savedInstanceState) {
     // 以便允许可以保存视图层次的状态
     super.onSaveInstanceState(savedInstanceState);

     savedInstanceState.putInt(LAST_VIEW_TIME, 9001002);
     }
     **/

    /**
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // 以便允许可以设定保存的视图层次的状态
        super.onRestoreInstanceState(savedInstanceState);

        int lastViewTime = savedInstanceState.getInt(LAST_VIEW_TIME);
    }
    **/
}
