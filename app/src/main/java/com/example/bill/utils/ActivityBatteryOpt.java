package com.example.bill.utils;

import android.app.usage.UsageStatsManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.bill.R;
import com.orhanobut.logger.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Change app standby state, this function need system sign and api level 23 or higher
 * <p/>
 * Created by bill_lv on 2015/12/10.
 */
public class ActivityBatteryOpt extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_opt);
    }

    /**
     * The package name is this demo's package name, so the function is invalid when you click this button.
     *
     * This code is a demo, if you have system sign or root permission, you can use this code to change app standby state.
     *
     * @param v
     */
    public void setAppIdle(View v) {
        changeInactiveState("com.example.bill",true);
    }

    public void wakeApp(View v) {
        changeInactiveState("com.example.bill",false);
    }

    private void changeInactiveState(String pkgName, boolean state) {
        UsageStatsManager usm = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Logger.d("change before idle= " + usm.isAppInactive(pkgName));
            try {
                Method m = usm.getClass().getMethod("setAppInactive", String.class, boolean.class);
                m.invoke(usm, pkgName, state);
                Logger.d("idle= " + usm.isAppInactive(pkgName));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
