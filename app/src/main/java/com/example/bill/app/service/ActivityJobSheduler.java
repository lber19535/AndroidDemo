package com.example.bill.app.service;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.bill.R;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ActivityJobSheduler extends AppCompatActivity {

    private static final String TAG = "ActivityJobSheduler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_sheduler);

        findViewById(R.id.job_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(getPackageName(), MyJobService.class.getName()));
                builder.setPeriodic(3000);
                if (scheduler.schedule(builder.build()) <= 0){
                    Log.d(TAG, "onClick: something wrong");
                }
            }
        });
    }
}
