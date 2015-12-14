package com.example.bill.third.rx;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bill.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RxJava Scheduler Demo
 * <p/>
 * io, computation, ui thread, new thread
 * <p/>
 * Created by bill_lv on 2015/12/7.
 */
public class ActivityRxScheduler extends AppCompatActivity {

    @Bind(R.id.img)
    ImageView img;

    @Bind(R.id.sumTv)
    TextView sumTv;

    private static final int INTERNET_PERMISSION = 0x01;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_scheduler);
        ButterKnife.bind(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case INTERNET_PERMISSION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    loadImage();
                break;
            }
            default:
                break;
        }
    }

    /**
     * compute random numbers sum
     *
     * @param v
     */
    public void onCompute(View v) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                int sum = 0;
                Random rad = new Random();
                for (int i = 0; i < 100000000; i++) {
                    sum += rad.nextInt(10);
                }
                subscriber.onNext(sum + "");
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                sumTv.setText(s);
            }
        });
    }

    /**
     * load image from net
     * <p/>
     * use RxJava and RxAndroid
     *
     * @param v
     */
    public void onLoadImage(View v) {
        if (checkCallingOrSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            loadImage();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION);
            else
                Toast.makeText(ActivityRxScheduler.this, "no internet permission", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadImage() {
        Observable.OnSubscribe onSubscribe = new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                try {
                    if (checkCallingOrSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                        URL url = new URL("http://lber19535.github.io/img/logo.png");
                        URLConnection connection = url.openConnection();
                        BitmapDrawable drawable = new BitmapDrawable(getResources(), connection.getInputStream());
                        subscriber.onNext(drawable);
                    } else {
                        subscriber.onError(new Throwable("no internet permission"));
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Subscriber subscriber = new Subscriber<Drawable>() {
            @Override
            public void onCompleted() {
                Toast.makeText(ActivityRxScheduler.this, "load img complete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(ActivityRxScheduler.this, "load img error " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Drawable drawable) {
                img.setImageDrawable(drawable);
            }
        };

        Observable.create(onSubscribe).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

}
