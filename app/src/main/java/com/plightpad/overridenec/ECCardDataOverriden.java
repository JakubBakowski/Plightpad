package com.plightpad.overridenec;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import java.util.List;

import android.support.annotation.DrawableRes;
import java.util.List;

public interface ECCardDataOverriden<T> {
    Bitmap getMainBackgroundResource();

    Bitmap getHeadBackgroundResource();

    List<T> getListItems();
}
