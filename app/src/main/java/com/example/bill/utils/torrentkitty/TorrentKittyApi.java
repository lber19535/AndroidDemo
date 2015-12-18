package com.example.bill.utils.torrentkitty;

import com.squareup.okhttp.ResponseBody;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by bill_lv on 2015/12/17.
 */
public interface TorrentKittyApi {

    @GET("/search/{q}")
    Observable<ResponseBody> search(@Path("q") String keyWord);

    @GET("/search/{q}/{p}")
    Observable<ResponseBody> pages(@Path("q") String keyWord, @Path("p") String page);
}
