package com.exmaple.bill.databinding.ui;

import android.databinding.BindingConversion;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.exmaple.bill.R;
import com.exmaple.bill.databinding.ActivityDatabindingConversionBinding;

/**
 * Created by Bill Lv on 2015/11/21.
 */
public class ActivityConversion extends AppCompatActivity {

    ActivityDatabindingConversionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding_conversion);

    }

    public void onBackgroundColorChanged(View v) {
        if (binding.getError()) {
            binding.setError(false);
        } else {
            binding.setError(true);
        }
    }

    @BindingConversion
    public static ColorDrawable converColor(int color){
        System.out.println("xxxxxx" + color);
        return new ColorDrawable(color);
    }
}
