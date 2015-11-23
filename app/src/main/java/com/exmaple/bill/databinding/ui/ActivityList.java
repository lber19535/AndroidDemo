package com.exmaple.bill.databinding.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.exmaple.bill.R;
import com.exmaple.bill.databinding.model.NameRecyclerAdapter;
import com.exmaple.bill.databinding.model.User;
import com.exmaple.bill.databinding.utils.NameUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bill_lv on 2015/11/23.
 */
public class ActivityList extends AppCompatActivity {

    @Bind(R.id.list)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_databinding_list);

        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User(NameUtils.getRandomFirstName(),NameUtils.getRandomLastName());
            userList.add(user);
        }

        ButterKnife.bind(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NameRecyclerAdapter(userList));

    }

}
