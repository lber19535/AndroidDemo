package com.bill.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivityTertiary extends Activity {
	
	private ListView mListView;
	private String[] strs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tertiary);
		
		mListView = (ListView) findViewById(R.id.list);
		strs = getResources().getStringArray(R.array.main_list_strs);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strs);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					Intent intent = new Intent(ActivityTertiary.this, ActivityContent.class);
					startActivity(intent);
					break;

				default:
					break;
				}
			}
		});
	}

}
