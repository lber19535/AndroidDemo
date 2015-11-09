package com.exmaple.bill.designpattern.mvp.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Bill Lv on 2015/11/7.
 */
public class AppListItem {

    private String appName;
    private String appPkg;
    private Drawable appIcon;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPkg() {
        return appPkg;
    }

    public void setAppPkg(String appPkg) {
        this.appPkg = appPkg;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
