package com.example.bill.app.channel;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.bill.R;

/**
 * Created by Bill on 2017/6/12.
 */
@TargetApi(Build.VERSION_CODES.O)
public class ActivityChannel extends AppCompatActivity {

    private int id = 1;
    private NotificationManager manager;
    private static final String CHANNEL_ID = "BILL";
    private int importanceLevel = NotificationManager.IMPORTANCE_DEFAULT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        initView();

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void initView() {
        Spinner levelSp = (Spinner) findViewById(R.id.notification_level);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.notification_level, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSp.setAdapter(adapter);
        levelSp.setSelection(NotificationManager.IMPORTANCE_DEFAULT + 1);
        levelSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    importanceLevel = NotificationManager.IMPORTANCE_UNSPECIFIED;
                }
                importanceLevel = i - 1;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void createNotificationChannel(String channelId) {
        String name = "Demo";
        String desc = "Notification Test";

        NotificationChannel channel = new NotificationChannel(channelId, name, importanceLevel);
        channel.setDescription(desc);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        manager.createNotificationChannel(channel);
    }

    private void deleteNotificationChannel(String channelId) {
        manager.deleteNotificationChannel(channelId);
    }

    public void deleteChannelId(View v) {
        deleteNotificationChannel(CHANNEL_ID);
    }

    public void createChannelId(View v) {
        createNotificationChannel(CHANNEL_ID);
    }

    public void postChannelNotification(View v) {
        Notification build = new Notification.Builder(this).setContentTitle("content title")
                .setContentText("content text")
                .setSmallIcon(R.drawable.ic_launcher)
                .setChannelId(CHANNEL_ID).build();
        manager.notify(this.id++, build);
    }
}
