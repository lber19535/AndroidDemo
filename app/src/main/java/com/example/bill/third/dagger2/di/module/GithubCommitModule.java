package com.example.bill.third.dagger2.di.module;

import com.example.bill.third.dagger2.di.scope.GithubApiScope;
import com.example.bill.third.dagger2.service.GithubCommitService;

import dagger.Module;
import dagger.Provides;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by bill_lv on 2016/4/21.
 */
@Module
public class GithubCommitModule {

    @Provides
    @GithubApiScope
    static GithubCommitService provideCommitService() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(GithubCommitService.class);
    }
}
