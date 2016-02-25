package com.example.bill;

import android.content.Intent;
import android.os.Build;

import com.example.bill.third.robotest.LoginActivity;
import com.example.bill.third.robotest.RoboTestActivity;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

/**
 * Robolectric test demo.
 *
 * In this demo, the version of Robolectric is 3.0, but in this version, they were not  support android 22 and higher.
 *
 * Developer says they support on version 3.1 in the issues of project, but I test the version 3.1-snapshot don't support perfect yet.
 *
 * Created by bill_lv on 2016/2/25.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = com.example.bill.BuildConfig.class, manifest = Config.NONE, sdk = Build.VERSION_CODES.LOLLIPOP)
public class RobolectricTest {

    @Test
    public void loginActivityTest() {
        RoboTestActivity roboTestActivity = Robolectric.setupActivity(RoboTestActivity.class);
        roboTestActivity.findViewById(R.id.login).performClick();

        Intent intent = new Intent(roboTestActivity, LoginActivity.class);

        Assert.assertThat("xxxxxaaaaaa", Shadows.shadowOf(roboTestActivity).getNextStartedActivity(), Is.is(intent));
    }
}