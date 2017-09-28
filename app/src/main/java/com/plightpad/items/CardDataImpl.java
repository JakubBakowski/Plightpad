package com.plightpad.items;

import android.graphics.Bitmap;

import com.plightpad.overridenec.ECCardDataOverriden;
import com.plightpad.boxdomain.Lane;

import java.util.List;

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
    private Lane lane;

    public CardDataImpl(String cardTitle, Bitmap mainBackgroundResource, Bitmap headBackgroundResource, Lane lane, String someString) {
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