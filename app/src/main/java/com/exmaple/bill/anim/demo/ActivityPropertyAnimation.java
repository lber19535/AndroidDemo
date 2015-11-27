package com.exmaple.bill.anim.demo;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.exmaple.bill.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bill_lv on 2015/11/24.
 */
public class ActivityPropertyAnimation extends AppCompatActivity {

    @Bind(R.id.target_tv)
    TextView targetTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.value_anim_btn)
    public void performValueAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 100f);
        animator.setDuration(3000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                targetTv.setText(animation.getAnimatedValue().toString());
            }
        });
    }

    @OnClick(R.id.alpha_anim_btn)
    public void performAlphaAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetTv, "alpha", 0f, 1f);
        animator.setDuration(1000);
        animator.start();
    }

    @OnClick(R.id.alpha_custom_anim_btn)
    public void performCustomInterpolator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetTv, "alpha", 0f, 1f);
        animator.setDuration(2000);
        animator.setInterpolator(new CustomInterpolator());
        animator.start();
    }

    @OnClick(R.id.alpha_keyf_anim_btn)
    public void performKeyframe() {
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.5f, 360f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
        ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(targetTv, pvhRotation);
        rotationAnim.setDuration(5000);
        rotationAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        rotationAnim.start();
    }

    @OnClick(R.id.view_property_animator_btn)
    public void performViewAnim() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            targetTv.animate().translationZ(200f).setDuration(3000).start();
    }

    class CustomInterpolator implements TimeInterpolator {

        @Override
        public float getInterpolation(float input) {
            System.out.println("input is " + input);
            return input;
        }
    }
}
