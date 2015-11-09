package com.exmaple.bill.designpattern.mvp.model;

/**
 * Created by Bill Lv on 2015/11/7.
 */
public interface MainModel {

    void loadData();
    void setOnLoadListener(OnLoadListener listener);
}
