package com.example.bill.app.service;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

/**
 * Created by Bill on 2016/11/14.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        System.out.println("on start job");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        System.out.println("on stop job");
        return false;
    }
}
