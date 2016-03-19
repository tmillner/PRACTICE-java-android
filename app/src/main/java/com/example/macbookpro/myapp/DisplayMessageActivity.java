package com.example.macbookpro.myapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    String[] messageDuplicated;
    String message = "";

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

    private void consumeMemory(String msg) {
        this.messageDuplicated = new String[1001002003];
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
}
