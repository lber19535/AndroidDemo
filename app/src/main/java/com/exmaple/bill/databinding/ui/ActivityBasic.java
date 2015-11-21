package com.exmaple.bill.databinding.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.exmaple.bill.R;
import com.exmaple.bill.databinding.ActivityBasicBinding;
import com.exmaple.bill.databinding.model.User;
import com.exmaple.bill.databinding.utils.NameUtils;

import java.util.Random;

/**
 * Created by bill_lv on 2015/11/19.
 */
public class ActivityBasic extends AppCompatActivity {



    ActivityBasicBinding binding;
    User user = new User("Bill", "Lv");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding_basic);

        binding.setUser(user);
        user.setIsupper(true);
        binding.bindViewId.setText("bind view id");
        binding.setList(NameUtils.TEST_STR_LIST);
    }

    public void onListChange(View v){
        binding.setIndex(new Random().nextInt(NameUtils.TEST_STR_LIST.size()));
    }
}
