package com.exmaple.bill.fragment.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.exmaple.bill.R;
import com.exmaple.bill.fragment.ViewPagerFragment;
import com.exmaple.bill.fragment.ViewPagerFragment.DragEffect;

/**
 * {@link ViewPagerFragment} demo
 * 
 * @author LvBeier
 * @version V1.0 2014-12-25
 */
public class ActivityRollingPager extends Activity {

	private ViewPagerFragment viewPageFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_rolling_pager);
		Button start = (Button) findViewById(R.id.start_rolling);
		Button stop = (Button) findViewById(R.id.stop_rolling);

		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					viewPageFragment.startRoll();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					viewPageFragment.stopRoll();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// 仅用来演示用，项目中请使用其他的构造方法
		viewPageFragment = new ViewPagerFragment();
		// 添加一个View的list到pager中
		// List<View> pagerList = new ArrayList<View>();
		// for (int i = 0; i < 10; i++) {
		// View v = inflater.inflate(R.layout.pager_item, null);
		// pagerList.add(v);
		// }
		// viewPageFragment = new ViewPagerFragment(pagerList);
		// 圆点的间距
		// viewPageFragment.setDotSpace(4);
		// 页面切换动画
		viewPageFragment.setEffect(DragEffect.FADE);
		// 圆点颜色透明度
		// viewPageFragment.setIndicatorColor(0x55ff2233, 0xff223344);
		// 轮播间隔
		// viewPageFragment.setRollTime(500);
		// 开启轮播
		viewPageFragment.setAutoRoll(true);

		getFragmentManager().beginTransaction()
				.replace(R.id.fragment_container, viewPageFragment).commit();
	}

}
