package com.example.bill.third.dagger2.service;

import com.example.bill.third.dagger2.model.GithubCommitItem;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by bill_lv on 2016/4/21.
 */
public interface GithubCommitService {

    @GET("/repos/{owner}/{repo}/commits")
    Observable<List<GithubCommitItem>> getRepoCommits(@Path("owner") String owner, @Path("repo")String repoNmae, @Query("since")String date);
}
