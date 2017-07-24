package com.example.bill.third.dagger2.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.bill.R;
import com.example.bill.third.dagger2.di.component.DaggerGithubApiComponent;
import com.example.bill.third.dagger2.di.component.GithubApiComponent;
import com.example.bill.third.dagger2.model.GithubCommitItem;
import com.example.bill.third.dagger2.presenter.GithubCommitPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/4/21.
 */
public class ActivityCommitList extends AppCompatActivity {

    @Inject
    GithubCommitPresenter presenter;

    private List<GithubCommitItem> items = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_commit);

        ListView listView = (ListView) findViewById(R.id.list);
        final CommitListAdapter adapter = new CommitListAdapter(items, this);
        listView.setAdapter(adapter);

        GithubApiComponent githubApiComponent = DaggerGithubApiComponent.create();
        githubApiComponent.injectActivity(this);

        presenter.loadCommitList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<GithubCommitItem>>() {
                    @Override
                    public void call(List<GithubCommitItem> githubCommitItems) {
                        items.addAll(githubCommitItems);
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
