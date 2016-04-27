package com.example.bill.third.uil;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bill.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

public class ActivityUIL extends AppCompatActivity {

    private static final String IMG_URL = "http://pic2.zhimg.com/50/8df2ac35a82b6aec59df0271927216cd_b.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uil);

        final ImageView iv = (ImageView) findViewById(R.id.img);
        final TextView tv = (TextView) findViewById(R.id.cache_path);

        DisplayImageOptions opt = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(opt).build();
        ImageLoader.getInstance().init(configuration);

        ImageLoader.getInstance().displayImage(IMG_URL, iv, opt, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                tv.setText(Uri.fromFile(ImageLoader.getInstance().getDiskCache().get(imageUri)).toString());
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                System.out.println(current);
                System.out.println(total);
            }
        });


    }

}
