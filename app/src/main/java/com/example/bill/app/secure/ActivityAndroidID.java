package com.example.bill.app.secure;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.bill.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by bill_lv on 2016/1/19.
 */
public class ActivityAndroidID extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_id);

        ((TextView) findViewById(R.id.device_id)).setText(Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID));
    }

}
