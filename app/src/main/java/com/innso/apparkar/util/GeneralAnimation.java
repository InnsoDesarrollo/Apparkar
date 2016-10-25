package com.innso.apparkar.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
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

        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        objectAnimator.start();
            }

    public static void appearFromRight(final View view, final int duration, final int delay) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getAppearFromRight(view, duration, delay, null);

        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        objectAnimator.start();
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

    public static void animateFadeIn(View view, int duration) {
        animateFadeIn(view, duration, null);
    }

    public static void animateFadeIn(final View view, int duration, final AnimatorListenerAdapter animationListener) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getAnimationFadeIn(view, duration, animationListener);
        objectAnimator.start();
    }

    public static Animator getAnimationFadeIn(final View view, int duration) {
        return getAnimationFadeIn(view, duration, null);
    }

    public static Animator getAnimationFadeIn(final View view, int duration, final AnimatorListenerAdapter animationListener) {

        ViewCompat.setAlpha(view, 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1);
        objectAnimator.setDuration(duration);
        objectAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                view.setAlpha(0);
                view.setVisibility(View.VISIBLE);
                if (animationListener != null) {
                    animationListener.onAnimationStart(animation);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationListener != null) {
                    animationListener.onAnimationEnd(animation);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (animationListener != null) {
                    animationListener.onAnimationCancel(animation);
                }

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (animationListener != null) {
                    animationListener.onAnimationRepeat(animation);
                }
            }

        });

        return objectAnimator;
    }

    public static void appearFromBottom(final View view, final int duration, final int delay) {

        appearFromBottom(view, duration, delay, null);
    }


    public static void appearFromBottom(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = (ObjectAnimator) getAppearFromBottom(view, duration, delay, animatorListenerAdapter);

        objectAnimator.setInterpolator(new FastOutSlowInInterpolator());

        objectAnimator.start();
    }

    public static Animator getAppearFromBottom(final View view, final int duration, final int delay, final AnimatorListenerAdapter animatorListenerAdapter) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, (view.getMeasuredHeight() * 2), 0);

        objectAnimator.setDuration(duration);

        objectAnimator.setStartDelay(delay);

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                if (animatorListenerAdapter != null) {
                    animatorListenerAdapter.onAnimationStart(animation);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animatorListenerAdapter != null) {
                    animatorListenerAdapter.onAnimationEnd(animation);
                }
            }
        });

        return objectAnimator;
    }
}
