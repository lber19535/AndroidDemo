package com.example.bill.designpattern.mvp.model;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.example.bill.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bill Lv on 2015/11/7.
 */
public class MainModelImpl implements MainModel {

    private OnLoadListener listener;
    private List<AppListItem> items;

    public MainModelImpl(){
        items = new ArrayList<>();
    }

    @Override
    public void loadData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                PackageManager pm = App.getAppContext().getPackageManager();
                List<ApplicationInfo> infos = pm.getInstalledApplications(PackageManager.GET_META_DATA);
                for (ApplicationInfo info : infos) {
                    AppListItem item = new AppListItem();
                    item.setAppIcon(info.loadIcon(pm));
                    item.setAppName(info.loadLabel(pm).toString());
                    item.setAppPkg(info.packageName);
                    System.out.println(info.packageName);
                    System.out.println(info.loadLabel(pm));
                    items.add(item);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                listener.loadSuccess(items);
            }
        }.execute();

    }

    @Override
    public void setOnLoadListener(OnLoadListener listener) {
        this.listener = listener;
    }
}
