package com.example.bill.utils;

import android.app.Instrumentation;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.example.bill.R;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Need root permission to use input keyevent
 *
 * Created by bill on 2017/10/12.
 */

public class TakeScreenShot extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);

        findViewById(R.id.screen_shot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String keyCommand = "input keyevent " + KeyEvent.KEYCODE_SYSRQ + "\n";
                        Runtime runtime = Runtime.getRuntime();
                        try {
                            Process process = runtime.exec("su");
                            DataOutputStream dos = new DataOutputStream(process.getOutputStream());
                            dos.writeBytes(keyCommand);
                            dos.flush();

                            System.out.println(process.waitFor());
                            System.out.println(process.exitValue());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }
}
