package com.plightpad.overridenec;

/////
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.ramotion.expandingcollection.AlphaScalePageTransformer;
import com.ramotion.expandingcollection.BackgroundBitmapCache;

import java.util.List;

public class ECPagerViewOverriden extends FrameLayout implements ViewPager.OnPageChangeListener {
    private ECPagerOverriden pager;
    private ECBackgroundSwitcherViewOverriden attachedImageSwitcher;
    private com.plightpad.overridenec.ECPagerViewOverriden.OnCardSelectedListener onCardSelectedListener;
    private boolean needsRedraw;
    private int nextTopMargin = 0;
    private Point center = new Point();
    private Point initialTouch = new Point();
    private Integer cardWidth;
    private Integer cardHeight;
    private Integer cardHeaderExpandedHeight;

    public ECPagerViewOverriden(Context context) {
        super(context);
        this.init(context);
    }

    public ECPagerViewOverriden(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initializeFromAttributes(context, attrs);
        this.init(context);
    }

    public ECPagerViewOverriden(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initializeFromAttributes(context, attrs);
        this.init(context);
    }

    protected void initializeFromAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, ramotion.com.expandingcollection.R.styleable.ExpandingCollection, 0, 0);

        try {
            this.cardWidth = Integer.valueOf(array.getDimensionPixelSize(ramotion.com.expandingcollection.R.styleable.ExpandingCollection_cardWidth, 500));
            this.cardHeight = Integer.valueOf(array.getDimensionPixelSize(ramotion.com.expandingcollection.R.styleable.ExpandingCollection_cardHeight, 550));
            this.cardHeaderExpandedHeight = Integer.valueOf(array.getDimensionPixelSize(ramotion.com.expandingcollection.R.styleable.ExpandingCollection_cardHeaderHeightExpanded, 450));
        } finally {
            array.recycle();
        }

    }

    private void init(Context context) {
        this.setClipChildren(false);
        this.setClipToPadding(false);
        if(Build.VERSION.SDK_INT < 21) {
            this.setLayerType(1, (Paint)null);
        }

        this.pager = new ECPagerOverriden(context);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.addView(this.pager, 0, layoutParams);
        this.pager.setPageTransformer(false, new AlphaScalePageTransformer());
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

        try {
            this.pager = (ECPagerOverriden) this.getChildAt(0);
            this.pager.addOnPageChangeListener(this);
            this.pager.updateLayoutDimensions(this.cardWidth.intValue(), this.cardHeight.intValue());
        } catch (Exception var2) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.center.x = w / 2;
        this.center.y = h / 2;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()) {
            case 0:
                this.initialTouch.x = (int)ev.getX();
                this.initialTouch.y = (int)ev.getY();
            default:
                ev.offsetLocation((float)(this.center.x - this.initialTouch.x), (float)(this.center.y - this.initialTouch.y));
                return this.pager.dispatchTouchEvent(ev);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(this.needsRedraw) {
            this.invalidate();
        }

    }

    public void onPageSelected(int position) {
        int oldPosition = this.pager.getCurrentPosition();
        this.pager.setCurrentPosition(position);
        ECBackgroundSwitcherViewOverriden.AnimationDirectionOverriden direction = null;
        int nextPositionPrediction = position;
        if(oldPosition < position) {
            direction = ECBackgroundSwitcherViewOverriden.AnimationDirectionOverriden.LEFT;
            nextPositionPrediction = position + 1;
        } else if(oldPosition > position) {
            direction = ECBackgroundSwitcherViewOverriden.AnimationDirectionOverriden.RIGHT;
            nextPositionPrediction = position - 1;
        }

        if(this.attachedImageSwitcher != null) {
            this.attachedImageSwitcher.setReverseDrawOrder(this.attachedImageSwitcher.getDisplayedChild() == 1);
            BackgroundBitmapCache instance = BackgroundBitmapCache.getInstance();
            if(instance.getBitmapFromBgMemCache(Integer.valueOf(position)) != null) {
                this.attachedImageSwitcher.updateCurrentBackground(this.pager, direction);
            } else {
                this.attachedImageSwitcher.updateCurrentBackgroundAsync(this.pager, direction);
            }

            this.attachedImageSwitcher.cacheBackgroundAtPosition(this.pager, nextPositionPrediction);
        }

        if(this.onCardSelectedListener != null) {
            this.onCardSelectedListener.cardSelected(position, oldPosition, this.pager.getAdapter().getCount());
        }
    }

    public void onPageScrollStateChanged(int state) {
        this.needsRedraw = state != 0;
    }

    protected void toggleTopMargin(int duration, int delay) {
        final android.widget.RelativeLayout.LayoutParams containerLayoutParams = (android.widget.RelativeLayout.LayoutParams)this.getLayoutParams();
        int currentTopMargin = containerLayoutParams.topMargin;
        ValueAnimator marginAnimation = new ValueAnimator();
        marginAnimation.setInterpolator(new DecelerateInterpolator());
        marginAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                containerLayoutParams.topMargin = ((Integer)animation.getAnimatedValue()).intValue();
                ECPagerViewOverriden.this.setLayoutParams(containerLayoutParams);
            }
        });
        marginAnimation.setIntValues(new int[]{containerLayoutParams.topMargin, this.nextTopMargin});
        marginAnimation.setDuration((long)duration);
        marginAnimation.setStartDelay((long)delay);
        marginAnimation.start();
        this.nextTopMargin = currentTopMargin;
    }

    public void setBackgroundSwitcherView(ECBackgroundSwitcherViewOverriden imageSwitcher) {
        this.attachedImageSwitcher = imageSwitcher;
        if(imageSwitcher != null) {
            ECPagerViewAdapterOverriden adapter = (ECPagerViewAdapterOverriden)this.pager.getAdapter();
            if(adapter != null && adapter.getDataset() != null && adapter.getDataset().size() > 1) {
                this.attachedImageSwitcher.updateCurrentBackground(this.pager, (ECBackgroundSwitcherViewOverriden.AnimationDirectionOverriden)null);
            }

        }
    }

    public void setPagerViewAdapter(ECPagerViewAdapterOverriden adapter) {
        this.pager.setAdapter(adapter);
        if(adapter != null) {
            List dataset = adapter.getDataset();
            if(dataset != null && dataset.size() > 1 && this.attachedImageSwitcher != null) {
                this.attachedImageSwitcher.updateCurrentBackground(this.pager, (ECBackgroundSwitcherViewOverriden.AnimationDirectionOverriden)null);
            }

            if(this.pager.getAdapter() != null && this.onCardSelectedListener != null) {
                this.onCardSelectedListener.cardSelected(this.pager.getCurrentPosition(), this.pager.getCurrentPosition(), this.pager.getAdapter().getCount());
            }

        }
    }

    public void setAttributes(int cardWidth, int cardHeight, int cardHeaderExpandedHeight) {
        this.cardWidth = Integer.valueOf(cardWidth);
        this.cardHeight = Integer.valueOf(cardHeight);
        this.cardHeaderExpandedHeight = Integer.valueOf(cardHeaderExpandedHeight);
        this.pager.updateLayoutDimensions(cardWidth, cardHeight);
    }

    public void setOnCardSelectedListener(com.plightpad.overridenec.ECPagerViewOverriden.OnCardSelectedListener listener) {
        this.onCardSelectedListener = listener;
        if(listener != null) {
            if(this.pager.getAdapter() != null) {
                this.onCardSelectedListener.cardSelected(this.pager.getCurrentPosition(), this.pager.getCurrentPosition(), this.pager.getAdapter().getCount());
            }

        }
    }

    public int getCardWidth() {
        return this.cardWidth.intValue();
    }

    public int getCardHeight() {
        return this.cardHeight.intValue();
    }

    public int getCardHeaderExpandedHeight() {
        return this.cardHeaderExpandedHeight.intValue();
    }

    public boolean expand() {
        return this.pager.expand();
    }

    public boolean collapse() {
        return this.pager.collapse();
    }

    public boolean toggle() {
        return this.pager.toggle();
    }

    public interface OnCardSelectedListener {
        void cardSelected(int var1, int var2, int var3);
    }
}
