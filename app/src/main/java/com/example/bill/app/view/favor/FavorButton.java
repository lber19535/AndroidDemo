package com.example.bill.app.view.favor;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.bill.R;

/**
 * Created by bill on 2017/10/16.
 */

public class FavorButton extends LinearLayout {

    private static final int SELECT_ANIMATION_DURATION = 150;

    private int mNumber;
    private boolean mChecked = false;
    private OnFavorButtonCheckedListener mOnCheckedChangeListener;
    private FavorNumberView mFavorTv;
    private ImageView mSelectIv;
    private ImageView mUnselectedIv;
    private ImageView mShiningIv;
    private int mNumberColor;

    public FavorButton(Context context) {
        super(context);
    }

    public FavorButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FavorButton, 0, 0);

        try {
            mNumber = typedArray.getInteger(R.styleable.FavorButton_number, 0);
            mNumberColor = typedArray.getColor(R.styleable.FavorButton_numberColor, Color.BLACK);
        } finally {
            typedArray.recycle();
        }

        init();
    }

    public FavorButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        Context context = getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.layout_favor, null);
        setClickable(true);

        setOrientation(LinearLayout.HORIZONTAL);

        mFavorTv = view.findViewById(R.id.favor_number);
        mFavorTv.setNumber(mNumber);
        mFavorTv.setNumberColor(mNumberColor);

        mSelectIv = view.findViewById(R.id.select);
        mUnselectedIv = view.findViewById(R.id.unselected);
        mShiningIv = view.findViewById(R.id.shining);

        addView(view);
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

    public void setNumber(int number) {
        mFavorTv.setNumberWithAnim(number);
    }

    public int getNumber() {
        return mFavorTv.getNumber();
    }

    public void toggle() {
        setChecked(!mChecked);
    }

    public void setChecked(boolean checked) {
        System.out.println(checked);
        if (mChecked != checked) {
            mChecked = checked;

            if (checked) {
                mFavorTv.setNumberWithAnim(mFavorTv.getNumber() + 1);
            } else {
                mFavorTv.setNumberWithAnim(mFavorTv.getNumber() - 1);
            }
        }

        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(checked);
        }

        performAnimation();
    }

    private void performAnimation() {

        if (mChecked) {
            AnimatorSet showSelectAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_show);
            showSelectAnim.setTarget(mSelectIv);

            AnimatorSet showShiningAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_show);
            showShiningAnim.setTarget(mShiningIv);

            AnimatorSet hideUnselectedAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_hide);
            hideUnselectedAnim.setTarget(mUnselectedIv);
            hideUnselectedAnim.setDuration(SELECT_ANIMATION_DURATION);

            final AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(showSelectAnim, showShiningAnim);
            animatorSet.setDuration(SELECT_ANIMATION_DURATION);
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mSelectIv.setVisibility(VISIBLE);
                    mShiningIv.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            hideUnselectedAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mUnselectedIv.setVisibility(INVISIBLE);
                    animatorSet.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            hideUnselectedAnim.start();


        } else {
            AnimatorSet hideSelectAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_hide);
            hideSelectAnim.setTarget(mSelectIv);

            AnimatorSet hideShiningAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_hide);
            hideShiningAnim.setTarget(mShiningIv);

            final AnimatorSet showUnselectedAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.anim_show);
            showUnselectedAnim.setTarget(mUnselectedIv);
            showUnselectedAnim.setDuration(SELECT_ANIMATION_DURATION);
            showUnselectedAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mUnselectedIv.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(hideSelectAnim, hideShiningAnim);
            animatorSet.setDuration(SELECT_ANIMATION_DURATION);
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mSelectIv.setVisibility(INVISIBLE);
                    mShiningIv.setVisibility(INVISIBLE);
                    showUnselectedAnim.start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animatorSet.start();
        }
    }

    public void setOnCheckedChangeListener(OnFavorButtonCheckedListener checkedChangeListener) {
        this.mOnCheckedChangeListener = checkedChangeListener;
    }
}
