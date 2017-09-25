package com.plightpad.overridenec;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerViewAdapter;

public class ECPagerOverriden extends ViewPager {
    private int currentPosition;
    private boolean pagingDisabled;

    public ECPagerOverriden(Context context) {
        super(context);
        this.init();
    }

    public ECPagerOverriden(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init() {
        this.setOffscreenPageLimit(3);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return !this.pagingDisabled && super.onTouchEvent(event);
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        return !this.pagingDisabled && super.onInterceptTouchEvent(event);
    }

    public void updateLayoutDimensions(int cardWidth, int cardHeight) {
//        ViewPager.LayoutParams pagerViewLayoutParams =
          this.getLayoutParams().height = cardHeight;
          this.getLayoutParams().width = cardWidth;
//        pagerViewLayoutParams.height = cardHeight;
//        pagerViewLayoutParams.width = cardWidth;
    }

    public ECCardDataOverriden getDataFromAdapterDataset(int position) {
        return (ECCardDataOverriden) ((ECPagerViewAdapterOverriden) this.getAdapter()).getDataset().get(position);
    }

    public void enablePaging() {
        this.pagingDisabled = false;
    }

    public void disablePaging() {
        this.pagingDisabled = true;
    }

    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
    }

    protected void animateWidth(int targetWidth, int duration, int startDelay, AnimatorListenerAdapter onAnimationEnd) {
        ValueAnimator pagerWidthAnimation = new ValueAnimator();
        pagerWidthAnimation.setInterpolator(new AccelerateInterpolator());
        pagerWidthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                android.view.ViewGroup.LayoutParams pagerLayoutParams = ECPagerOverriden.this.getLayoutParams();
                pagerLayoutParams.width = ((Integer) animation.getAnimatedValue()).intValue();
                ECPagerOverriden.this.setLayoutParams(pagerLayoutParams);
            }
        });
        pagerWidthAnimation.setIntValues(new int[]{this.getWidth(), targetWidth});
        pagerWidthAnimation.setStartDelay((long) startDelay);
        pagerWidthAnimation.setDuration((long) duration);
        if (onAnimationEnd != null) {
            pagerWidthAnimation.addListener(onAnimationEnd);
        }

        pagerWidthAnimation.start();
    }

    protected void animateHeight(int targetHeight, int duration, int startDelay, AnimatorListenerAdapter onAnimationEnd) {
        ValueAnimator pagerHeightAnimation = new ValueAnimator();
        pagerHeightAnimation.setInterpolator(new DecelerateInterpolator());
        pagerHeightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                android.view.ViewGroup.LayoutParams pagerLayoutParams = ECPagerOverriden.this.getLayoutParams();
                pagerLayoutParams.height = ((Integer) animation.getAnimatedValue()).intValue();
                ECPagerOverriden.this.setLayoutParams(pagerLayoutParams);
            }
        });
        pagerHeightAnimation.setIntValues(new int[]{this.getHeight(), targetHeight});
        pagerHeightAnimation.setDuration((long) duration);
        pagerHeightAnimation.setStartDelay((long) startDelay);
        if (onAnimationEnd != null) {
            pagerHeightAnimation.addListener(onAnimationEnd);
        }

        pagerHeightAnimation.start();
    }

    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
    }

    public boolean expand() {
        ECPagerViewAdapterOverriden adapter = (ECPagerViewAdapterOverriden) this.getAdapter();
        return adapter.getActiveCard().expand();
    }

    public boolean collapse() {
        ECPagerViewAdapterOverriden adapter = (ECPagerViewAdapterOverriden) this.getAdapter();
        return adapter.getActiveCard().collapse();
    }

    public boolean toggle() {
        ECPagerViewAdapterOverriden adapter = (ECPagerViewAdapterOverriden) this.getAdapter();
        return adapter.getActiveCard().toggle();
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
