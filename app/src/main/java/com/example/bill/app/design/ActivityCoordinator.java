package com.example.bill.app.design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SimpleAdapter;

import com.example.bill.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bill_lv on 2016/4/13.
 */
public class ActivityCoordinator extends AppCompatActivity {

    @BindView(R.id.list)
    RecyclerView listView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
        ButterKnife.bind(this);

        listView.setAdapter(new ReAdapter(createData()));
        listView.setLayoutManager(new LinearLayoutManager(this));

    }

    private SimpleAdapter createAdapter() {
        List<Map<String, String>> data = createData();

        String[] from = {"key"};
        SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, from, new int[]{android.R.id.text1});
        return adapter;
    }

    private List<Map<String, String>> createData() {
        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            Map<String, String> item = new HashMap<>();
            item.put("key", i + "value");
            data.add(item);
        }
        return data;
    }
}
