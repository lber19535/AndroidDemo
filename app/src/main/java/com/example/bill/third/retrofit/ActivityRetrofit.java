package com.example.bill.third.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.bill.R;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2015/12/21.
 */
public class ActivityRetrofit extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_basic);
        Logger.init("ActivityRetrofit");
    }

    public void getUser(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .build();

        GitHubService githubService = retrofit.create(GitHubService.class);

        Call<ResponseBody> users = githubService.getUser5("lber19535");
        users.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                Logger.d(response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Logger.e(t, "error");
            }
        });
    }

    public void getUserByConverter(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(new UserConverterFactory())
                .addConverterFactory(new CustomConverterFactory())
                .build();

        GitHubService githubService = retrofit.create(GitHubService.class);

        Call<User> users = githubService.getUserBean("lber19535");
        users.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> user, Retrofit retrofit) {
                Logger.d(user.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                Logger.e(t, "error");
            }
        });
    }

    public void getUserByAdapter(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(new UserConverterFactory())
                .addConverterFactory(new CustomConverterFactory())
                .addCallAdapterFactory(new CustomAdapterFactory())
                .build();

        GitHubService githubService = retrofit.create(GitHubService.class);

        MyCall<User> users = githubService.getUser3("lber19535");
        Logger.d(users.string().getName());
    }

    public void getUserByObservable(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(new UserConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        GitHubService githubService = retrofit.create(GitHubService.class);

        githubService.getUser4("lber19535")
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        Logger.d(user.getName());
                    }
                });
    }

}
