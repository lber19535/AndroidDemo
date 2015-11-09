package com.exmaple.bill;

import android.app.Application;
import android.content.Context;

/**
 * Created by Bill Lv on 2015/11/7.
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext() {
        return mContext;
    }
}
