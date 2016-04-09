package com.example.macbookpro.myapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Set;

public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void showAnimation1(View source) {
        View image = findViewById(R.id.animation_photo);
        // values are the extent to which something occurs
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(image, "rotationY", 0f, 720.0F);
        /** IF wanted to add some custom functinoality to the animation can do so using implemented
         * listeners
        objectAnimator.addListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
         **/
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    public void showAnimation2(View source) {
        final View image = findViewById(R.id.animation_photo);
        // ValueAnimator requires a listener implementation on update
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0F, 7200F);
        valueAnimator.setDuration(15000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                image.setRotationX(value);
                if (value < 3600) {
                    image.setTranslationX(value / 20);
                    image.setTranslationY(value / 20);
                } else {
                    image.setTranslationX((7200 - value) / 20);
                    image.setTranslationY((7200 - value) / 20);
                }
            }
        });
        valueAnimator.start();
    }

    public void showAnimation3(View source) {
        View image = findViewById(R.id.animation_photo);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(image, "translationX", 0f, 300.0F);
        objectAnimator1.setDuration(2000);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(image, "translationY", 0f, 300.0F);
        objectAnimator2.setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(objectAnimator1, objectAnimator2);
        // Float values are arbitrary time points
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(image, "rotation", 0f, 250F);
        objectAnimator1.setDuration(4000);
        animatorSet.play(objectAnimator3).after(objectAnimator2);
        animatorSet.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case (R.id.action_settings):
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        StringBuilder stringBuilder = new StringBuilder();
        boolean multipleAccounts = sharedPreferences.getBoolean(SettingsActivity.MULTIPLE_ACCOUNTS, true);
        stringBuilder.append("Multiple Accounts = " + multipleAccounts);
        String environment = sharedPreferences.getString(SettingsActivity.ENVIORNMENT, "");
        stringBuilder.append("Environment = " + environment);
        Set<String> arr = sharedPreferences.getStringSet(SettingsActivity.ENABLED_ZONES, new HashSet<String>());
        stringBuilder.append("Enabled Zones = " + arr.toString());
        TextView textView = (TextView) findViewById(R.id.settings_string);
        textView.setText(stringBuilder.toString());
    }
}
