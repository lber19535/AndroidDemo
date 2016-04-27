package com.example.bill.third.swipeback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.bill.R;
import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;

/**
 * Created by bill_lv on 2016/4/14.
 */
public class ActivitySwipeBack extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SwipeBack.attach(this, SwipeBack.Type.OVERLAY,Position.LEFT)
                .setContentView(R.layout.activity_swipe_back)
                .setDividerEnabled(true)
                .setSwipeBackView(R.layout.swipeback_default);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.swipeback_stack_to_front,
                R.anim.swipeback_stack_right_out);
    }
}
