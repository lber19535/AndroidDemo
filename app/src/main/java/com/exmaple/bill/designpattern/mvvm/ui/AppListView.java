package com.exmaple.bill.designpattern.mvvm.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.exmaple.bill.R;
import com.exmaple.bill.databinding.AppListViewBinding;
import com.exmaple.bill.designpattern.mvvm.vm.AppListVM;

/**
 * Created by bill_lv on 2015/11/30.
 */
public class AppListView extends AppCompatActivity {

    private AppListViewBinding bind;
    private AppListVM vm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);

        bind = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);

        vm = new AppListVM(bind);

        vm.loadData();

    }
}
