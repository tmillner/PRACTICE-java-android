package com.example.macbookpro.myapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class IntentPool extends Activity {

    static final int CONTACT_REQUEST_CODE = 900;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_pool);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


    /** 注意: 发送意向时如果不用chooser的话,第一次调用,会问用户
     * 要不要从此设置该应用为默认应用
     * 不然:
     String askString = getResources().getString(R.string.question);
     Intent chooser = Intent.createChooser(callIntent, askString);
     if (callIntent.resolveActivity(getPackageManager()) != null) {
     this.startActivity(chooser);
     }
     */
    public void makeCallIntent(View initiatedFromView) {
        EditText editText = (EditText) findViewById(R.id.call_intent_number);
        String number = editText.getText().toString();
        Uri uri = Uri.parse("tel:" + number);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
        this.safeStartActivity(callIntent);
    }

    public void makeGeoIntent(View initiatedFromView) {
        EditText editText = (EditText) findViewById(R.id.geo_intent_address);
        String address = editText.getText().toString();
        // Uri.parse("geo:37.422219,-122.08364?z=14"); // z = zoom level
        Uri uri = Uri.parse("geo:0,0?q=" + address);
        // 查看网址也是同样的意向 Intent.ACTION_VIEW
        // 要是我们没有说明Intent, 只是用setType, 尽量要定义Intend的类型
        // 比如 MIME/image 很重要,这样能避免多余的软件包出现
        Intent geoIntent = new Intent(Intent.ACTION_VIEW, uri);
        this.safeStartActivity(geoIntent);
    }

    // 如果您未在意向中包含 Uri，您通常应使用 setType() 指定与意向关联的数据的类型
    public void makeEmailIntent(View initiatedFromView) {
        EditText editText = (EditText) findViewById(R.id.send_intent_email);
        String email = editText.getText().toString();
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // http://developer.android.com/intl/zh-cn/training/basics/intents/sending.html
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Android intent from Intent pool");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Yes, so this succeeded :)");
        this.startActivity(emailIntent);
    }

    public boolean safeStartActivity(Intent intent) {
        List availableActivities = getPackageManager().queryIntentActivities(
            intent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean isPackageAvailable =  availableActivities.size() >= 0;
        // 调用开始活动以后, 对话框将出现 给用户选择哪些应用要处意向
        startActivity(intent);
        return isPackageAvailable;
    }

    public void makeContactActivity(View initiatedFromView) {
        Intent contactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        // 仅显示有手机好吗的联系人
        contactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(contactIntent, this.CONTACT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 该函数处理所有的活动, 因此必须检查要处理哪个活动响应
        // 这是一个很常见的(回调)模型 (跟 onRequestPermissionsResult 同样)
        if (requestCode == this.CONTACT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // 必须了解结果的 Intent 的格式
                // Andriod 平台附带的应用提供它们自己的 API，(能够获取特定结果数据)
                Uri selectedContacts = data.getData();
                String[] viewProjection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // 注意: 进行db搜索操作,别阻挡其它UI操作,一般要异步性处理
                Cursor cursor = getContentResolver()
                        .query(selectedContacts, viewProjection, null, null, null);
                cursor.moveToLast();
                String lastNumber = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                TextView textView = (TextView) findViewById(R.id.intent_pool_activity4_result);
                textView.setText(lastNumber);
            }
        }
    }
}
