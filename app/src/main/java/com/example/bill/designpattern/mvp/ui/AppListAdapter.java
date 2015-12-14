package com.example.bill.designpattern.mvp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bill.R;
import com.example.bill.designpattern.mvp.model.AppListItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bill Lv on 2015/11/7.
 */
public class AppListAdapter extends BaseAdapter {
    private List<AppListItem> items;
    private Context mContext;

    public AppListAdapter(List<AppListItem> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_app_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AppListItem item = items.get(position);
        holder.appIconIv.setImageDrawable(item.getAppIcon());
        holder.appNameTv.setText(item.getAppName());
        holder.appPkgTv.setText(item.getAppPkg());

        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.icon)
        ImageView appIconIv;
        @Bind(R.id.app_name)
        TextView appNameTv;
        @Bind(R.id.app_pkg)
        TextView appPkgTv;

        ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
