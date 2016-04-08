package com.example.macbookpro.myapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;

public class AnimationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
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
                if(value < 3600) {
                    image.setTranslationX(value/20);
                    image.setTranslationY(value / 20);
                }else{
                    image.setTranslationX((7200-value)/20);
                    image.setTranslationY((7200-value)/20);
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

}
