package com.example.bill.designpattern.mvp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


import com.example.bill.R;
import com.example.bill.designpattern.mvp.model.AppListItem;
import com.example.bill.designpattern.mvp.presenter.MainPresenter;
import com.example.bill.designpattern.mvp.presenter.MainPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityMVP extends AppCompatActivity implements MainView {

    private AppListAdapter adapter;
    private List<AppListItem> items;

    private MainPresenter mainPresenter;

    @Bind(R.id.list)
    ListView appLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        ButterKnife.bind(this);

        mainPresenter = new MainPresenterImpl(this);

        items = new ArrayList<>();
        adapter = new AppListAdapter(items, this);

        appLv.setAdapter(adapter);

        mainPresenter.getAppList();
    }

    @Override
    public void updateData(List<AppListItem> items) {
        this.items.clear();
        this.items.addAll(items);
        adapter.notifyDataSetChanged();
    }
}
