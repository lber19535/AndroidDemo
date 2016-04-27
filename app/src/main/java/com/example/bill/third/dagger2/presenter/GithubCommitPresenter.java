package com.example.bill.third.dagger2.presenter;

import com.example.bill.third.dagger2.api.GithubApi;
import com.example.bill.third.dagger2.model.GithubCommitItem;
import com.example.bill.third.dagger2.ui.ActivityCommitList;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by bill_lv on 2016/4/21.
 */
public class GithubCommitPresenter {


    ActivityCommitList activity;

    private GithubApi api;

    @Inject
    public GithubCommitPresenter() {

    }

    @Inject
    public void setApi(GithubApi api) {
        this.api = api;
    }

    public Observable<List<GithubCommitItem>> loadCommitList(){
        return api.loadCommitList();
    }
}
