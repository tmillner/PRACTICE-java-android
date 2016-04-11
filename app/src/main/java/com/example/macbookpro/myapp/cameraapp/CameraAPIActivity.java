package com.example.macbookpro.myapp.cameraapp;

import android.content.pm.PackageManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.View;

import com.example.macbookpro.myapp.R;

import java.io.File;

public class CameraAPIActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String APP_DIR = CameraAPIActivity.class.getSimpleName();
    private static final File PHOTO_DIR = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), APP_DIR);
    private static final String PHOTO_NAME = APP_DIR + "-photo_01.jpg";
    private SoundPool soundPool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_api);
        // Check if the app has permissions to use camera
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {android.Manifest.permission.CAMERA}, 2);
            }
            else {
                validSetup();
            }
        }
        else {
            validSetup();
        }
    }

    private void validSetup() {

    }

    public void takePhoto(View source) {

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
