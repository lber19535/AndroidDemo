package com.example.bill.app.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.IntDef;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bill on 2016/11/14.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        System.out.println("on start job");

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("xxxxxx");
            }
        }, 3, TimeUnit.SECONDS);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        System.out.println("on stop job");
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("qqqqqqqqqqqq");
        return super.onStartCommand(intent, flags, startId);
    }
}
