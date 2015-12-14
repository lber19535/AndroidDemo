package com.example.bill.view.demo;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.bill.R;
import com.example.bill.view.SignView;

/**
 * {@link SignView} demo
 * 
 * @author Lv Beier
 *
 */
public class ActivitySign extends AppCompatActivity {

    private Button saveBtn;
    private Button clearBtn;
    private SignView mSignView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        saveBtn = (Button) findViewById(R.id.save);
        clearBtn = (Button) findViewById(R.id.clear);

        mSignView = (SignView) findViewById(R.id.sign);

        clearBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mSignView.clearSgin();
            }
        });

        saveBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    mSignView.saveImage();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

}
