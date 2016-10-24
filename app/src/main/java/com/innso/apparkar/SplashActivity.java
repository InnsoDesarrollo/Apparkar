package com.innso.apparkar;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.innso.apparkar.util.GeneralAnimation;

public class SplashActivity extends AppCompatActivity {

    private Runnable timeSplash = this::finish;

    private AnimatorSet startAnimation;

    private ImageView imageCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        init();

        new Handler().postDelayed(timeSplash, 10000);
    }

    private void init() {
        imageCar = (ImageView) findViewById(R.id.imageView_ic_car);

        imageCar.post(this::initAnimation);
    }

    private void initAnimation() {

        startAnimation = new AnimatorSet();

        ObjectAnimator moveCart = (ObjectAnimator) GeneralAnimation.getAppearFromRight(imageCar, 1300, 0, new AccelerateDecelerateInterpolator(), null);

        startAnimation.play(moveCart);

        startAnimation.start();
    }

    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

}
