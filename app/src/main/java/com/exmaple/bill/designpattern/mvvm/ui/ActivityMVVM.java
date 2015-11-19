package com.exmaple.bill.designpattern.mvvm.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.exmaple.bill.R;
import com.exmaple.bill.databinding.ActivityMvvmBinding;
import com.exmaple.bill.designpattern.mvvm.model.ObservableUser;
import com.exmaple.bill.designpattern.mvvm.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by bill_lv on 2015/11/15.
 */
public class ActivityMVVM extends AppCompatActivity {

    private static final String[] FIRST_NAMES = {"Peter", "Bill", "Jessie", "Alex", "Tim"};
    private static final String[] LAST_NAMES = {"Gates", "Phillips", "Cook", "Jobs"};
    private static final List<String> TEST_STR_LIST = Arrays.asList(FIRST_NAMES);

    ObservableUser obUser = new ObservableUser("fist", "last");
    User user = new User("Bill", "Lv");
    ActivityMvvmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);

        binding.setUser(user);
        binding.bindViewId.setText("bind view id");
        binding.setObUser(obUser);
        binding.setList(TEST_STR_LIST);

    }

    public void onChangeObUser(View v) {
        obUser.setFirstName(FIRST_NAMES[new Random().nextInt(FIRST_NAMES.length)]);
        obUser.setLastName(LAST_NAMES[new Random().nextInt(LAST_NAMES.length)]);
    }

    public void onLog(View v) {
        Toast.makeText(this, "on long click", Toast.LENGTH_SHORT).show();
    }

    public void onChangeIsAudlt(View v) {
        if (obUser.isAdult()) {
            obUser.setAge(10);
        } else {
            obUser.setAge(19);
        }
    }

    public void onListChange(View v){
        binding.setIndex(new Random().nextInt(TEST_STR_LIST.size()));
    }

}
