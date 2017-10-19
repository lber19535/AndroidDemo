package com.example.bill.app.view.favor;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.bill.R;

/**
 * Created by bill on 2017/10/16.
 */

public class FavorNumberView extends View {
    private static final String TAG = "FavorTextView";

    private static final int ANIMATION_DURATION = 150;


    private int mNumber = 0;
    private int mOldNumber = 0;

    private float mTextHeight = 0;

    private boolean mNeedAnim = false;
    private int mDifferentIndex = -1;
    private float mDiffOffsetUp = 0;
    private float mDiffOffsetDown = 0;
    private Paint mNewNumberPaint;
    private Paint mOldNumberPaint;
    private float mNumberSplitSize;

    private String mNumberStr = "0";
    private Paint mNumberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private OnFavorNumberChangedListener mFavorNumberChangedListener;

    public FavorNumberView(Context context) {
        super(context);
    }

    public FavorNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mNumberPaint.setColor(Color.BLACK); // default color use black
        mNumberPaint.setTextSize(context.getResources().getDimension(R.dimen.jk_favor_number_size));
        mNumberPaint.setStyle(Paint.Style.STROKE);
        mNewNumberPaint = new Paint(mNumberPaint);
        mOldNumberPaint = new Paint(mNumberPaint);
        mTextHeight = mNumberPaint.measureText("0");

        mNumberSplitSize = context.getResources().getDimension(R.dimen.jk_favor_number_split);
    }

    public FavorNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setNumberWithAnim(int number) {
        mNeedAnim = true;
        setNumber(number);
        mDifferentIndex = getDifferenceIndex();
        animNumberChange();
    }

    public void setNumberColor(int color) {
        mNewNumberPaint.setColor(color);
        mOldNumberPaint.setColor(color);
        mNumberPaint.setColor(color);
        invalidate();
    }

    private void animNumberChange() {
        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator newNumberAlphaAnim = ValueAnimator.ofInt(0, 255);
        newNumberAlphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mNewNumberPaint.setAlpha((Integer) animation.getAnimatedValue());
            }
        });

        ValueAnimator oldNumberAlphaAnim = ValueAnimator.ofInt(255, 0);
        oldNumberAlphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOldNumberPaint.setAlpha((Integer) animation.getAnimatedValue());
            }
        });

        if (mOldNumber < mNumber) {
            ValueAnimator offsetInAnim = ValueAnimator.ofFloat(0, -mTextHeight);
            offsetInAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mDiffOffsetUp = (float) animation.getAnimatedValue();
                    requestLayout();
                    invalidate();
                }
            });

            animatorSet.playTogether(newNumberAlphaAnim, oldNumberAlphaAnim, offsetInAnim);
        } else if (mOldNumber > mNumber) {
            ValueAnimator offsetOutAnim = ValueAnimator.ofFloat(0, mTextHeight);
            offsetOutAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mDiffOffsetDown = (float) animation.getAnimatedValue();

                    invalidate();
                }
            });
            offsetOutAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    requestLayout();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animatorSet.playTogether(newNumberAlphaAnim, oldNumberAlphaAnim, offsetOutAnim);
        }


        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mNeedAnim = false;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mNeedAnim = false;
                invalidate();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.setDuration(ANIMATION_DURATION);

        animatorSet.start();
    }

    public void setNumber(int number) {
        Log.d(TAG, "mNumber " + number);
        if (number < 0) {
            Log.e(TAG, "mNumber is negative");
            return;
        } else {
            if (mFavorNumberChangedListener != null) {
                mFavorNumberChangedListener.onFavorNumberChanged(this.mNumber, number);
            }
            this.mOldNumber = this.mNumber;
            this.mNumber = number;
            this.mNumberStr = String.valueOf(number);

            if (!mNeedAnim)
                invalidate();
        }

    }

    public int getNumber() {
        return mNumber;
    }

    public void setFavorNumberChangedListener(OnFavorNumberChangedListener favorNumberChangedListener) {
        this.mFavorNumberChangedListener = favorNumberChangedListener;
    }

    private int getDifferenceIndex() {
        String oldNumberStr = String.valueOf(mOldNumber);
        String numberStr = String.valueOf(mNumber);

        char[] oldChars = oldNumberStr.toCharArray();
        char[] chars = numberStr.toCharArray();
        int len = Math.min(oldChars.length, chars.length);

        for (int i = 0; i < len; i++) {
            if (chars[i] != oldChars[i]) {
                return i;
            }
        }

        return -1;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (int) mNumberPaint.measureText(mNumberStr);

        setMeasuredDimension(width, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mNumberStr == null) {
            return;
        }
        Rect textBounds = new Rect();
        mNumberPaint.getTextBounds(mNumberStr, 0, mNumberStr.length(), textBounds);

        // text align bottom 1/3
        int d = (getHeight() - textBounds.height()) / 3 * 2;

        // text base location
        float basePosition = textBounds.height() + d;

        if (mNeedAnim && mDifferentIndex != -1) {

            canvas.drawText(mNumberStr, 0, mDifferentIndex, 0, basePosition, mNumberPaint);
            float sameTextWith = mNumberPaint.measureText(mNumberStr, 0, mDifferentIndex);
            String oldNumberStr = String.valueOf(mOldNumber);

            float oldY = basePosition;
            float newY = basePosition;
            if (mOldNumber < mNumber) {
                oldY += mDiffOffsetUp;
                newY += mTextHeight + mDiffOffsetUp + mNumberSplitSize;
            } else if (mOldNumber > mNumber) {
                oldY += mDiffOffsetDown;
                newY += mDiffOffsetDown - mTextHeight - mNumberSplitSize;
            }

            canvas.drawText(oldNumberStr, mDifferentIndex, oldNumberStr.length(), sameTextWith, oldY, mOldNumberPaint);
            canvas.drawText(mNumberStr, mDifferentIndex, mNumberStr.length(), sameTextWith, newY, mNewNumberPaint);


        } else {
            canvas.drawText(mNumberStr, 0, basePosition, mNumberPaint);
        }
    }
}
