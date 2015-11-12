package com.exmaple.bill.third.eventbus.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exmaple.bill.R;
import com.exmaple.bill.third.eventbus.msg.PriorityMessageEvent;
import com.exmaple.bill.third.eventbus.msg.RightMessageEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by bill_lv on 2015/11/12.
 */
public class RightFragment extends Fragment {

    @Bind(R.id.event)
    TextView eventTv;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this, 1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventbus, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public void onEventBackgroundThread(PriorityMessageEvent event) {
        EventBus.getDefault().post(new RightMessageEvent(event.message + PriorityMessageEvent.PRIORITY));
        PriorityMessageEvent.PRIORITY++;
    }

    public void onEventMainThread(RightMessageEvent event) {
        eventTv.setText(event.message);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
