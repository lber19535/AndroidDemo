package com.example.bill.app.design;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.bill.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bill_lv on 2016/4/13.
 */
public class ActivityBottomSheet extends AppCompatActivity {

    @Bind(R.id.show_bottom_sheet)
    Button showSheetBtn;
    @Bind(R.id.show_bottom_view)
    Button showSheetViewBtn;
    @Bind(R.id.list)
    RecyclerView listView;

//    @Bind(R.id.list_main)
//    ListView listViewMain;

    BottomSheetDialog bottomSheetDialog;
    BottomSheetBehavior behavior;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        ButterKnife.bind(this);
        behavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                System.out.println(newState);
                System.out.println("on state change");
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                System.out.println("on slide");
            }
        });


        showSheetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });

        showSheetViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));
        ReAdapter reAdapter = new ReAdapter(createData());
        listView.setAdapter(reAdapter);


//        listViewMain.setAdapter(createAdapter());

    }

    public void showBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(this);
        View dialogContent = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_list, null);
        ListViewCompat listViewCompat = (ListViewCompat) dialogContent.findViewById(R.id.list);
        listViewCompat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bottomSheetDialog.dismiss();
            }
        });

        listViewCompat.setAdapter(createAdapter());
        bottomSheetDialog.setContentView(dialogContent);
        bottomSheetDialog.show();
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomSheetDialog = null;
            }
        });

    }

    private SimpleAdapter createAdapter() {
        List<Map<String, String>> data = createData();

        String[] from = {"key"};
        SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, from, new int[]{android.R.id.text1});
        return adapter;
    }

    private List<Map<String, String>> createData() {
        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Map<String, String> item = new HashMap<>();
            item.put("key", i + "value");
            data.add(item);
        }
        return data;
    }

}
