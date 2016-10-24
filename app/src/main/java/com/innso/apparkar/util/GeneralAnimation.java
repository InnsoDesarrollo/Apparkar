package com.innso.apparkar.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

public class GeneralAnimation {

    public static Animator getAppearFromRight(View view, int duration, int delay, AnimatorListenerAdapter animatorListenerAdapter) {
        return getAppearFromRight(view, duration, delay, new OvershootInterpolator(1f), animatorListenerAdapter);
    }

    public static void appearFromRight(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getAppearFromRight(view, duration, delay, animatorListenerAdapter);

        startAnimation(view, objectAnimator, new AccelerateDecelerateInterpolator());
    }

    public static void appearFromRight(final View view, final int duration, final int delay) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getAppearFromRight(view, duration, delay, null);

        startAnimation(view, objectAnimator, new AccelerateDecelerateInterpolator());
    }

    public static Animator getAppearFromRight(final View view, final int duration, final int delay, TimeInterpolator timeInterpolator, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, (view.getMeasuredWidth() * 2), 0);

        objectAnimator.setDuration(duration);

        objectAnimator.setStartDelay(delay);

        objectAnimator.setInterpolator(timeInterpolator);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animatorListenerAdapter != null)
                    animatorListenerAdapter.onAnimationEnd(animation);
            }
        });

        return objectAnimator;

    }

    public static void startAnimation(final View view, final ObjectAnimator objectAnimator, final TimeInterpolator interpolation) {

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                objectAnimator.setInterpolator(interpolation);
                objectAnimator.start();
                return false;
            }
        });
    }
}
