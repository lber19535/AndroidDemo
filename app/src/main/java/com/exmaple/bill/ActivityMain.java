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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * 列表界面
 * 
 * @author Lv Beier
 *
 */
public class ActivityMain extends AppCompatActivity {

    private ListView mListView;

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

        BaseAdapter adapter = new SimpleAdapter(this, getListData(path),
                android.R.layout.simple_list_item_1, new String[] { TITLE },
                new int[] { android.R.id.text1 });
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) mListView
                        .getItemAtPosition(position);
                Intent intent = (Intent) map.get(INTENT);
                startActivity(intent);
            }
        });

    }

    private List<Map<String, Object>> getListData(String prefix) {
        PackageManager pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory("android.intent.category.bill.SAMPLE_CODE");
        // 获取activity列表
        List<ResolveInfo> mResolveInfo = pm.queryIntentActivities(intent, 0);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // 根据label获取当前显示列表
        for (ResolveInfo resolveInfo : mResolveInfo) {
            /**
             * 1.获取当前所有activity的label
             * 2.判断除prefix外是否含有 / 初始的prefix是空
             * |----1.含有，分割label，取出除prefix外第一项作为TITLE放入map，
             * |	   |----1.将prefix和key加slash组合成PATH放入intent中
             * |      |----2.将INTENT放入map中
             * |----2.不含，取处prefix外的label作为列表中的显示项，作为TITLE放入map
             * |      |----1.将完整label放入path作为下一个activity的title
             * |      |----2.用类名组合成一个INTENT放入map
             */
            String activityName = resolveInfo.activityInfo.name;
            String packageName = resolveInfo.activityInfo.packageName;
            Map<String, Object> item = new HashMap<String, Object>();

            // 1
            String label = resolveInfo.loadLabel(pm).toString();
            if (!label.startsWith(prefix)) {
                continue;
            }
            String nextPath = label.replace(prefix, "");
            // 2
            boolean haveNext = nextPath.contains("/");
            if (haveNext) {
                // 2.1
                String title = nextPath.split("/")[0];
                // 判断是否有重复的title
                if (haveSameTitle(list, title)) {
                    continue;
                }
                item.put(TITLE, title);
                // 2.1.1
                String path = prefix + title + "/";
                Intent nextIntent = new Intent(this, ActivityMain.class);
                // 2.1.2
                nextIntent.putExtra(PATH, path);
                item.put(INTENT, nextIntent);
                list.add(item);
            } else {
                // 2.2
                String title = nextPath;
                item.put(TITLE, title);
                // 2.2.1
                Intent nextIntent = new Intent();
                nextIntent.setClassName(packageName, activityName);
                nextIntent.putExtra(PATH, label);
                item.put(INTENT, nextIntent);
                list.add(item);
            }
        }
        return list;
    }

    private boolean haveSameTitle(List<Map<String, Object>> list, String title) {
        for (Map<String, Object> map : list) {
            if (map.get(TITLE).toString().equals(title)) {
                return true;
            }
        }
        return false;
    }

}
