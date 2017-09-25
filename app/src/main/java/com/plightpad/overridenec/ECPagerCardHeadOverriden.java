package com.plightpad.overridenec;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class ECPagerCardHeadOverriden extends FrameLayout {
    private ImageView headBackgroundImageView;

    public ECPagerCardHeadOverriden(Context context) {
        super(context);
        this.init(context);
    }

    public ECPagerCardHeadOverriden(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public ECPagerCardHeadOverriden(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    public void init(Context context) {
        this.headBackgroundImageView = new ImageView(context);
        this.headBackgroundImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LayoutParams params = new LayoutParams(-1, -1);
        this.headBackgroundImageView.setLayoutParams(params);
        this.addView(this.headBackgroundImageView);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

        try {
            this.headBackgroundImageView = (ImageView)this.getChildAt(0);
        } catch (Exception var2) {
            throw new IllegalStateException("Invalid children elements in ECPagerCardHead.");
        }
    }

    protected void setHeadImageDrawable(Drawable headImageDrawable) {
        if(this.headBackgroundImageView != null) {
            this.headBackgroundImageView.setImageDrawable(headImageDrawable);
        }

    }

    protected void setHeadImageDrawable(@DrawableRes int headImageDrawableRes) {
        if(this.headBackgroundImageView != null) {
            this.headBackgroundImageView.setImageResource(headImageDrawableRes);
        }

    }

    protected void setHeadImageBitmap(Bitmap headImageBitmap) {
        if(this.headBackgroundImageView != null) {
            this.headBackgroundImageView.setImageBitmap(headImageBitmap);
        }

    }

    protected void animateHeight(int targetHeight, int duration, int delay) {
        final android.view.ViewGroup.LayoutParams cardHeaderLayoutParams = this.getLayoutParams();
        ValueAnimator cardHeadHeightAnimation = new ValueAnimator();
        cardHeadHeightAnimation.setInterpolator(new DecelerateInterpolator());
        cardHeadHeightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                cardHeaderLayoutParams.height = ((Integer)animation.getAnimatedValue()).intValue();
                ECPagerCardHeadOverriden.this.setLayoutParams(cardHeaderLayoutParams);
            }
        });
        cardHeadHeightAnimation.setIntValues(new int[]{cardHeaderLayoutParams.height, targetHeight});
        cardHeadHeightAnimation.setDuration((long)duration);
        cardHeadHeightAnimation.setStartDelay((long)delay);
        cardHeadHeightAnimation.start();
    }

    protected void setHeight(int height) {
        this.getLayoutParams().height = height;
    }
}
