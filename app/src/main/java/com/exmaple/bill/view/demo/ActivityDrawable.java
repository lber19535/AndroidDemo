package com.exmaple.bill.view.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.DrawableMarginSpan;
import android.view.View;
import android.widget.TextView;

import com.exmaple.bill.R;
import com.exmaple.bill.view.TextDrawable;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class ActivityDrawable extends AppCompatActivity {

    private View drawView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);

        drawView = findViewById(R.id.draw);
        mTextView = (TextView) findViewById(R.id.txet);

        TextDrawable textDrawable = new TextDrawable("hahaha");
        textDrawable.setTextSize(getResources().getDimension(R.dimen.font_22));
        textDrawable.setPadding(5);

        DrawableMarginSpan span = new DrawableMarginSpan(textDrawable);
        SpannableStringBuilder ssb = SpannableStringBuilder.valueOf("               asdasda");
        ssb.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        mTextView.setText(ssb);

//        if (Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
//            drawView.setBackground(textDrawable);
//        } else {
//            drawView.setBackgroundDrawable(textDrawable);
//        }
    }

}
