package com.example.macbookpro.myapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BitmapActivity extends Activity {

    private Integer sampleSize = 1;
    private Integer imageId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        refresh();
    }
    public void scaleUp(View v) {
        if (sampleSize > 1) {
            sampleSize--;
            refresh();
        }
    }

    public void scaleDown(View v) {
        if (sampleSize < 8) {
            sampleSize++;
            refresh();
        }
    }

    public void changeImage(View v) {
        if (imageId == 3) {
            imageId = 1;
        }
        else {
            imageId++;
        }
        refresh();
    }

    private void refresh() {
        // Before loading the image get the properties of them.
        // (it could be too large that we want to apply some reductions...)
        // Standard protocol is not to load entirely but read properties
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.barbados, options);
        Integer imageHeight = options.outHeight;
        Integer imageWidth = options.outWidth;
        String imageType = options.outMimeType;

        StringBuilder imageInfo = new StringBuilder();

        int id = R.drawable.barbados;
        if (imageId == 2) {
            id = R.drawable.panama;
            imageInfo.append("Image 2");
        }
        else if (imageId == 3) {
            id = R.drawable.trinidad;
            imageInfo.append("Image 3");
        }
        else {
            imageInfo.append("Image 1");
        }

        imageInfo.append(". Original Dimension: " + imageWidth + " x " + imageHeight + imageType);
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id, options);

        ImageView bitmapImageView = (ImageView) findViewById(R.id.bitmap_image);
        bitmapImageView.setImageBitmap(bitmap);

        TextView scaleTextView = (TextView) findViewById(R.id.bitmap_sample_size);
        // Instead of passing in a single Integer with toString() , this pattern allows
        // conversion Setting a string first, tells the compiler what to do -- convert
        scaleTextView.setText("" + sampleSize);

        TextView imageInfoTextView = (TextView) findViewById(R.id.bitmap_image_info);
        imageInfoTextView.setText(imageInfo);
    }

}
