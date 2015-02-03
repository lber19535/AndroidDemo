package com.bill.demo.view.demo;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.bill.demo.R;
import com.bill.demo.view.fragment.ViewPagerFragment;
import com.bill.demo.view.fragment.ViewPagerFragment.DragEffect;

/**
 * {@link ViewPagerFragment} demo
 * 
 * @author LvBeier
 * @version V1.0 2014-12-25
 */
@SuppressLint("NewApi")
public class FragmentRollingPager extends Fragment {

	private ViewPagerFragment viewPageFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View vrootView = inflater
				.inflate(R.layout.fragment_rolling_pager, null);

		Button start = (Button) vrootView.findViewById(R.id.start_rolling);
		Button stop = (Button) vrootView.findViewById(R.id.stop_rolling);

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

		getChildFragmentManager().beginTransaction()
				.replace(R.id.fragment_container, viewPageFragment).commit();
		return vrootView;
	}

}
