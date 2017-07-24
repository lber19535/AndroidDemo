package com.example.bill.third.dagger2.di.component;

import com.example.bill.third.dagger2.api.GithubApi;
import com.example.bill.third.dagger2.di.module.GithubCommitModule;
import com.example.bill.third.dagger2.di.scope.GithubApiScope;
import com.example.bill.third.dagger2.ui.ActivityCommitList;

import dagger.Component;

/**
 * Created by bill_lv on 2016/4/21.
 */
@GithubApiScope
@Component(modules = GithubCommitModule.class)
public interface GithubApiComponent {

    GithubApi getGithubApi();

    void injectActivity(ActivityCommitList activity);
}
