package com.innso.apparkar.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.innso.apparkar.R;
import com.innso.apparkar.util.GeneralAnimation;

public class SplashActivity extends AppCompatActivity {

    private boolean animationEnd;

    private boolean loadDataEnd;

    private Runnable timeSplash = this::loadData;

    private ImageView imageCar;

    private ImageView imageParkingOption;

    private ImageView imageGasOption;

    private ImageView imageOptionOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        //TODO: Force update
        new Handler().postDelayed(timeSplash, 5000);
    }

    private void init() {
        imageCar = (ImageView) findViewById(R.id.imageView_ic_car);

        imageParkingOption = (ImageView) findViewById(R.id.imageView_parking);

        imageGasOption = (ImageView) findViewById(R.id.imageView_parking_free);

        imageOptionOther = (ImageView) findViewById(R.id.imageView_wash_car);

        imageCar.post(this::initAnimation);
    }

    private void initAnimation() {

        AnimatorSet startAnimation = new AnimatorSet();

        ObjectAnimator moveCart = (ObjectAnimator) GeneralAnimation.getAppearFromRight(imageCar, 1300, 0, new AccelerateDecelerateInterpolator(), null);

        ObjectAnimator parkingOption = (ObjectAnimator) GeneralAnimation.getAnimationFadeIn(imageParkingOption, 600);

        ObjectAnimator gasOption = (ObjectAnimator) GeneralAnimation.getAnimationFadeIn(imageGasOption, 600);

        ObjectAnimator otherOption = (ObjectAnimator) GeneralAnimation.getAnimationFadeIn(imageOptionOther, 600);

        startAnimation.playSequentially(moveCart, parkingOption, gasOption, otherOption);

        startAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationEnd = true;
                notifyFinish();
            }
        });

        startAnimation.start();
    }

    private void loadData() {
        loadDataEnd = true;
        notifyFinish();
    }

    public void notifyFinish() {
        if (animationEnd && loadDataEnd) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
