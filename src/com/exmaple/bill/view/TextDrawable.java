package com.exmaple.bill.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * 可设置背景和半透明度的显示text的drawable。
 * 用于通过{@code DrawableMarginSpan} 嵌入到 {@code TextView}中
 * 
 * @author Bill Lv
 *
 */
public class TextDrawable extends Drawable {

    private String text;
    private Paint textPaint;
    private Paint backgroundPaint;
    private int textColor = Color.BLACK;
    private int backgroundColor = Color.GREEN;
    private float textSize = 22;
    private float textPaintWidth = 2;

    private float paddingTop = 5;
    private float paddingLeft = 5;
    private float paddingBottom = 5;
    private float paddingRight = 5;

    private Rect textBounds;
    private RectF backgroundRect;

    // 圆角矩形角度，为0时是矩形
    private float rx = 5;
    private float ry = 5;

    public TextDrawable() {
        this.textPaint = new Paint();
        this.backgroundPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setStrokeWidth(textPaintWidth);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.LEFT);

        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.FILL);

        textBounds = new Rect();
        backgroundRect = new RectF();
    }

    public TextDrawable(String text) {
        this();
        this.text = text;
    }

    public TextDrawable(String text, int textColor, int backgroundColor) {
        this(text);
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void draw(Canvas canvas) {
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        /*
         * 由于baseline的原因，text的start是在左下角，所以就造成了当在顶部的时候top可能为负
         * 所以下面会对top和bottom做处理。
         */
        // 交换top和bottom
        textBounds.top = textBounds.top ^ textBounds.bottom;
        textBounds.bottom = textBounds.top ^ textBounds.bottom;
        textBounds.top = textBounds.top ^ textBounds.bottom;
        // 将bounds整体下移一个本身的高度
        textBounds.bottom = textBounds.top - textBounds.bottom;

        // 加入padding
        backgroundRect.set(textBounds);
        backgroundRect.bottom += paddingBottom + paddingTop;
        backgroundRect.right += paddingLeft + paddingRight;

        textBounds.top += paddingTop;
        textBounds.bottom += paddingTop;
        textBounds.left += paddingLeft;
        textBounds.right += paddingLeft;

        // 背景
        canvas.drawRoundRect(backgroundRect, rx, ry, backgroundPaint);
        // 文字
        canvas.drawText(text, textBounds.left, textBounds.bottom, textPaint);
    }

    /**
     * 设置内边距
     * 
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setPadding(int left, int top, int right, int bottom) {
        this.paddingLeft = left;
        this.paddingTop = top;
        this.paddingRight = right;
        this.paddingBottom = bottom;
    }

    /**
     * 设置内边距
     * 
     * @param padding
     */
    public void setPadding(int padding) {
        setPadding(padding, padding, padding, padding);
    }

    /**
     * 设置背景色的透明度
     * 
     * @param alpha 0~0xff
     */
    public void setBackgroudAlpha(int alpha) {
        backgroundColor = backgroundColor << 8 >> 8 | alpha << 24;
        backgroundPaint.setColor(backgroundColor);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        textPaint.setColor(textColor);
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        backgroundPaint.setColor(backgroundColor);
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        textPaint.setTextSize(textSize);
    }

    public float getTextPaintWidth() {
        return textPaintWidth;
    }

    /**
     * text画笔粗细
     * 
     * @param textPaintWidth
     */
    public void setTextPaintWidth(float textPaintWidth) {
        this.textPaintWidth = textPaintWidth;
        textPaint.setStrokeWidth(textPaintWidth);
    }

    @Override
    public void setAlpha(int alpha) {
        textPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        textPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

}
