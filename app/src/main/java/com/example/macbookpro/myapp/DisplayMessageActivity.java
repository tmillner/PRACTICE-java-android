package com.example.macbookpro.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;

public class DisplayMessageActivity extends AppCompatActivity {

    String[] messageDuplicated;
    String message = "";
    final String filename = "DisplayMessageActivity.log";

    /*
     * Bundle（键值对的二进制大对象）中有保留状态的 View 对象（比如 EditText 中的文本）
     * 也使得销毁并重新创建Activity不需要更多代码
     */
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
        // finish(); 如果该方法被调用,该Action不会完成创造用户界面
    }

    public void showText() {
        Intent intent = getIntent();
        this.message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        // Only on resume consume this memory
        this.consumeMemory(this.message);
        // Execute only new functionality on new supported SDKs (just an example)
        // NOTE: xml elements that have specify new functionality (from the attr)
        //   don't need conditional checks, the attr is just ignored
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.CUPCAKE) {
            // Best to make TextView a member variable, for further referencing
            TextView textView = new TextView(this);
            textView.setTextSize(38);
            textView.setText(message);

            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.content);
            relativeLayout.addView(textView);
        }
    }

    public void openIntentPool() {
        Intent intent = new Intent(this, IntentPool.class);
        startActivity(intent);
    }

    private void consumeMemory(String msg) {
        // 1048576 when using String[1001002003] app crashes w/
        // OutOfMemoryError: Failed to allocate a 4004008024 byte allocation
        // with 1048576 free bytes and 14MB until OOM
        this.messageDuplicated = new String[200];
        for(int i =0; i< this.messageDuplicated.length; i++) {
            this.messageDuplicated[i] = msg;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        this.messageDuplicated = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 或者其他初始化操作
        this.consumeMemory(this.message);
    }

    // 有关onDestroy.如果在onCreate创建的后台线程或其他如若未正确关闭可能导致内存泄露的长期运行资源，
    //  您应在 onDestroy() 期间终止它们。不然一般不需要
    //  而且onDestroy意味着某个地方泄露了占用的内存
    /*
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    */

    // Prefrenences/首选项 are like map representations of data,
    // not as robust as files, or DBs
    private void writePreferences(){
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.pref_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.rage_string), "DOOHH!!");
        editor.commit();
    }

    private String getPreferences() {
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                getString(R.string.pref_key), Context.MODE_PRIVATE);

        String defaultMessage = getResources().getString(R.string.rage_string_default);
        return sharedPreferences.getString(getString(R.string.rage_string), defaultMessage);
    }

    private void writeLocalFile() {
        FileOutputStream fileOutputStream;

        try {
            // openFileOutput() 获取写入到内部目录中的文件的 FileOutputStream 。
            fileOutputStream = openFileOutput(this.filename, Context.MODE_PRIVATE);
            // 在这里我们并不用查询有没有足够空间, 只要处理IOException例外就行了
            fileOutputStream.write("THIS IS A LOG MESSAGE".getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteLocalFile(AppCompatActivity context) {
        context.getBaseContext().deleteFile(this.filename);
    }
}
