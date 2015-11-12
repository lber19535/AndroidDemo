package com.exmaple.bill.third.eventbus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.exmaple.bill.R;
import com.exmaple.bill.third.eventbus.msg.AsyncMessageEvent;
import com.exmaple.bill.third.eventbus.msg.BackgroundMessageEvent;
import com.exmaple.bill.third.eventbus.msg.MessageEvent;
import com.exmaple.bill.third.eventbus.msg.PriorityMessageEvent;
import com.exmaple.bill.third.eventbus.msg.StickyMessageEvent;
import com.exmaple.bill.third.eventbus.msg.UIMessageEvent;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by bill_lv on 2015/11/11.
 */
public class EventBusActivity extends AppCompatActivity {

    @Bind(R.id.on_ui_thread_text)
    TextView uiTextTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @OnClick(R.id.send_post_thread_event)
    public void sendPostEvent() {
        EventBus.getDefault().post(new MessageEvent("Post Thread Event"));
    }

    @OnClick(R.id.send_ui_thread_event)
    public void sendPostUIEvent() {
        EventBus.getDefault().post(new UIMessageEvent("Post UI Event"));
    }

    @OnClick(R.id.send_back_thread_event)
    public void sendPostBackEvent() {
        EventBus.getDefault().post(new BackgroundMessageEvent("Post Back Event"));
    }

    @OnClick(R.id.send_priority_event)
    public void sendPostPriorityEvent() {
        EventBus.getDefault().post(new PriorityMessageEvent("Post Priority Event"));
    }

    @OnClick(R.id.send_sticky_event)
    public void sendSrickyEvent(){
        EventBus.getDefault().postSticky(new StickyMessageEvent("Sticky Event"));
    }

    @OnClick(R.id.open_stick_activity)
    public void openStickyActivity(){
        Intent intent = new Intent(this, StickyEventActivity.class);
        startActivity(intent);
    }

    /**
     * 从哪个线程 post 的，event 就在哪个线程执行
     *
     * @param event
     */
    public void onEvent(MessageEvent event) {
        Toast.makeText(this, event.message, Toast.LENGTH_LONG).show();
    }

    /**
     * 总是 UI 线程
     *
     * @param event
     */
    public void onEventMainThread(UIMessageEvent event) {
        uiTextTv.setText(event.message);
    }

    /**
     * 后台线程，使用 Executor 执行的一个 Runnable, Runnable 中有一个队列，每隔一秒轮询下看看有没有 Event 来，所以每次 Event 都是在同一个  Thread 中
     *
     * @param event
     */
    public void onEventBackgroundThread(BackgroundMessageEvent event) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        EventBus.getDefault().post(new UIMessageEvent(event.message));
    }

    /**
     * 后台线程，每次 Event 都会建一个新的 Runnable 去执行方法中的内容
     *
     * @param event
     */
    public void onEventAsync(AsyncMessageEvent event) {
        EventBus.getDefault().post(new UIMessageEvent(event.message));
    }

    public void onEventMainThread(StickyMessageEvent event){
        Toast.makeText(this, event.message, Toast.LENGTH_LONG).show();
    }
}
