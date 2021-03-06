package com.example.bill.designpattern.mvvm.model;

import java.util.List;

/**
 * Created by Bill Lv on 2015/11/7.
 */
public interface OnLoadListener {

    void loadSuccess(List<AppListItem> items);
    void loadFailed();
}
