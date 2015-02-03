package com.bill.demo;

import com.bill.demo.view.demo.FragmentRollingPager;

import android.app.Activity;
import android.os.Bundle;

public class ActivityContent extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);

		getFragmentManager().beginTransaction().replace(
				R.id.fragment_container, new FragmentRollingPager()).commit();

	}

}
