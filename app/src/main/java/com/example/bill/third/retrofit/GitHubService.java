package com.example.bill.third.retrofit;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by bill_lv on 2015/12/21.
 */
public interface GitHubService {

    // basic
    @GET("/users/{user}/repos")
    Call<ResponseBody> getUserRepo(@Path("user") String user);
    @GET("/users/{user}")
    Call<ResponseBody> getUser(@Path("user") String user);
    @GET("/users/lber19535")
    Call<ResponseBody> getMyInfo();
    @POST("/user/lber19535")
    Call<ResponseBody> getUser(@Body RequestBody body);
    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("/users/{username}")
    Call<ResponseBody> getUser2(@Path("username") String username);


    // converter
    @GET("/users/{user}")
    Call<User> getUserBean(@Path("user") String user);

    // call adapter
    @GET("/users/{user}")
    MyCall<User> getUser3(@Path("user") String user);
}
