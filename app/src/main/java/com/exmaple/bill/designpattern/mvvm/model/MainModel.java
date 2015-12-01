package com.exmaple.bill.designpattern.mvvm.model;

/**
 * Created by Bill Lv on 2015/11/7.
 */
public interface MainModel {

    void loadData();
    void setOnLoadListener(OnLoadListener listener);
}
