package com.plightpad.overridenec;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.List;

public abstract class ECCardContentListItemAdapterOverriden<T> extends ArrayAdapter<T> {
    private boolean zeroItemsMode = true;

    public ECCardContentListItemAdapterOverriden(@NonNull Context context, @LayoutRes int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
    }

    public final int getCount() {
        return this.zeroItemsMode?0:super.getCount();
    }

    @NonNull
    public abstract View getView(int var1, @Nullable View var2, @NonNull ViewGroup var3);

    protected final void enableZeroItemsMode() {
        this.zeroItemsMode = true;
        this.notifyDataSetChanged();
    }

    protected final void disableZeroItemsMode() {
        this.zeroItemsMode = false;
        this.notifyDataSetChanged();
    }
}

