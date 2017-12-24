package com.example.bill.app.view.favor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.bill.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lber1 on 2017/11/28.
 */

public class FavorButtonV2 extends View {

    private static final String TAG = "FavorButtonV2";
    private static String DEFAULT_NUMBER_STR = "0";
    private static final int ANIMATION_DURATION = 150;

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

    private Paint mIconPaint = new Paint();

    private int mNumber = 0;
    private int mOldNumber = 0;

    private float mTextHeight = 0;

    private boolean mNeedNumberAnim = false;
    private int mDifferentIndex = -1;
    private float mDiffOffsetUp = 0;
    private float mDiffOffsetDown = 0;
    private Paint mNewNumberPaint;
    private Paint mOldNumberPaint;
    private float mNumberSplitSize;
    private String mNumberStr = DEFAULT_NUMBER_STR;
    private Paint mNumberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mNumberColor;
    private Rect mTextBounds = new Rect();

    @ScaleType
    private int mScaleType = ScaleType.SCALE_DOWN;

    private ValueAnimator mSelectScaleDownAnim = ValueAnimator.ofFloat(1, 0.7f);
    private ValueAnimator mSelectScaleUpAnim = ValueAnimator.ofFloat(0.7f, 1);
    private LinearInterpolator mLinearInterpolator = new LinearInterpolator();

    private ValueAnimator.AnimatorUpdateListener mSelectScaleAnimUpdateListener;
    private ValueAnimator.AnimatorUpdateListener mShinningScaleAnimUpdateListener;
    private ValueAnimator.AnimatorUpdateListener mInvalidateUpdateListener;
    private AnimatorListenerAdapter mScaleUpListener;
    private AnimatorListenerAdapter mScaleDownListener;
    private AnimatorListenerAdapter mNumberAnimListener;

    public FavorButtonV2(Context context) {
        this(context, null);
    }

    public FavorButtonV2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FavorButtonV2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setClickable(true);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FavorButton, 0, 0);
        mNumber = typedArray.getInteger(R.styleable.FavorButton_number, 0);
        mNumberColor = typedArray.getColor(R.styleable.FavorButton_numberColor, Color.BLACK);

        init();

    }

    private void init() {
        // icon res init
        mSelectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        mUnelectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        mShinningBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);

        mSelectWidth = mSelectedBitmap.getWidth();
        mSelectHeight = mSelectedBitmap.getHeight();

        mShiningWidth = mShinningBitmap.getWidth();
        mShiningHeight = mShinningBitmap.getHeight();

        mSelectMarginTop = mShiningHeight / 3 * 2;

        resetSelectMatrix(mSelectScaleMatrix);

        // init icon animation resource
        mSelectScaleUpAnim.setInterpolator(mLinearInterpolator);
        mSelectScaleDownAnim.setInterpolator(mLinearInterpolator);

        mSelectScaleAnimUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                resetSelectMatrix(mSelectScaleMatrix);
                mSelectScaleMatrix.postTranslate(mSelectWidth * (1 - factor) / 2, mSelectHeight * (1 - factor) / 2);
                mSelectScaleMatrix.postScale(factor, factor);
            }
        };

        mShinningScaleAnimUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                mShinningScaleMatrix.reset();
                mShinningScaleMatrix.postTranslate(mShiningWidth * (1 - factor) / 2, mShiningHeight * (1 - factor) / 2);
                mShinningScaleMatrix.postScale(factor, factor);
            }
        };

        mInvalidateUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        };

        mScaleUpListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mScaleUpAnimEnd = true;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mScaleUpAnimEnd = false;
            }
        };

        mScaleDownListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mScaleDownAnimEnd = true;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mScaleDownAnimEnd = false;
            }
        };

        // number res init
        mNumberPaint.setColor(mNumberColor); // default color use black
        mNumberPaint.setTextSize(getResources().getDimension(R.dimen.jk_favor_number_size));
        mNumberPaint.setStyle(Paint.Style.STROKE);
        mNewNumberPaint = new Paint(mNumberPaint);
        mOldNumberPaint = new Paint(mNumberPaint);
        mNumberStr = String.valueOf(mNumber);
        mTextHeight = mNumberPaint.measureText(mNumberStr);

        mNumberSplitSize = getResources().getDimension(R.dimen.jk_favor_number_split);

        // init number animation resource
//        AnimatorSet animatorSet = new AnimatorSet();
//
//
//        animatorSet.setDuration(ANIMATION_DURATION);


//        mNumberAnimListener = new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                mNeedNumberAnim = true;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mNeedNumberAnim = false;
//            }
//        };

    }

    private void resetSelectMatrix(Matrix matrix) {
        matrix.reset();
        matrix.postTranslate(0, mSelectMarginTop);
    }

    public void setCheck(boolean check) {
        mChecked = check;

        if (mChecked) {
            setNumberWithAnim(getNumber() + 1);
        } else {
            setNumberWithAnim(getNumber() - 1);
        }

        performAnimation();
    }

    private void performAnimation() {
        // anim icon
        mSelectScaleUpAnim.addUpdateListener(mSelectScaleAnimUpdateListener);
        mSelectScaleUpAnim.addUpdateListener(mInvalidateUpdateListener);
        mSelectScaleUpAnim.addListener(mScaleUpListener);

        mSelectScaleDownAnim.addUpdateListener(mSelectScaleAnimUpdateListener);
        mSelectScaleDownAnim.addUpdateListener(mInvalidateUpdateListener);
        mSelectScaleDownAnim.addListener(mScaleDownListener);

        if (mChecked) {
            mSelectScaleUpAnim.addUpdateListener(mShinningScaleAnimUpdateListener);
        } else {
            mSelectScaleDownAnim.addUpdateListener(mShinningScaleAnimUpdateListener);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(mSelectScaleDownAnim, mSelectScaleUpAnim);
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.start();

        // anim number


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
                mNeedNumberAnim = false;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mNeedNumberAnim = false;
                invalidate();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.setDuration(ANIMATION_DURATION);

        animatorSet.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int numberStrWidth = (int) mNumberPaint.measureText(mNumberStr);

        int width = mSelectedBitmap.getWidth() + numberStrWidth;
        int height = mSelectedBitmap.getHeight() + mShinningBitmap.getHeight();

        setMeasuredDimension(width, height);
    }

    public void setNumberWithAnim(int number) {
        mNeedNumberAnim = true;
        setNumber(number);
        mDifferentIndex = getDifferenceIndex();
        animNumberChange();
    }


    public void setNumber(int number) {
        Log.d(TAG, "mNumber " + number);
        if (number < 0) {
            Log.e(TAG, "mNumber is negative");
            return;
        } else {
//            if (mFavorNumberChangedListener != null) {
//                mFavorNumberChangedListener.onFavorNumberChanged(this.mNumber, number);
//            }
            this.mOldNumber = this.mNumber;
            this.mNumber = number;
            this.mNumberStr = String.valueOf(number);

            if (!mNeedNumberAnim)
                invalidate();
        }

    }

    public int getNumber() {
        return mNumber;
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
    protected void onDraw(Canvas canvas) {
        // draw like icon
        if (mChecked && !mScaleDownAnimEnd || !mChecked && mScaleDownAnimEnd) {
            canvas.drawBitmap(mUnelectedBitmap, mSelectScaleMatrix, mIconPaint);
        } else if (mChecked && mScaleDownAnimEnd || !mChecked && !mScaleDownAnimEnd) {
            canvas.drawBitmap(mSelectedBitmap, mSelectScaleMatrix, mIconPaint);
            canvas.drawBitmap(mShinningBitmap, mShinningScaleMatrix, mIconPaint);
        }

        // draw number
        if (mNumberStr == null) {
            return;
        }

        mTextBounds.setEmpty();
        mNumberPaint.getTextBounds(mNumberStr, 0, mNumberStr.length(), mTextBounds);

        // text align bottom 2/5
        int d = (getHeight() - mTextBounds.height()) / 5 * 3;

        // text base location
        float basePositionY = mTextBounds.height() + d;
        float basePositionX = mSelectedBitmap.getWidth();

        if (mNeedNumberAnim && mDifferentIndex != -1) {

            canvas.drawText(mNumberStr, 0, mDifferentIndex, basePositionX, basePositionY, mNumberPaint);
            float sameTextWith = mNumberPaint.measureText(mNumberStr, 0, mDifferentIndex);
            String oldNumberStr = String.valueOf(mOldNumber);

            float oldY = basePositionY;
            float newY = basePositionY;
            if (mOldNumber < mNumber) {
                oldY += mDiffOffsetUp;
                newY += mTextHeight + mDiffOffsetUp + mNumberSplitSize;
            } else if (mOldNumber > mNumber) {
                oldY += mDiffOffsetDown;
                newY += mDiffOffsetDown - mTextHeight - mNumberSplitSize;
            }

            canvas.drawText(oldNumberStr, mDifferentIndex, oldNumberStr.length(), sameTextWith + basePositionX, oldY, mOldNumberPaint);
            canvas.drawText(mNumberStr, mDifferentIndex, mNumberStr.length(), sameTextWith + basePositionX, newY, mNewNumberPaint);


        } else {
            canvas.drawText(mNumberStr, basePositionX, basePositionY, mNumberPaint);
        }
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

    @IntDef(flag = true, value = {ScaleType.SCALE_UP, ScaleType.SCALE_DOWN, ScaleType.SCALE_NONE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ScaleType {
        int SCALE_UP = 0;
        int SCALE_DOWN = 1;
        int SCALE_NONE = 3;
    }
}
