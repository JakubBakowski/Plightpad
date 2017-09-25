package com.plightpad.overridenec;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import com.ramotion.expandingcollection.BackgroundBitmapCache;
import com.ramotion.expandingcollection.BitmapFactoryOptions;
import com.ramotion.expandingcollection.BitmapWorkerTask;
import com.ramotion.expandingcollection.ECPager;

public class ECBackgroundSwitcherViewOverriden extends ImageSwitcher {
    private final int[] REVERSE_ORDER = new int[]{1, 0};
    private final int[] NORMAL_ORDER = new int[]{0, 1};
    private boolean reverseDrawOrder;
    private int bgImageGap;
    private int bgImageWidth;
    private int alphaDuration = 400;
    private int movementDuration = 500;
    private int widthBackgroundImageGapPercent = 12;
    private Animation bgImageInLeftAnimation;
    private Animation bgImageOutLeftAnimation;
    private Animation bgImageInRightAnimation;
    private Animation bgImageOutRightAnimation;
    private ECBackgroundSwitcherViewOverriden.AnimationDirectionOverriden currentAnimationDirection;
    private BitmapWorkerTaskOverriden mCurrentAnimationTask;

    public ECBackgroundSwitcherViewOverriden(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.inflateAndInit(context);
    }

    public ECBackgroundSwitcherViewOverriden(Context context) {
        super(context);
        this.inflateAndInit(context);
    }

    private void inflateAndInit(final Context context) {
        this.setChildrenDrawingOrderEnabled(true);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.bgImageGap = displayMetrics.widthPixels / 100 * this.widthBackgroundImageGapPercent;
        this.bgImageWidth = displayMetrics.widthPixels + this.bgImageGap * 2;
        this.setFactory(new ViewFactory() {
            public View makeView() {
                ImageView myView = new ImageView(context);
                myView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                myView.setLayoutParams(new LayoutParams(ECBackgroundSwitcherViewOverriden.this.bgImageWidth, -1));
                myView.setTranslationX((float)(-ECBackgroundSwitcherViewOverriden.this.bgImageGap));
                return myView;
            }
        });
        this.bgImageInLeftAnimation = this.createBgImageInAnimation(this.bgImageGap, 0, this.movementDuration, this.alphaDuration);
        this.bgImageOutLeftAnimation = this.createBgImageOutAnimation(0, -this.bgImageGap, this.movementDuration);
        this.bgImageInRightAnimation = this.createBgImageInAnimation(-this.bgImageGap, 0, this.movementDuration, this.alphaDuration);
        this.bgImageOutRightAnimation = this.createBgImageOutAnimation(0, this.bgImageGap, this.movementDuration);
    }

    protected int getChildDrawingOrder(int childCount, int i) {
        return this.reverseDrawOrder?this.REVERSE_ORDER[i]:this.NORMAL_ORDER[i];
    }

    public void setReverseDrawOrder(boolean reverseDrawOrder) {
        this.reverseDrawOrder = reverseDrawOrder;
    }

    private synchronized void setImageBitmapWithAnimation(Bitmap newBitmap, ECBackgroundSwitcherViewOverriden.AnimationDirectionOverriden animationDirection) {
        if(this.currentAnimationDirection == animationDirection) {
            this.setImageBitmap(newBitmap);
        } else if(animationDirection == ECBackgroundSwitcherViewOverriden.AnimationDirectionOverriden.LEFT) {
            this.setInAnimation(this.bgImageInLeftAnimation);
            this.setOutAnimation(this.bgImageOutLeftAnimation);
            this.setImageBitmap(newBitmap);
        } else if(animationDirection == ECBackgroundSwitcherViewOverriden.AnimationDirectionOverriden.RIGHT) {
            this.setInAnimation(this.bgImageInRightAnimation);
            this.setOutAnimation(this.bgImageOutRightAnimation);
            this.setImageBitmap(newBitmap);
        }

        this.currentAnimationDirection = animationDirection;
    }

    public void cacheBackgroundAtPosition(ECPagerOverriden pager, int position) {
        if(position >= 0 && position < pager.getAdapter().getCount()) {
            Bitmap mainBgImageDrawableResource = pager.getDataFromAdapterDataset(position).getMainBackgroundResource();
            if(mainBgImageDrawableResource == null) {
                return;
            }

            BitmapWorkerTaskOverriden addBitmapToCacheTask = new BitmapWorkerTaskOverriden(mainBgImageDrawableResource);
            addBitmapToCacheTask.execute(new Integer[]{Integer.valueOf(position)});
        }

    }

    public void updateCurrentBackground(ECPagerOverriden pager, ECBackgroundSwitcherViewOverriden.AnimationDirectionOverriden direction) {
        int position = pager.getCurrentPosition();
        BackgroundBitmapCache instance = BackgroundBitmapCache.getInstance();
        Bitmap cachedBitmap = instance.getBitmapFromBgMemCache(Integer.valueOf(position));
        if(cachedBitmap == null) {
            Bitmap mainBgImageDrawableResource = pager.getDataFromAdapterDataset(position).getMainBackgroundResource();
            if(mainBgImageDrawableResource == null) {
                return;
            }

            cachedBitmap = mainBgImageDrawableResource;
            instance.addBitmapToBgMemoryCache(Integer.valueOf(position), cachedBitmap);
        }

        this.setImageBitmapWithAnimation(cachedBitmap, direction);
    }

    public void updateCurrentBackgroundAsync(ECPagerOverriden pager, final ECBackgroundSwitcherViewOverriden.AnimationDirectionOverriden direction) {
        if(this.mCurrentAnimationTask != null && this.mCurrentAnimationTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            this.getInAnimation().cancel();
        }

        int position = pager.getCurrentPosition();
        Bitmap mainBgImageDrawableResource = pager.getDataFromAdapterDataset(position).getMainBackgroundResource();
        if(mainBgImageDrawableResource != null) {
            this.mCurrentAnimationTask = new BitmapWorkerTaskOverriden(mainBgImageDrawableResource) {
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    ECBackgroundSwitcherViewOverriden.this.setImageBitmapWithAnimation(bitmap, direction);
                }
            };
            this.mCurrentAnimationTask.execute(new Integer[]{Integer.valueOf(position)});
        }
    }

    private void setImageBitmap(Bitmap bitmap) {
        ImageView image = (ImageView)this.getNextView();
        image.setImageBitmap(bitmap);
        this.showNext();
    }

    private Animation createBgImageInAnimation(int fromX, int toX, int transitionDuration, int alphaDuration) {
        TranslateAnimation translate = new TranslateAnimation((float)fromX, (float)toX, 0.0F, 0.0F);
        translate.setDuration((long)transitionDuration);
        AlphaAnimation alpha = new AlphaAnimation(0.0F, 1.0F);
        alpha.setDuration((long)alphaDuration);
        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(translate);
        set.addAnimation(alpha);
        return set;
    }

    private Animation createBgImageOutAnimation(int fromX, int toX, int transitionDuration) {
        TranslateAnimation ta = new TranslateAnimation((float)fromX, (float)toX, 0.0F, 0.0F);
        ta.setDuration((long)transitionDuration);
        ta.setInterpolator(new DecelerateInterpolator());
        return ta;
    }

    static enum AnimationDirectionOverriden {
        LEFT,
        RIGHT;

        private AnimationDirectionOverriden() {
        }
    }
}
