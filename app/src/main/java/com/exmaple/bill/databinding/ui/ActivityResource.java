package com.exmaple.bill.databinding.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.exmaple.bill.R;
import com.exmaple.bill.databinding.ActivityResourceBinding;
import com.exmaple.bill.databinding.utils.NameUtils;

import java.util.Random;

/**
 * Created by bill_lv on 2015/11/19.
 */
public class ActivityResource extends AppCompatActivity{

    ActivityResourceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding_res);
        binding.setBananaCount(0);
    }

    public void onChangeTextSize(View v){
        if (binding.getLarge()){
            binding.setLarge(false);
        }else {
            binding.setLarge(true);
        }
    }

    public void onChangeNameCount(View v){
        int fisrtIndex = new Random().nextInt(NameUtils.FIRST_NAMES.length);
        int lastIndex = new Random().nextInt(NameUtils.LAST_NAMES.length);
        binding.setFirstName(NameUtils.FIRST_NAMES[fisrtIndex]);
        binding.setLastName(NameUtils.LAST_NAMES[lastIndex]);
    }
}
