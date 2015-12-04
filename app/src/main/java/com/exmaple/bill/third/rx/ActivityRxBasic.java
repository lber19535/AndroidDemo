package com.exmaple.bill.third.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.exmaple.bill.databinding.utils.NameUtils;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Basic about RxJava
 * <p/>
 * Created by bill_lv on 2015/12/3.
 */
public class ActivityRxBasic extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
            }

            @Override
            public void onNext(String s) {
                System.out.println("next " + s);
            }
        };

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
                subscriber.onNext("hi");
                subscriber.onNext("bill");
                subscriber.onCompleted();
            }
        });

        Observable observable1 = Observable.just("hello ","hi ","just ");
        String[] words = {"helle", "hi", "words"};

        Observable observable2  = Observable.from(words);

        observable.subscribe(observer);
        observable1.subscribe(observer);
        observable2.subscribe(observer);

        Action1<String> action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("action 1");
            }
        };

        Action1<Throwable> action2 = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.out.println("action 1 throwable");
            }
        };

        Action0 action0 = new Action0() {
            @Override
            public void call() {
                System.out.println("action 0");
            }
        };
        observable.subscribe(action1, action2, action0);

        Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String name) {
                System.out.println(name);
            }
        };
        Observable<String> nameObservable = Observable.from(NameUtils.FIRST_NAMES);
        nameObservable.subscribeOn(Schedulers.io()).observeOn(Schedulers.io());

        nameObservable.subscribe(nameObserver);
        new Thread().start();
    }
}
