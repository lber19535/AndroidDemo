package com.example.bill.app.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by bill_lv on 2016/5/23.
 */
@CoordinatorLayout.DefaultBehavior(CustomFABGroup.Behavior.class)
public class CustomFABGroup extends LinearLayout {
    public CustomFABGroup(Context context) {
        super(context);
        System.out.println("1");
    }

    public CustomFABGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        System.out.println("2");

    }

    public CustomFABGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        System.out.println("3");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomFABGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        System.out.println("child count " + getChildCount());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        System.out.println("measure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        System.out.println("layout");
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("draw");
    }

    public static class Behavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
            return super.layoutDependsOn(parent, child, dependency);
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
            return super.onDependentViewChanged(parent, child, dependency);
        }

        @Override
        public boolean onLayoutChild(CoordinatorLayout parent, FloatingActionButton child, int layoutDirection) {
            return super.onLayoutChild(parent, child, layoutDirection);
        }
    }
}
