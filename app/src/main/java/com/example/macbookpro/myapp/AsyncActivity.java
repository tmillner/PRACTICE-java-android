package com.example.macbookpro.myapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class AsyncActivity extends Activity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
        progressBar = (ProgressBar) findViewById(R.id.blurTaskProgress);
    }

    public void blur(View source) {
        ImageView imageView = (ImageView) findViewById(R.id.blurImage);
        // Although getDrawable returns Drawable, we want Subclass of that for our image
        Bitmap inputImage = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        new BlurTask().execute(inputImage);
    }

    // Task is really performing a darkened dim effect
    private class BlurTask extends AsyncTask<Bitmap, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            Bitmap outputImage = params[0].copy(params[0].getConfig(), true /* mutable */);
            int width = outputImage.getWidth();
            int height = outputImage.getHeight();
            int level = 7;
            for (int h = 0; h<height; h++) {
                for (int w = 0; w<width; w++) {
                    int pixel = outputImage.getPixel(w, h);
                    int a = pixel & 0xff000000;
                    int r = (pixel >> 16) & 0xff;
                    int g = (pixel >> 8) & 0xff;
                    int b = pixel & 0xff;
                    r = (r+level)/2;
                    g = (g+level)/2;
                    b = (b+level)/2;
                    int gray = a | (r << 16) | (g << 8) | b;
                    outputImage.setPixel(w, h, gray);
                }
                // Don't worry about 2d granularity, do chunks by looking at 1 dimension
                int progress = 100*((h+1)/height);
                publishProgress(progress);
            }
            return outputImage;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap outputImage) {
            ImageView imageView = (ImageView) findViewById(R.id.blurImage);
            imageView.setImageBitmap(outputImage);
            progressBar.setProgress(0);
        }
    }
}
