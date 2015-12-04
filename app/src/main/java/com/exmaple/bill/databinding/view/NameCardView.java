package com.exmaple.bill.databinding.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exmaple.bill.App;
import com.exmaple.bill.R;


/**
 * Created by bill_lv on 2015/11/23.
 */
public class NameCardView extends LinearLayout {
    private int mAge;

    private TextView mFirstName;
    private TextView mLastName;

    public NameCardView(Context context) {
        this(context, null);
    }

    public NameCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NameCardView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    @BindingAdapter({"xxx:imageUrl"})
    public static void loadImage(NameCardView view, String url) {
        Toast.makeText(App.getAppContext(),"load img success " + url,Toast.LENGTH_SHORT).show();
    }

}
