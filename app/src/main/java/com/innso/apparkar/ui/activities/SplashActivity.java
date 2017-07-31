package com.innso.apparkar.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.innso.apparkar.R;
import com.innso.apparkar.api.controller.ApplicationController;
import com.innso.apparkar.databinding.ActivitySplashBinding;
import com.innso.apparkar.ui.BaseActivity;
import com.innso.apparkar.util.ErrorUtil;
import com.innso.apparkar.util.GeneralAnimation;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    private ActivitySplashBinding binding;

    private boolean animationEnd;

    private boolean versionChecked;

    @Inject
    ApplicationController applicationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        getComponent().inject(this);
        binding.imageViewIcCar.post(this::initAnimation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        applicationController.forceUpdate().subscribe(this::validateVersion, e -> showError(binding.getRoot(), ErrorUtil.getMessageError(e)));
    }

    private void validateVersion(boolean forceUpdate) {
        if (forceUpdate) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.splash_new_version)
                    .setMessage(R.string.splash_get_latest_version)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        startActivity(new Intent(Intent.ACTION_VIEW)
                                .setData(Uri.parse(getString(R.string.url_rappi_tendero))));
                        finish();
                    })
                    .setIcon(R.mipmap.ic_app)
                    .setCancelable(false)
                    .show();
        } else {
            versionChecked = true;
            notifyFinish();
        }
    }

    private void initAnimation() {

        AnimatorSet startAnimation = new AnimatorSet();

        ObjectAnimator moveCart = (ObjectAnimator) GeneralAnimation.getAppearFromRight(binding.imageViewIcCar, 1300, 0, new AccelerateDecelerateInterpolator(), null);

        ObjectAnimator parkingOption = (ObjectAnimator) GeneralAnimation.getAnimationFadeIn(binding.imageViewParking, 600);

        ObjectAnimator gasOption = (ObjectAnimator) GeneralAnimation.getAnimationFadeIn(binding.imageViewParkingFree, 600);

        ObjectAnimator otherOption = (ObjectAnimator) GeneralAnimation.getAnimationFadeIn(binding.imageViewWashCar, 600);

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


    public void notifyFinish() {
        if (animationEnd && versionChecked) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
