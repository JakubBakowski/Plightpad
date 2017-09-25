package com.plightpad.overridenec;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.plightpad.R;
import com.ramotion.expandingcollection.ECCardContentListItemAdapter;

public class ECPagerCardContentListOverriden extends ListView {
    private boolean scrollDisabled;
    private int mPosition;
    private ECCardContentListItemAdapterOverriden contentListItemAdapter;
    private ECPagerCardHeadOverriden headView;

    public ECPagerCardContentListOverriden(Context context) {
        super(context);
        this.init(context);
    }

    public ECPagerCardContentListOverriden(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public ECPagerCardContentListOverriden(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    public void init(Context context) {
        this.headView = new ECPagerCardHeadOverriden(context);
        this.headView.setBackgroundColor(context.getResources().getColor(R.color.semi_transparent_black));
        this.headView.setLayoutParams(new LayoutParams(-1, -2));
        this.addHeaderView(this.headView);
    }

    public ECCardContentListItemAdapterOverriden getContentListItemAdapter() {
        return this.contentListItemAdapter;
    }

    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        if(adapter instanceof ECCardContentListItemAdapterOverriden) {
            this.contentListItemAdapter = (ECCardContentListItemAdapterOverriden) adapter;
        }

    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        int actionMasked = ev.getActionMasked() & 255;
        if(this.scrollDisabled && actionMasked == 2) {
            return true;
        } else if(this.scrollDisabled && actionMasked == 8) {
            return true;
        } else if(actionMasked == 0) {
            this.mPosition = this.pointToPosition((int)ev.getX(), (int)ev.getY());
            return super.dispatchTouchEvent(ev);
        } else {
            int eventPosition = this.pointToPosition((int)ev.getX(), (int)ev.getY());
            if(actionMasked == 1 && eventPosition != this.mPosition) {
                ev.setAction(3);
            }

            return super.dispatchTouchEvent(ev);
        }
    }

    public void disableScroll() {
        this.scrollDisabled = true;
    }

    public void enableScroll() {
        this.scrollDisabled = false;
    }

    public void scrollToTop() {
        this.smoothScrollToPosition(0);
    }

    protected ECPagerCardHeadOverriden getHeadView() {
        return this.headView;
    }

    protected void animateWidth(int targetWidth, int duration, int delay) {
        this.getLayoutParams().width = this.getWidth();
        ValueAnimator widthAnimation = new ValueAnimator();
        widthAnimation.setInterpolator(new DecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                android.view.ViewGroup.LayoutParams pagerLayoutParams = ECPagerCardContentListOverriden.this.getLayoutParams();
                pagerLayoutParams.width = ((Integer)animation.getAnimatedValue()).intValue();
                ECPagerCardContentListOverriden.this.setLayoutParams(pagerLayoutParams);
            }
        });
        widthAnimation.setIntValues(new int[]{this.getWidth(), targetWidth});
        widthAnimation.setStartDelay((long)delay);
        widthAnimation.setDuration((long)duration);
        widthAnimation.start();
    }

    protected final void hideListElements() {
        this.getContentListItemAdapter().enableZeroItemsMode();
    }

    protected final void showListElements() {
        this.getContentListItemAdapter().disableZeroItemsMode();
    }
}

