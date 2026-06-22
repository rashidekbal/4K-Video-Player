package com.rtechnologies.videoplayer;

import android.content.Context;

public class Application extends android.app.Application {
    static Application instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static Application getInstance() {
        return instance;
    }
    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
