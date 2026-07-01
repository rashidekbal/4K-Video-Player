package com.rtechnologies.videoplayer;

import android.content.Context;

import com.rtechnologies.videoplayer.core.Core;

public class Application extends android.app.Application {
    static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Core.init(this);
    }

    public static Application getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
