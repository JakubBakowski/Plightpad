package com.plightpad.tools;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.appolica.flubber.Flubber;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.plightpad.R;

/**
 * Created by Szczypiorek on 15.08.2017.
 */

public class AnimationUtils {

    public static void animateScaleIn(Context context, View view) {
        YoYo.with(Techniques.RollIn)
                .duration(300)
                .repeat(0)
                .playOn(view);
    }

    public static void animateFadeIn(Context context, View view) {
        YoYo.with(Techniques.FadeIn)
                .duration(300)
                .repeat(0)
                .pivotX(0.5f)
                .pivotY(0.5f)
                .playOn(view);
    }

    public static void animateScaleOut(Context context, View view) {
        YoYo.with(Techniques.ZoomOut)
                .duration(300)
                .repeat(0)
                .pivotX(0.5f)
                .pivotY(0.5f)
                .playOn(view);
    }

    public static void animateFadeOut(Context context, View view) {
        YoYo.with(Techniques.FadeOut)
                .duration(300)
                .repeat(0)
                .pivotX(0.5f)
                .pivotY(0.5f)
                .onEnd(s -> view.setVisibility(View.GONE))
                .playOn(view);
    }

    public static void animateTada(Context context, View view) {
        YoYo.with(Techniques.Tada)
                .duration(300)
                .repeat(0)
                .pivotX(0.5f)
                .pivotY(0.5f)
                .playOn(view);
    }

    public static void animateWooble(Context context, View view) {
        YoYo.with(Techniques.Wobble)
                .duration(300)
                .repeat(0)
                .pivotX(0.5f)
                .pivotY(0.5f)
                .playOn(view);
    }

    public static void animateRefresh(Context context, View view) {
        YoYo.with(Techniques.FadeOut)
                .duration(250)
                .repeat(0)
                .pivotX(0.5f)
                .pivotY(0.5f)
                .onEnd(s -> AnimationUtils.animateLanding(context, view))
                .playOn(view);
    }

    public static void animateLanding(Context context, View view) {
        YoYo.with(Techniques.FadeIn)
                .duration(250)
                .repeat(0)
                .pivotX(0.5f)
                .pivotY(0.5f)
                .playOn(view);
    }


    public static void animateScaleUp(Context context, View view, Rect originalRect, int position) {
        view.getGlobalVisibleRect(originalRect);
        Animation animScale = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.scale_up_anim);
        animScale.setFillAfter(true);
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Animation animTranslation = new TranslateAnimation(
                0,  originalRect.width(),
                0, originalRect.height()*1.25f);
        animTranslation.setFillAfter(true);
        animTranslation.setDuration(100);
        AnimationSet animationSet = new AnimationSet(context, null);
        animationSet.addAnimation(animScale);
        animationSet.addAnimation(animTranslation);
        animationSet.setFillAfter(true);
        animationSet.setDuration(100);
        view.startAnimation(animationSet);
        view.setOnClickListener(s -> animateScaleDown(context, view, originalRect, position));
    }

    public static void animateScaleDown(Context context, View view, Rect originalRect, int position) {
        Animation animScale = android.view.animation.AnimationUtils.loadAnimation(context, R.anim.scale_down_anim);
        animScale.setFillAfter(true);
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Animation animTranslation = new TranslateAnimation(
                originalRect.width(), 0,
                originalRect.height()*1.25f , 0);
        animTranslation.setFillAfter(true);
        animTranslation.setDuration(100);
        AnimationSet animationSet = new AnimationSet(context, null);
        animationSet.addAnimation(animScale);
        animationSet.addAnimation(animTranslation);
        animationSet.setFillAfter(true);
        animationSet.setDuration(100);
        view.startAnimation(animationSet);
        view.setOnClickListener(s -> animateScaleUp(context, view, originalRect, position));
    }


}
