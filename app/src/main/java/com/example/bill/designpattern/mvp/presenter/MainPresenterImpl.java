package com.example.bill.designpattern.mvp.presenter;


import com.example.bill.designpattern.mvp.model.AppListItem;
import com.example.bill.designpattern.mvp.model.MainModel;
import com.example.bill.designpattern.mvp.model.MainModelImpl;
import com.example.bill.designpattern.mvp.model.OnLoadListener;
import com.example.bill.designpattern.mvp.ui.MainView;

import java.util.List;

/**
 * Created by Bill Lv on 2015/11/7.
 */
public class MainPresenterImpl implements MainPresenter,OnLoadListener {

    private MainView view;
    private MainModel model;


    public MainPresenterImpl(MainView view) {
        this.view = view;
        model = new MainModelImpl();
        model.setOnLoadListener(this);
    }

    @Override
    public void getAppList() {
        model.loadData();
    }

    @Override
    public void loadSuccess(List<AppListItem> items) {
        view.updateData(items);
    }

    @Override
    public void loadFailed() {

    }
}
