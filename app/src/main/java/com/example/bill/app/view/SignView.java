package com.example.bill.app.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * sign view
 * 
 * @author Lv Beier
 *
 */
public class SignView extends View {

    private static final String DEFAULT_IMAGE_PATH = "/img";
    private static final String DEFAULT_IMAGE_NAME = "temp_image.png";

    private float mX;
    private float mY;

    private final Paint mGesturePaint = new Paint();
    private final Path mPath = new Path();

    public SignView(Context context) {
        super(context);
    }

    public SignView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mGesturePaint.setAntiAlias(true);
        mGesturePaint.setStyle(Style.STROKE);
        mGesturePaint.setStrokeWidth(5);
        mGesturePaint.setColor(Color.BLACK);
    }

    public SignView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(event);
        }
        // 更新绘制
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        // 通过画布绘制多点形成的图形
        canvas.drawPath(mPath, mGesturePaint);
    }

    // 手指点下屏幕时调用
    private void touchDown(MotionEvent event) {

        // 重置绘制路线，即隐藏之前绘制的轨迹
        float x = event.getX();
        float y = event.getY();

        mX = x;
        mY = y;
        // mPath绘制的绘制起点
        mPath.moveTo(x, y);
    }

    // 手指在屏幕上滑动时调用
    private void touchMove(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();

        final float previousX = mX;
        final float previousY = mY;

        final float dx = Math.abs(x - previousX);
        final float dy = Math.abs(y - previousY);

        // 两点之间的距离大于等于3时，生成贝塞尔绘制曲线
        if (dx >= 3 || dy >= 3) {
            // 设置贝塞尔曲线的操作点为起点和终点的一半
            float cX = (x + previousX) / 2;
            float cY = (y + previousY) / 2;

            // 二次贝塞尔，实现平滑曲线；previousX, previousY为操作点，cX, cY为终点
            mPath.quadTo(previousX, previousY, cX, cY);

            // 第二次执行时，第一次结束调用的坐标值将作为第二次调用的初始坐标值
            mX = x;
            mY = y;
        }
    }

    public void clearSgin() {
        mPath.reset();
        invalidate();
    }

    /**
     * 将绘制的内容保存为png文件
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveImage() throws FileNotFoundException, IOException {
        saveImage(getContext().getCacheDir() + DEFAULT_IMAGE_PATH,
                DEFAULT_IMAGE_NAME);
    }

    /**
     * 文件格式为png
     * 
     * @param path 保存路径
     * @param fileName 文件名
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveImage(String path, String fileName)
            throws FileNotFoundException, IOException {
        setDrawingCacheEnabled(true);
        Bitmap bitmap = getDrawingCache();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        bitmap.compress(CompressFormat.PNG, 90, new FileOutputStream(path + "/"
                + fileName));
        setDrawingCacheEnabled(false);
    }

}
