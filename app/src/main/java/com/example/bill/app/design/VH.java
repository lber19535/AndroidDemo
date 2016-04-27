package com.example.bill.app.design;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by bill_lv on 2016/4/13.
 */
class VH extends RecyclerView.ViewHolder {

    TextView tv;
    BottomSheetBehavior b;

    public VH(View itemView) {
        super(itemView);
//        this.b = behavior;
        tv = (TextView) itemView.findViewById(android.R.id.text1);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                b.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

}