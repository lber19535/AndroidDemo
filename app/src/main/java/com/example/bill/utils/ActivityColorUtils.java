package com.example.bill.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.bill.R;
import com.example.bill.utils.ColorUtils;

/**
 * 随机颜色生成演示
 * 
 * 
 * 写完了才发现{@link Color}本身自带hsb转rgb的方法
 * 
 * @author Lv Beier
 *
 */
public class ActivityColorUtils extends AppCompatActivity {

    private View colorView;
    private TextView hsvTv;
    private TextView rgbTv;
    private Button radomBtn;
    private SeekBar hSeekbar;
    private SeekBar sSeekbar;
    private SeekBar vSeekbar;

    private int h = 0;
    private int s = 50;
    private int v = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_color);

        colorView = findViewById(R.id.color_view);

        hsvTv = (TextView) findViewById(R.id.hsv_value_tv);
        rgbTv = (TextView) findViewById(R.id.rgb_value_tv);
        radomBtn = (Button) findViewById(R.id.random_btn);

        radomBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int[] rgb = new int[3];
                ColorUtils.randomColor(rgb);
                float[] hsv = ColorUtils.RGB2HSV(rgb[0], rgb[1], rgb[2]);
                hSeekbar.setProgress((int) hsv[0]);
                sSeekbar.setProgress((int) (hsv[1] * 100));
                vSeekbar.setProgress((int) (hsv[2] * 100));
            }
        });

        hSeekbar = (SeekBar) findViewById(R.id.color_h);
        sSeekbar = (SeekBar) findViewById(R.id.color_s);
        vSeekbar = (SeekBar) findViewById(R.id.color_v);
        System.out.println(hSeekbar.getProgress());
        hSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                h = progress;
                changeColor(h, s, v);
            }
        });

        sSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                s = progress;
                changeColor(h, s, v);

            }
        });

        vSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                v = progress;
                changeColor(h, s, v);
            }
        });

        // progress 默认值为0，第一次设置h的时候其实是没有触发回调事件的
        hSeekbar.setProgress(h);
        sSeekbar.setProgress(s);
        vSeekbar.setProgress(v);

    }

    private void changeColor(int h, int s, int v) {
        ColorHandler handler = new ColorHandler();
        int[] rgb = new int[3];
        int color = ColorUtils.HSV2RGB(h, s, v, rgb);
        handler.sendEmptyMessage(color);
        hsvTv.setText(String.format("(%d, %d, %d)", h, s, v));
        rgbTv.setText(String.format("(%d, %d, %d)", rgb[0], rgb[1], rgb[2]));
    }

    @SuppressLint("HandlerLeak")
    class ColorHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            colorView.setBackgroundColor(msg.what);
        }

    }

}
