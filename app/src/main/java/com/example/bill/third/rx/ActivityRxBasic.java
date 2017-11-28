package com.example.bill.third.rx;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bill.R;
import com.example.bill.databinding.utils.NameUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Basic about RxJava
 * <p/>
 * Created by bill_lv on 2015/12/3.
 */
public class ActivityRxBasic extends AppCompatActivity {

    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_basic);
        ButterKnife.bind(this);
    }

    public void onLoadImage(View v) {
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    drawable = getDrawable(R.drawable.ic_launcher);
                else
                    drawable = getResources().getDrawable(R.drawable.ic_launcher);

                subscriber.onNext(drawable);
            }
        }).subscribe(new Subscriber<Drawable>() {
            @Override
            public void onCompleted() {
                Toast.makeText(ActivityRxBasic.this, "Load Image Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(ActivityRxBasic.this, "Load Image Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Drawable drawable) {
                img.setImageDrawable(drawable);
            }
        });
    }

    public void onObAction(View v) {
        Action1<String> next = new Action1<String>() {
            @Override
            public void call(String s) {
                Toast.makeText(ActivityRxBasic.this, "action next " + s, Toast.LENGTH_SHORT).show();
            }
        };

        Action1<Throwable> error = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toast.makeText(ActivityRxBasic.this, "action error ", Toast.LENGTH_SHORT).show();
            }
        };

        Action0 complete = new Action0() {
            @Override
            public void call() {
                Toast.makeText(ActivityRxBasic.this, "action complete", Toast.LENGTH_SHORT).show();
            }
        };

        Observable<String> nameObservable = Observable.from(NameUtils.FIRST_NAMES);
        nameObservable.subscribe(next, error, complete);
    }

    public void onObFrom(View v) {
        String[] words = {"from", "hi", "words"};

        Observable observable = Observable.from(words);
        observable.subscribe(new ObserverString());
    }

    public void onObJust(View v) {
        Observable observable = Observable.just("just ", "hi ", "just ");
        observable.subscribe(new ObserverString());
    }

    public void onObCreate(View v) {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("create");
                subscriber.onNext("hi");
                subscriber.onNext("bill");
                subscriber.onCompleted();
            }
        });
        observable.subscribe(new ObserverString());
    }

    public void onObSub(View v) {
        Observable observable = Observable.just("sub", "hi ", "bill ");
        observable.subscribe(new SubscriberString());
    }

    class ObserverString implements Observer<String> {

        @Override
        public void onCompleted() {
            Toast.makeText(ActivityRxBasic.this, "ob completed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(ActivityRxBasic.this, "ob error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(String s) {
            Toast.makeText(ActivityRxBasic.this, "ob next " + s, Toast.LENGTH_SHORT).show();
        }
    }

    class SubscriberString extends Subscriber<String> {

        @Override
        public void onCompleted() {
            Toast.makeText(ActivityRxBasic.this, "sub completed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(ActivityRxBasic.this, "sub error", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(String s) {
            Toast.makeText(ActivityRxBasic.this, "sub next " + s, Toast.LENGTH_SHORT).show();
        }

    }
}
