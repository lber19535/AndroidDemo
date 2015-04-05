package com.exmaple.bill.anim.demo;

import com.exmaple.bill.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * Drawable Animation
 * 
 * @author Bill-pc
 *
 */
public class DrawableAnimationActivity extends Activity {

    private AnimationDrawable mAnimationDrawable;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_anim);

        ImageView animImageView = (ImageView) findViewById(R.id.drawable_anim);
        animImageView.setBackground(getResources().getDrawable(
                R.drawable.drawable_anim));
        mAnimationDrawable = (AnimationDrawable) animImageView.getBackground();

        findViewById(R.id.start).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mAnimationDrawable.start();
            }
        });

        findViewById(R.id.stop).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mAnimationDrawable.stop();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // start()无法在onCreate中使用
        mAnimationDrawable.start();
    }
}
