package com.exmaple.bill.designpattern.mvvm.vm;


import android.view.View;

import com.exmaple.bill.App;
import com.exmaple.bill.databinding.AppListViewBinding;
import com.exmaple.bill.designpattern.mvp.model.AppListItem;
import com.exmaple.bill.designpattern.mvp.model.MainModel;
import com.exmaple.bill.designpattern.mvp.model.MainModelImpl;
import com.exmaple.bill.designpattern.mvp.model.OnLoadListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bill Lv on 2015/11/7.
 */
public class AppListVM implements OnLoadListener {

    private MainModel model;
    private List<AppListItem> items = new ArrayList<>();
    private AppListAdapter adapter;

    public AppListVM(AppListViewBinding bind) {
        model = new MainModelImpl();
        model.setOnLoadListener(this);
        adapter = new AppListAdapter(items, App.getAppContext());
        bind.list.setAdapter(adapter);
    }

    public void loadData() {
        model.loadData();
    }


    @Override
    public void loadSuccess(List<AppListItem> items) {
        this.items.clear();
        this.items.addAll(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadFailed() {

    }
}
