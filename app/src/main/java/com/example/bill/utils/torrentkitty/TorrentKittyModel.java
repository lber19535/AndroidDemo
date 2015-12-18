package com.example.bill.utils.torrentkitty;

import com.orhanobut.logger.Logger;
import com.squareup.okhttp.ResponseBody;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 紗倉まな, 鈴原 エミリ, さとう愛理, tek 067, 城田理加, 古川 いおり, 星野美优, 成濑心美, 中西里菜
 * Created by bill_lv on 2015/12/17.
 */
public class TorrentKittyModel {

    private static final String TORRENT_KITTY_URL = "http://www.torrentkitty.net";

    public TorrentKittyModel() {
        Logger.init("TorrentKitty");
    }

    public Observable<MagnetContent> requestTorrent(final String key) {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TORRENT_KITTY_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        TorrentKittyApi kitty = retrofit.create(TorrentKittyApi.class);

        Observable<ResponseBody> result = kitty.search(key);

        return result.map(new Func1<ResponseBody, String>() {
            public String call(ResponseBody responseBody) {
                String content = "";
                try {
                    content = responseBody.string();
                    if (content.isEmpty())
                        Logger.d("search request is empty");

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    return content;
                }
            }
        }).map(new Func1<String, Document>() {
            public Document call(String s) {
                return Jsoup.parse(s);
            }
        }).map(new Func1<Document, Integer>() {
            public Integer call(Document document) {

                Elements pages = document.select("div[class=pagination] > a");

                if (pages.size() == 0) {
                    Logger.d("page count is 1");
                    return 1;
                } else {
                    String pageString = pages.get(pages.size() - 2).text();
                    Logger.d("page count is " + pageString);
                    return Integer.valueOf(pageString);
                }
            }
        }).flatMap(new Func1<Integer, Observable<Integer>>() {
            public Observable<Integer> call(Integer maxPageNums) {
                return Observable.range(1, maxPageNums);
            }
        }).flatMap(new Func1<Integer, Observable<ResponseBody>>() {
            public Observable<ResponseBody> call(Integer page) {
                TorrentKittyApi kitty = retrofit.create(TorrentKittyApi.class);
                Logger.d("prepare to load page " + page);
                return kitty.pages(key, page + "");
            }
        }).map(new Func1<ResponseBody, String>() {
            public String call(ResponseBody responseBody) {
                String content = "";
                try {
                    content = responseBody.string();
                    if (content.isEmpty())
                        Logger.d("page content is empty");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    return content;
                }
            }
        }).map(new Func1<String, Document>() {
            public Document call(String s) {
                return Jsoup.parse(s);
            }
        }).flatMapIterable(new Func1<Document, Elements>() {
            public Elements call(Document document) {
                Elements elements = document.select("table[id=archiveResult] > tbody > tr");
                return elements;
            }
        }).map(new Func1<Element, MagnetContent>() {
            public MagnetContent call(Element element) {
                return new MagnetContent(element.select("td[class=name]").text(), element.select("a[rel=magnet]").attr("href"));
            }
        }).distinct(new Func1<MagnetContent, String>() {
            public String call(MagnetContent magnetContent) {
                Logger.d("load item is \n" + magnetContent.toString());
                return magnetContent.getName();
            }
        }).filter(new Func1<MagnetContent, Boolean>() {
            public Boolean call(MagnetContent magnetContent) {
                // filter is empty temporary
                return true;
            }
        }).subscribeOn(Schedulers.io());

    }
}
