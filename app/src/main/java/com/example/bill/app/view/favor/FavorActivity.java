package com.example.bill.app.view.favor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.bill.R;

/**
 * simulate jike app favor button
 *
 * Created by bill on 2017/10/16.
 */

public class FavorActivity extends AppCompatActivity {

    private FavorButton favorBtn1;
    private FavorButton favorBtn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);

        favorBtn1 = (FavorButton) findViewById(R.id.favor_1);
        favorBtn2 = (FavorButton) findViewById(R.id.favor_2);


        findViewById(R.id.up_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorBtn1.setNumber(favorBtn1.getNumber() + 1);
            }
        });

        findViewById(R.id.down_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorBtn1.setNumber(favorBtn1.getNumber() - 1);
            }
        });

        findViewById(R.id.up_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorBtn2.setNumber(favorBtn2.getNumber() + 1);
            }
        });

        findViewById(R.id.down_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorBtn2.setNumber(favorBtn2.getNumber() - 1);
            }
        });

        favorBtn1.setOnCheckedChangeListener(new OnFavorButtonCheckedListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                Toast.makeText(FavorActivity.this, "check " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
