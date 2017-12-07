package com.example.bill.app.view.favor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.bill.R;

/**
 * Created by lber1 on 2017/11/28.
 */

public class FavorButtonV2 extends View {

    private boolean mChecked = true;

    private Bitmap mSelectedBitmap;
    private Bitmap mUnelectedBitmap;
    private Bitmap mShinningBitmap;
    private Matrix mShinningScaleMatrix = new Matrix();
    private Matrix mSelectScaleMatrix = new Matrix();

    private float mSelectWidth = 0;
    private float mSelectHeight = 0;

    private float mShiningWidth = 0;
    private float mShiningHeight = 0;

    private float mSelectMarginTop = 0;

    private boolean mScaleDownAnimEnd = true;
    private boolean mScaleUpAnimEnd = true;

    public FavorButtonV2(Context context) {
        this(context, null);
    }

    public FavorButtonV2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FavorButtonV2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setClickable(true);

        init();

    }

    private void init() {
        mSelectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        mUnelectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        mShinningBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);

        mSelectWidth = mSelectedBitmap.getWidth();
        mSelectHeight = mSelectedBitmap.getHeight();

        mShiningWidth = mShinningBitmap.getWidth();
        mShiningHeight = mShinningBitmap.getHeight();

        mSelectMarginTop = mShiningHeight / 3 * 2;

        resetSelectMatrix(mSelectScaleMatrix);
//        resetSelectMatrix(mSelectScaleUpMatrix);
    }

    private void resetSelectMatrix(Matrix matrix) {
        matrix.reset();
        matrix.postTranslate(0, mSelectMarginTop);
    }

    public void setCheck(boolean check) {
        mChecked = check;

        performAnimation();
    }

    private void performAnimation() {
        // anim icon
        if (mChecked) {
            ValueAnimator selectScaleDownAnim = ValueAnimator.ofFloat(1, 0.7f);
            selectScaleDownAnim.setInterpolator(new LinearInterpolator());
            selectScaleDownAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float factor = (float) animation.getAnimatedValue();
                    resetSelectMatrix(mSelectScaleMatrix);
                    mSelectScaleMatrix.postTranslate(mSelectWidth * (1 - factor) / 2,  mSelectHeight * (1 - factor) / 2);
                    mSelectScaleMatrix.postScale(factor, factor);
                    invalidate();
                }
            });

            selectScaleDownAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mScaleDownAnimEnd = true;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    mScaleDownAnimEnd = false;
                }
            });
            ValueAnimator selectScaleUpAnim = ValueAnimator.ofFloat(0.7f, 1);
            selectScaleUpAnim.setInterpolator(new LinearInterpolator());
            selectScaleUpAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float factor = (float) animation.getAnimatedValue();
                    resetSelectMatrix(mSelectScaleMatrix);
                    mSelectScaleMatrix.postTranslate(mSelectWidth * (1 - factor) / 2,  mSelectHeight * (1 - factor) / 2);
                    mSelectScaleMatrix.postScale(factor, factor);
                    mShinningScaleMatrix.reset();
                    mShinningScaleMatrix.postTranslate(mShiningWidth * (1 - factor) / 2, mShiningHeight * (1 - factor) / 2);
                    mShinningScaleMatrix.postScale(factor, factor);
                    invalidate();
                }
            });

            selectScaleUpAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mScaleUpAnimEnd = true;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    mScaleUpAnimEnd = false;
                }
            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(selectScaleDownAnim, selectScaleUpAnim);
            animatorSet.setDuration(150);
            animatorSet.start();
        } else {
            ValueAnimator selectScaleDownAnim = ValueAnimator.ofFloat(1, 0.7f);
            selectScaleDownAnim.setInterpolator(new LinearInterpolator());
            selectScaleDownAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float factor = (float) animation.getAnimatedValue();
                    resetSelectMatrix(mSelectScaleMatrix);
                    mSelectScaleMatrix.postTranslate(mSelectWidth * (1 - factor) / 2,  mSelectHeight * (1 - factor) / 2);
                    mSelectScaleMatrix.postScale(factor, factor);

                    mShinningScaleMatrix.reset();
                    mShinningScaleMatrix.postTranslate(mShiningWidth * (1 - factor) / 2, mShiningHeight * (1 - factor) / 2);
                    mShinningScaleMatrix.postScale(factor, factor);
                    invalidate();
                }
            });

            selectScaleDownAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mScaleDownAnimEnd = true;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    mScaleDownAnimEnd = false;
                }
            });
            ValueAnimator selectScaleUpAnim = ValueAnimator.ofFloat(0.7f, 1);
            selectScaleUpAnim.setInterpolator(new LinearInterpolator());
            selectScaleUpAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float factor = (float) animation.getAnimatedValue();
                    resetSelectMatrix(mSelectScaleMatrix);
                    mSelectScaleMatrix.postTranslate(mSelectWidth * (1 - factor) / 2,  mSelectHeight * (1 - factor) / 2);
                    mSelectScaleMatrix.postScale(factor, factor);
                    invalidate();
                }
            });

            selectScaleUpAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mScaleUpAnimEnd = true;
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    mScaleUpAnimEnd = false;
                }
            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(selectScaleDownAnim, selectScaleUpAnim);
            animatorSet.setDuration(150);
            animatorSet.start();
        }

        // anim number
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = mSelectedBitmap.getWidth();
        int height = mSelectedBitmap.getHeight() + mShinningBitmap.getHeight();

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw like icon
        if (mChecked) {
            if (!mScaleDownAnimEnd) {
                canvas.drawBitmap(mUnelectedBitmap, mSelectScaleMatrix, new Paint());
            }
            if (!mScaleUpAnimEnd || mScaleUpAnimEnd && mScaleDownAnimEnd) {
                canvas.drawBitmap(mSelectedBitmap, mSelectScaleMatrix, new Paint());
                canvas.drawBitmap(mShinningBitmap, mShinningScaleMatrix, new Paint());
            }
        } else {
            if (!mScaleUpAnimEnd || mScaleUpAnimEnd && mScaleDownAnimEnd) {
                canvas.drawBitmap(mUnelectedBitmap, mSelectScaleMatrix, new Paint());
            }
            if (!mScaleDownAnimEnd) {
                canvas.drawBitmap(mSelectedBitmap, mSelectScaleMatrix, new Paint());
                canvas.drawBitmap(mShinningBitmap, mShinningScaleMatrix, new Paint());
            }
        }

        // draw number
    }

    @Override
    public boolean performClick() {
        toggle();

        boolean handled = super.performClick();
        if (!handled) {
            playSoundEffect(SoundEffectConstants.CLICK);
        }
        return handled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private void toggle() {
        setCheck(!mChecked);
        invalidate();
    }
}
