package com.plightpad.items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.plightpad.R;
import com.plightpad.overridenec.ECCardDataOverriden;
import com.plightpad.sugardomain.LaneSugar;
import com.ramotion.expandingcollection.ECCardData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Szczypiorek on 01.08.2017.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDataImpl implements ECCardDataOverriden<String> {

    private String cardTitle;
    private String laneName;
    private String message;
    private Bitmap mainBackgroundResource;
    private Bitmap headBackgroundResource;
    private LaneSugar lane;

    public CardDataImpl(String cardTitle, Bitmap mainBackgroundResource, Bitmap headBackgroundResource, LaneSugar lane, String someString) {
        this.mainBackgroundResource = mainBackgroundResource;
        this.headBackgroundResource = headBackgroundResource;
        this.lane = lane;
        this.cardTitle = cardTitle;
    }

    @Override
    public Bitmap getMainBackgroundResource() {
        return mainBackgroundResource;
    }

    @Override
    public Bitmap getHeadBackgroundResource() {
        return headBackgroundResource;
    }

    @Override
    public List<String> getListItems() {
        return null;
    }
}