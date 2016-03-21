package com.example.macbookpro.myapp;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by macbookpro on 3/20/16.
 */
public class EnviornmentHelper {
    // 外部读写 必须在manifest中添加
    // <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    public static boolean isExternalStorageWritable() {
       if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    // 外部读取 可以在manifest中添加
    // <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    // <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    public static boolean isExternalStorageReadable() {
        String externalState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(externalState) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(externalState)){
            return true;
        }
        return false;
    }

    public File getPhotoExternally(String album) {
        File file = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), album);
        if (!file.mkdirs()) {
            Log.e("ERROR", "External directory not made");
        }
        return file;
    }

    public File getPhotoLocally(Context context, String album) {
        File file = new File(context
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES), album);
        if (!file.mkdirs()) {
            Log.e("ERROR", "Local directory not made");
        }
        return file;
    }
}
