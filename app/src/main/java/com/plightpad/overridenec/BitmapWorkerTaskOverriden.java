package com.plightpad.overridenec;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;

import com.ramotion.expandingcollection.BackgroundBitmapCache;
import com.ramotion.expandingcollection.BitmapFactoryOptions;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;
import com.ramotion.expandingcollection.BackgroundBitmapCache;
import com.ramotion.expandingcollection.BitmapFactoryOptions;

public class BitmapWorkerTaskOverriden extends AsyncTask<Integer, Void, Bitmap> {
    private final BackgroundBitmapCache cache;
    private final Bitmap bitmap;

    public BitmapWorkerTaskOverriden(Bitmap bitmap) {
        this.cache = BackgroundBitmapCache.getInstance();
        this.bitmap = bitmap;
    }

    protected Bitmap doInBackground(Integer... params) {
        Integer key = params[0];
        Bitmap cachedBitmap = this.cache.getBitmapFromBgMemCache(key);
        if(cachedBitmap == null) {
            cachedBitmap = this.bitmap;
            this.cache.addBitmapToBgMemoryCache(key, cachedBitmap);
        }

        return cachedBitmap;
    }
}

