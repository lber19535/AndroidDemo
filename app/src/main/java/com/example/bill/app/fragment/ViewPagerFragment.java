package com.example.bill.app.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.bill.R;


/**
 * Fragment include {@link ViewPager}, build-in indicator, drag animation and
 * auto rolling.
 * 
 * @author Lv Beier
 * @version V1.0 2015-1-15
 */
public class ViewPagerFragment extends Fragment {

	public enum DragEffect {
		NONE, ZOOM, DEPTH, FADE
	};

	private static final String TAG = "ViewPagerFragment";

	private static final int DEFAULT_PAGER_COUNT = 5;

	private boolean DEBUG = true;

	private ViewPager viewPager;
	private List<View> pagerList;
	private DragEffect effect;
	private LinearLayout hintIndicatorLayout;
	private LinearLayout indicatorLayout;
	private LayoutParams params;
	// if this value is -1, program would use default color
	private int indicateColor = -1;
	private int hintColor = -1;
	private boolean haveIndicator;
	private boolean autoRoll;
	private int milliseconds = 2 * 1000;// default roll speed
	private int leftMargin;

	private Timer timer;
	private PagerAutoRollTask autoRollTask;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int index = msg.what;
			viewPager.setCurrentItem(index, true);
		}
	};

	/**
	 * Custom whether auto rolling
	 * 
	 * @param pagerList
	 * @param effect
	 */
	public ViewPagerFragment(List<View> pagerList, DragEffect effect,
			boolean haveIndicator, boolean autoRoll) {
		this.pagerList = pagerList;
		this.effect = effect;
		this.haveIndicator = haveIndicator;
		this.autoRoll = autoRoll;
	}

	/**
	 * Custom whether use indicator
	 * 
	 * @param pagerList
	 * @param effect
	 */
	public ViewPagerFragment(List<View> pagerList, DragEffect effect,
			boolean haveIndicator) {
		this(pagerList, effect, true, false);
	}

	/**
	 * Put custom view in this fragment and set some transform animation. With
	 * indicator.
	 * 
	 * @param pagerList
	 *            views list for every item
	 * @param effect
	 *            use {@link DragEffect}, not be null, if you don't need
	 *            transformer
	 */
	public ViewPagerFragment(List<View> pagerList, DragEffect effect) {
		this(pagerList, effect, true);
	}

	/**
	 * Use default transform animation.
	 * 
	 * Inflate some pager view items by {@link LayoutInflater}
	 * 
	 * @param pagerList
	 *            views list for every item
	 */
	public ViewPagerFragment(List<View> pagerList) {
		this(pagerList, DragEffect.NONE);
	}

	/**
	 * Fragment demo used.
	 */
	@Deprecated
	public ViewPagerFragment() {
		this(null, DragEffect.NONE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view_pager, null);
		viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
		hintIndicatorLayout = (LinearLayout) rootView
				.findViewById(R.id.indicator_hint);
		indicatorLayout = (LinearLayout) rootView.findViewById(R.id.indicator);

		// test data
		initPagerItem();

		// init transformer animation
		initPagerTransformer();

		// init default dot size
		int size = getResources().getDimensionPixelSize(
				R.dimen.pager_indicator_size);

		AutoRollPagerAdapter adapter = new AutoRollPagerAdapter(pagerList);

		viewPager.setAdapter(adapter);

		params = new LinearLayout.LayoutParams(size, size);
		LayoutParams firstParams = new LinearLayout.LayoutParams(size, size);
		if (leftMargin == 0) {
			leftMargin = (int) getResources().getDimension(
					R.dimen.pager_indicator_margin);
		}
		params.leftMargin = leftMargin;

		// dot color
		if (haveIndicator) {
			for (int i = 0; i < pagerList.size(); i++) {
				IndicatorView indicatorView;
				if (hintColor != -1) {
					indicatorView = new IndicatorView(getActivity(), hintColor,
							size, size);
				} else {
					indicatorView = new IndicatorView(getActivity(), size, size);
				}
				if (i == 0) {
					hintIndicatorLayout.addView(indicatorView, firstParams);
				} else {
					hintIndicatorLayout.addView(indicatorView, params);
				}
			}

			IndicatorView indicatorView;
			if (indicateColor != -1) {
				indicatorView = new IndicatorView(getActivity(), indicateColor,
						size, size);
			} else {
				indicatorView = new IndicatorView(getActivity(), getResources()
						.getColor(R.color.indicator), size, size);
			}
			indicatorLayout.addView(indicatorView, firstParams);
		}

		// drag animation
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			private View indicator = indicatorLayout.getChildAt(0);

			@Override
			public void onPageSelected(int position) {
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

				// out of index
				if (position + 1 >= pagerList.size()) {
					return;
				}
				float currentX = hintIndicatorLayout.getChildAt(position)
						.getX();
				float nextX = hintIndicatorLayout.getChildAt(position + 1)
						.getX();
				// two dot space
				float space = nextX - currentX;
				indicator.setX(hintIndicatorLayout.getChildAt(position).getX()
						+ space * positionOffset);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});

		if (autoRoll) {
			timer = new Timer();
			try {
				startRoll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return rootView;
	}

	/*
	 * If use default constructor, this fragment will be create an owner pager
	 * item for demo
	 */
	private void initPagerItem() {
		if (!DEBUG) {
			return;
		}
		if (pagerList == null) {
			pagerList = new ArrayList<View>();
			for (int i = 0; i < DEFAULT_PAGER_COUNT; i++) {
				View pagerItem = LayoutInflater.from(getActivity()).inflate(
						R.layout.pager_item, null);
				TextView itemTv = (TextView) pagerItem
						.findViewById(R.id.item_tv);
				itemTv.setText(i + "");
				pagerList.add(pagerItem);
			}
		}
	}

	/*
	 * Init pager transformer
	 */
	private void initPagerTransformer() {
		switch (effect) {
		case DEPTH:
			viewPager.setPageTransformer(true, new DepthPageTransformer());
			break;
		case ZOOM:
			viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
			break;
		case FADE:
			viewPager.setPageTransformer(true, new FadePageTransformer());
			break;
		case NONE:
		default:
			break;
		}
	}

	/**
	 * If you want to create other transformer animations, you can extends
	 * {@link ViewPager.PageTransformer} <br/>
	 * and override {@link transformPage()} method.
	 * 
	 * @param transformer
	 * 
	 */
	public void setPagerTransformer(ViewPager.PageTransformer transformer) {
		viewPager.setPageTransformer(true, transformer);
	}

	/**
	 * example 0xff000011
	 * 
	 * @param hint
	 * @param indicator
	 */
	public void setIndicatorColor(int hint, int indicator) {
		hintColor = hint;
		indicateColor = indicator;
	}

	/**
	 * indicator dot space
	 * 
	 * @param space
	 */
	public void setDotSpace(int space) {
		if (params != null) {
			params.leftMargin = space;
		} else {
			leftMargin = space;
		}
	}

	public List<View> getPagerList() {
		return pagerList;
	}

	public void setPagerList(List<View> pagerList) {
		this.pagerList = pagerList;
	}

	public DragEffect getEffect() {
		return effect;
	}

	public void setEffect(DragEffect effect) {
		this.effect = effect;
	}

	public int getIndicateColor() {
		return indicateColor;
	}

	public void setIndicateColor(int indicateColor) {
		this.indicateColor = indicateColor;
	}

	public int getHintColor() {
		return hintColor;
	}

	public void setHintColor(int hintColor) {
		this.hintColor = hintColor;
	}

	public boolean isHaveIndicator() {
		return haveIndicator;
	}

	public void setHaveIndicator(boolean haveIndicator) {
		this.haveIndicator = haveIndicator;
	}

	public ViewPager getViewPager() {
		return viewPager;
	}

	public void setAutoRoll(boolean auto) {
		this.autoRoll = auto;
	}

	public void setRollTime(int milliseconds) {
		this.milliseconds = milliseconds;
	}

	public void stopRoll() throws Exception {
		if (timer == null) {
			Log.e(TAG, "do not turn on the auto roll");
			throw new Exception("do not turn on the auto roll");
		}
		if (autoRollTask != null) {
			autoRollTask.cancel();
		}
		timer.purge();
	}

	public void startRoll() throws Exception {
		stopRoll();
		autoRollTask = new PagerAutoRollTask(pagerList.size());
		timer.schedule(autoRollTask, milliseconds, milliseconds);
	}

	@Override
	public void onStop() {
		super.onStop();
		try {
			stopRoll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class AutoRollPagerAdapter extends PagerAdapter {

		private List<View> pagerList;

		public AutoRollPagerAdapter(List<View> pagerList) {
			this.pagerList = pagerList;
		}

		@Override
		public int getCount() {
			return this.pagerList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(pagerList.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(pagerList.get(position));
			return pagerList.get(position);
		}

	}

	class ZoomOutPageTransformer implements ViewPager.PageTransformer {
		private static final float MIN_SCALE = 0.85f;
		private static final float MIN_ALPHA = 0.5f;

		public void transformPage(View view, float position) {
			int pageWidth = view.getWidth();
			int pageHeight = view.getHeight();

			Log.d("bill", "position " + position);
			if (position < -1) { // [-Infinity,-1)
				// This page is way off-screen to the left.
				view.setAlpha(0);

			} else if (position <= 1) { // [-1,1]
				// Modify the default slide transition to shrink the page as
				// well
				float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
				float vertMargin = pageHeight * (1 - scaleFactor) / 2;
				float horzMargin = pageWidth * (1 - scaleFactor) / 2;
				if (position < 0) {
					view.setTranslationX(horzMargin - vertMargin / 2);
				} else {
					view.setTranslationX(-horzMargin + vertMargin / 2);
				}

				// Scale the page down (between MIN_SCALE and 1)
				view.setScaleX(scaleFactor);
				view.setScaleY(scaleFactor);

				// Fade the page relative to its size.
				view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
						/ (1 - MIN_SCALE) * (1 - MIN_ALPHA));

			} else { // (1,+Infinity]
				// This page is way off-screen to the right.
				view.setAlpha(0);
			}
		}
	}

	class DepthPageTransformer implements ViewPager.PageTransformer {
		private static final float MIN_SCALE = 0.75f;

		public void transformPage(View view, float position) {
			int pageWidth = view.getWidth();

			if (position < -1) { // [-Infinity,-1)
				// This page is way off-screen to the left.
				view.setAlpha(0);

			} else if (position <= 0) { // [-1,0]
				// Use the default slide transition when moving to the left page
				view.setAlpha(1);
				view.setTranslationX(0);
				view.setScaleX(1);
				view.setScaleY(1);

			} else if (position <= 1) { // (0,1]
				// Fade the page out.
				view.setAlpha(1 - position);

				// Counteract the default slide transition
				view.setTranslationX(pageWidth * -position);

				// Scale the page down (between MIN_SCALE and 1)
				float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
						* (1 - Math.abs(position));
				view.setScaleX(scaleFactor);
				view.setScaleY(scaleFactor);

			} else { // (1,+Infinity]
				// This page is way off-screen to the right.
				view.setAlpha(0);
			}
		}
	}

	class FadePageTransformer implements ViewPager.PageTransformer {

		public void transformPage(View view, float position) {

			if (position < -1) {
				view.setAlpha(0);
			} else if (position <= 0) {// [-1,0] left
				float alpha = ((1 + position) / 1f);
				view.setAlpha(alpha);
			} else if (position <= 1) {// [0,1] right
				float alpha = ((1 - position) / 1f);
				view.setAlpha(alpha);
			} else {
				view.setAlpha(0);
			}
		}
	}

	class IndicatorView extends View {

		private ShapeDrawable background;
		private OvalShape rectOval;
		private float size;
		private int indicatorColor;

		/**
		 * 
		 * @param context
		 * @param width
		 *            rect bounds of dot
		 * @param height
		 *            rect bounds of dot
		 * @param color
		 */
		public IndicatorView(Context context, int color, float width,
				float height) {
			this(context);
			setIndicattorColor(color);
			setIndicatorSize(width, height);
		}

		/**
		 * With default color
		 * 
		 * @param context
		 * @param width
		 *            rect bounds of dot
		 * @param height
		 *            rect bounds of dot
		 */
		public IndicatorView(Context context, float width, float height) {
			this(context);
			setIndicatorSize(width, height);
		}

		/**
		 * With defualt size
		 * 
		 * @param context
		 * @param color
		 *            color values, example: 0xff112233
		 */
		public IndicatorView(Context context, int color) {
			this(context);
			setIndicattorColor(color);
		}

		public IndicatorView(Context context) {
			super(context);
			initIndicatorView();
		}

		private void initIndicatorView() {
			// size = getResources()
			// .getDimension(R.dimen.pager_indicator_dot_size);
			indicatorColor = getResources().getColor(R.color.indicator_hint);
			rectOval = new OvalShape();
			rectOval.resize(size, size);
			background = new ShapeDrawable(rectOval);
			background.getPaint().setColor(indicatorColor);
		}

		public void setIndicattorColor(int color) {
			background.getPaint().setColor(color);
		}

		public void setIndicatorSize(float width, float height) {
			rectOval.resize(width, height);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			background.draw(canvas);
		}
	}

	class PagerAutoRollTask extends TimerTask {

		private int sum;
		private int count = 1;

		public PagerAutoRollTask(int sum) {
			this.sum = sum;
		}

		@Override
		public void run() {
			if (count >= sum) {
				count = 0;
			}
			handler.sendEmptyMessage(count);
			count++;
		}
	}
}
