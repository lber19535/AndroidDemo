package com.exmaple.bill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ActivityMain extends Activity {

	private ListView mListView;
	private String[] strs;

	private static final String TITLE = "title";
	private static final String INTENT = "intent";

	private static final String PATH = "com.example.bill.path";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent intent = getIntent();
		String path = intent.getStringExtra(PATH);
		if (path == null) {
			path = "";
		}

		mListView = (ListView) findViewById(R.id.list);
		strs = getResources().getStringArray(R.array.main_list_strs);

		BaseAdapter adapter = new SimpleAdapter(this, getListData(path),
				android.R.layout.simple_list_item_1, new String[] { TITLE },
				new int[] { android.R.id.text1 });
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				mListView.getItemAtPosition(position);
			}
		});

	}

	private List<Map<String, Object>> getListData(String path) {
		PackageManager pm = getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory("android.intent.category.bill.SAMPLE_CODE");
		List<ResolveInfo> mResolveInfo = pm.queryIntentActivities(intent, 0);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (ResolveInfo resolveInfo : mResolveInfo) {
			String activityName = resolveInfo.activityInfo.name;
			String packageName = resolveInfo.activityInfo.packageName;
			String label = resolveInfo.loadLabel(pm).toString();
			Map<String, Object> item = new HashMap<String, Object>();
			item.put(TITLE, label);
			item.put(INTENT, label);
			list.add(item);

			String[] paths = label.split("/");
		}
		return list;
	}

}
