package com.example.bill.third.dagger2.api;

import com.example.bill.third.dagger2.model.GithubCommitItem;
import com.example.bill.third.dagger2.service.GithubCommitService;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by bill_lv on 2016/4/26.
 */
public class GithubApi {

    private GithubCommitService service;

    @Inject
    public GithubApi(GithubCommitService service) {
        this.service = service;
    }

    public Observable<List<GithubCommitItem>> loadCommitList(){
        return service.getRepoCommits("lber19535", "ZhiHu", "2016-01-01T09:28:04Z");
    }
}
