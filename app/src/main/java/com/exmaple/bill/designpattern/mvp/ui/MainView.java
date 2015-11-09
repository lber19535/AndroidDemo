package com.exmaple.bill.designpattern.mvp.ui;


import com.exmaple.bill.designpattern.mvp.model.AppListItem;

import java.util.List;

/**
 * Created by Bill Lv on 2015/11/7.
 */
public interface MainView {
    void updateData(List<AppListItem> items);

}
