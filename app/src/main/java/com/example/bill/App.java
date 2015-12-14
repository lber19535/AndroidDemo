package com.example.bill;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.Logger;

/**
 * Created by Bill Lv on 2015/11/7.
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Logger.init("Android Demo");
    }

    public static Context getAppContext() {
        return mContext;
    }
}
