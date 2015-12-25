package com.example.bill.app.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.bill.R;

import java.util.Random;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bill_lv on 2015/12/15.
 */
public class ActivityNotificationBasic extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_notification_basic);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.default_btn)
    public void showDefault() {
        showNotification("default", "default message", NotificationCompat.PRIORITY_DEFAULT);
    }

    @OnClick(R.id.high_btn)
    public void showHigh() {
        showNotification("high", "high message", NotificationCompat.PRIORITY_HIGH);
    }

    @OnClick(R.id.max_btn)
    public void showMax() {
        showNotification("max", "max message", NotificationCompat.PRIORITY_MAX);
    }

    @OnClick(R.id.low_btn)
    public void showLow() {
        showNotification("low", "low message", NotificationCompat.PRIORITY_LOW);
    }

    @OnClick(R.id.min_btn)
    public void showMin() {
        showNotification("min", "min message", NotificationCompat.PRIORITY_MIN);
    }

    private void showNotification(String title, String message, int priority) {
        long[] vibrate = {10,100,100,200}; // star with 10ms delay, turn on 100 ms, turn off 100, turn on 200ms
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setPriority(priority)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText(message)
                .setVibrate(vibrate);

        Intent intent = new Intent(this, ActivityNotificationBasic.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(intent);
        stackBuilder.addParentStack(this);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(new Random().nextInt(), builder.build());

    }
}
