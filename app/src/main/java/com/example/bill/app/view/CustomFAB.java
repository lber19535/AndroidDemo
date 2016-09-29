package com.example.bill.app.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by bill_lv on 2016/5/23.
 */
public class CustomFAB extends ImageView {
    public CustomFAB(Context context) {
        super(context);
    }

    public CustomFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(56, 56);

    }

    private int resolveMeasureSpec(int spec) {
        return MeasureSpec.getMode(spec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    int shadowPendding = 5;

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println(canvas.getHeight());

        Paint paint = new Paint();
        final BlurMaskFilter filter = new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL);
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setAlpha(50);
        paint.setMaskFilter(filter);
        paint.setFilterBitmap(true);
        canvas.drawPaint(paint);

    }
}
