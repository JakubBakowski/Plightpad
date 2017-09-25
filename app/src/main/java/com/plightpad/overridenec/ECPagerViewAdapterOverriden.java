package com.plightpad.overridenec;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.plightpad.LanesActivity;
import com.plightpad.R;
import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPager;
import com.ramotion.expandingcollection.ECPagerCard;
import com.ramotion.expandingcollection.ECPagerView;

import java.util.List;

/**
 * Created by Szczypiorek on 05.08.2017.
 */

public abstract class ECPagerViewAdapterOverriden extends PagerAdapter {
    private ECPagerCardOverriden activeCard;
    private List<ECCardDataOverriden> dataset;
    private LayoutInflater inflaterService;
    private Context context;

    public ECPagerViewAdapterOverriden(Context applicationContext, List<ECCardDataOverriden> dataset) {
        this.inflaterService = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dataset = dataset;
        this.context = applicationContext;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        ECPagerOverriden pager = (ECPagerOverriden) container;
        ECPagerCardOverriden pagerCard = (ECPagerCardOverriden) this.inflaterService.inflate(context.getResources().getLayout(R.layout.ec_pager_card_overriden), (ViewGroup)null);
        ECPagerViewOverriden pagerContainer = (ECPagerViewOverriden) pager.getParent();
        ECPagerCardContentListOverriden ecPagerCardContentList = pagerCard.getEcPagerCardContentList();
        ECPagerCardHeadOverriden headView = ecPagerCardContentList.getHeadView();
        headView.setHeight(pagerContainer.getCardHeight());
        Bitmap drawableRes = ((ECCardDataOverriden)this.dataset.get(position)).getHeadBackgroundResource();
        if(drawableRes != null) {
            headView.setHeadImageBitmap(drawableRes);
        }

        this.instantiateCard(this.inflaterService, headView, ecPagerCardContentList, (ECCardDataOverriden)this.dataset.get(position));
        pager.addView(pagerCard, pagerContainer.getCardWidth(), pagerContainer.getCardHeight());
        return pagerCard;
    }

    public abstract void instantiateCard(LayoutInflater var1, ViewGroup var2, ListView var3, ECCardDataOverriden var4);

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        this.activeCard = (ECPagerCardOverriden)object;
    }

    public ECPagerCardOverriden getActiveCard() {
        return this.activeCard;
    }

    public int getCount() {
        return this.dataset.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public List<ECCardDataOverriden> getDataset() {
        return this.dataset;
    }
}
