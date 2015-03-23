package com.sssta.ropetest;

import android.app.Application;
import android.content.Context;

/**
 * Created by cauchywei on 15/3/5.
 */
public class MainApplication extends Application {

    static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
