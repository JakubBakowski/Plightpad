package com.plightpad;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.plightpad.boxdomain.MyObjectBox;

import io.objectbox.BoxStore;
import lombok.Getter;

public class App extends Application {

    private static App sApp;

    @Getter
    private BoxStore boxStore;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
    }

    public static App getApp() {
        return sApp;
    }
}
