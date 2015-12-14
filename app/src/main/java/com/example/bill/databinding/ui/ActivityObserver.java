package com.example.bill.databinding.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableArrayMap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.bill.R;
import com.example.bill.databinding.ActivityObserverBinding;
import com.example.bill.databinding.model.ObservableFieldUser;
import com.example.bill.databinding.model.ObservableUser;
import com.example.bill.databinding.utils.NameUtils;

import java.util.Random;

/**
 * Created by bill_lv on 2015/11/19.
 */
public class ActivityObserver extends AppCompatActivity {

    ActivityObserverBinding binding;
    ObservableUser obUser = new ObservableUser("fist", "last");
    ObservableFieldUser user = new ObservableFieldUser();
    ObservableArrayList<String> userList = new ObservableArrayList<>();
    ObservableArrayMap<String,String> userMap = new ObservableArrayMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding_observer);

        userList.add("first name");
        userList.add("last name");

        userMap.put("firstName","first");
        userMap.put("lastName","last");

        binding.setObUser(obUser);
        binding.setUser(user);
        binding.setUserList(userList);
        binding.setUserMap(userMap);
    }
    public void onChangeObUser(View v) {
        obUser.setFirstName(NameUtils.FIRST_NAMES[new Random().nextInt(NameUtils.FIRST_NAMES.length)]);
        obUser.setLastName(NameUtils.LAST_NAMES[new Random().nextInt(NameUtils.LAST_NAMES.length)]);
    }
    public void onChangeIsAudlt(View v) {
        if (obUser.isAdult()) {
            obUser.setAge(10);
        } else {
            obUser.setAge(19);
        }
    }

    public void onChangeObField(View v){
        int index = new Random().nextInt(NameUtils.FIRST_NAMES.length);
        user.firstName.set(NameUtils.FIRST_NAMES[index]);
    }

    public void onChangeUserList(View v){
        userList.clear();
        userList.add(NameUtils.getRandomFirstName());
        userList.add(NameUtils.getRandomLastName());
    }

    public void onChangeUserMap(View v){
        userMap.put("firstName", NameUtils.getRandomFirstName());
        userMap.put("lastName",NameUtils.getRandomLastName());
    }

}
