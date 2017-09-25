package com.plightpad.overridenec;
////
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class ECPagerCardOverriden extends FrameLayout {
    private ECPagerCardContentListOverriden ecPagerCardContentList;
    private boolean animationInProgress;
    private boolean cardExpanded;

    public ECPagerCardOverriden(Context context) {
        super(context);
    }

    public ECPagerCardOverriden(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ECPagerCardOverriden(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();

        try {
            this.ecPagerCardContentList = (ECPagerCardContentListOverriden) this.getChildAt(0);
        } catch (Exception var2) {
            throw new IllegalStateException("Invalid children element in ECPagerCard.");
        }
    }

    public boolean expand() {
        if(!this.animationInProgress && !this.cardExpanded) {
            this.animationInProgress = true;
            ECPagerOverriden pager = (ECPagerOverriden) this.getParent();
            ECPagerViewOverriden pagerView = (ECPagerViewOverriden) pager.getParent();
            pager.disablePaging();
            ViewGroup pagerParent = (ViewGroup)pagerView.getParent();
            int expandedCardWidth = pagerParent.getWidth();
            int expandedCardHeight = pagerParent.getHeight();
            AnimatorListenerAdapter onAnimationEnd = new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    ECPagerCardOverriden.this.animationInProgress = false;
                    ECPagerCardOverriden.this.cardExpanded = true;
                }
            };
            short pushNeighboursDuration = 200;
            short cardAnimDelay = 150;
            short cardAnimDuration = 250;
            pager.animateWidth(expandedCardWidth, pushNeighboursDuration, 0, (AnimatorListenerAdapter)null);
            this.ecPagerCardContentList.animateWidth(expandedCardWidth, cardAnimDuration, cardAnimDelay);
            pagerView.toggleTopMargin(cardAnimDuration, cardAnimDelay);
            pager.animateHeight(expandedCardHeight, cardAnimDuration, cardAnimDelay, onAnimationEnd);
            this.ecPagerCardContentList.getHeadView().animateHeight(pagerView.getCardHeaderExpandedHeight(), cardAnimDuration, cardAnimDelay);
            this.ecPagerCardContentList.showListElements();
            return true;
        } else {
            return false;
        }
    }

    public boolean collapse() {
        if(!this.animationInProgress && this.cardExpanded) {
            this.animationInProgress = true;
            final ECPagerOverriden pager = (ECPagerOverriden) this.getParent();
            ECPagerViewOverriden pagerView = (ECPagerViewOverriden) pager.getParent();
            pager.disablePaging();
            this.ecPagerCardContentList.scrollToTop();
            AnimatorListenerAdapter onAnimationEnd = new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    ECPagerCardOverriden.this.animationInProgress = false;
                    pager.enablePaging();
                    ECPagerCardOverriden.this.cardExpanded = false;
                    ECPagerCardOverriden.this.ecPagerCardContentList.hideListElements();
                }
            };
            short cardAnimDuration = 250;
            short pushNeighboursDelay = 150;
            short pushNeighboursDuration = 200;
            pagerView.toggleTopMargin(cardAnimDuration, 0);
            pager.animateHeight(pagerView.getCardHeight(), cardAnimDuration, 0, (AnimatorListenerAdapter)null);
            this.ecPagerCardContentList.animateWidth(pagerView.getCardWidth(), cardAnimDuration, 0);
            this.ecPagerCardContentList.getHeadView().animateHeight(pagerView.getCardHeight(), cardAnimDuration, 0);
            pager.animateWidth(pagerView.getCardWidth(), pushNeighboursDuration, pushNeighboursDelay, onAnimationEnd);
            return true;
        } else {
            return false;
        }
    }

    public boolean toggle() {
        return this.cardExpanded?this.collapse():this.expand();
    }

    public ECPagerCardContentListOverriden getEcPagerCardContentList() {
        return this.ecPagerCardContentList;
    }
}
