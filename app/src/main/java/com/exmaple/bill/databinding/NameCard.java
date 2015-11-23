package com.exmaple.bill.databinding;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exmaple.bill.R;


/**
 * Created by bill_lv on 2015/11/23.
 */
public class NameCard extends LinearLayout {
    private int mAge;

    private TextView mFirstName;
    private TextView mLastName;

    public NameCard(Context context) {
        this(context, null);
    }

    public NameCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NameCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_name_card, this);
        mFirstName = (TextView) findViewById(R.id.first_name);
        mLastName = (TextView) findViewById(R.id.last_name);
    }

    public void setFirstName(@NonNull final String firstName) {
        mFirstName.setText(firstName);
    }

    public void setLastName(@NonNull final String lastName) {
        mLastName.setText(lastName);
    }

}
