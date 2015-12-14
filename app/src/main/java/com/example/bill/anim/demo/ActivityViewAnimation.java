package com.example.bill.anim.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.bill.R;

public class ActivityViewAnimation extends AppCompatActivity implements OnClickListener {

    private TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween_anim);

        view = (TextView) findViewById(R.id.text);

        findViewById(R.id.translate).setOnClickListener(this);
        findViewById(R.id.spin).setOnClickListener(this);
        findViewById(R.id.alpha).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Animation animation = null;
        switch (id) {
        case R.id.translate:
            animation = AnimationUtils.loadAnimation(
                    ActivityViewAnimation.this, R.anim.tween_anim_translate);
            break;
        case R.id.spin:
            animation = AnimationUtils.loadAnimation(
                    ActivityViewAnimation.this, R.anim.tween_anim_spin);
            break;
        case R.id.alpha:
            animation = AnimationUtils.loadAnimation(
                    ActivityViewAnimation.this, R.anim.tween_anim_alpha);
            break;
        default:
            break;
        }
        view.startAnimation(animation);
    }

}
