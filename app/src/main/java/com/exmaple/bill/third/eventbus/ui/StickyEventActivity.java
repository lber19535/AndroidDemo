package com.exmaple.bill.third.eventbus.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.exmaple.bill.R;
import com.exmaple.bill.third.eventbus.msg.StickyMessageEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by bill_lv on 2015/11/12.
 */
public class StickyEventActivity extends AppCompatActivity {

    @Bind(R.id.sticky_event)
    TextView stickyEventTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_event);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @OnClick(R.id.sticky_event)
    public void sendEvent(){
        EventBus.getDefault().post(new StickyMessageEvent("Normal Event"));
    }

    public void onEventMainThread(StickyMessageEvent event) {
        stickyEventTv.setText(event.message);
    }

}
