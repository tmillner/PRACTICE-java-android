package com.example.macbookpro.myapp.cameraapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.macbookpro.myapp.R;
import java.io.File;

public class CameraActivity extends AppCompatActivity {

    private static final Integer TAKE_PHOTO_REQUEST_CODE = 12345;
    private static final String APP_DIR = CameraActivity.class.getSimpleName();
    private static final File PHOTO_DIR = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), APP_DIR);
    private static final String PHOTO_NAME = APP_DIR + "-photo_01.jpg";
    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        // IMPORTANT, this activity must use a theme that defines an actionbar else, it'll null pointer!
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cameraapp_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.cameraapp_take_photo_button:
                showCamera();
                return true;
            case R.id.cameraapp_email_photo_button:
                sendEmail();
                return true;
            default:
               return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PHOTO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ImageView photoSlotImageView = (ImageView) findViewById(R.id.cameraapp_photo);
                Log.d("BLAH_TAG", data.getDataString());
                photoSlotImageView.setImageURI(data.getData());
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Photo capture cancelled", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Whoaa, something bad happened!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] results) {
        switch(requestCode){
            case 2: {
                if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                    showCamera();
                }
            }
        }
    }

    private void showCamera() {
        // THIS IS CRITICAL FOR NEWER ANDROID VERSIONS!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {android.Manifest.permission.CAMERA}, 2);
            }
        }
        // Invoke an activity that can store photos
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(PHOTO_DIR, PHOTO_NAME);
        photoURI = Uri.fromFile(photo);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        // Check if there is an app to run the activity with first!
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE);
        }
    }

    private void sendEmail() {
        Intent sendEmailIntent = new Intent(Intent.ACTION_SEND);
        sendEmailIntent.setType("application/image");
        sendEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"trevor.millner@gmail.com"});
        sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "New photo :)");
        sendEmailIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
        // Let user choose which app to send with
        startActivity(Intent.createChooser(sendEmailIntent, "Send an email"));
    }
}
