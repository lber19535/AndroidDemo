package com.example.bill.third.dagger2.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.bill.R;
import com.example.bill.third.dagger2.model.GithubCommitItem;

import java.util.List;

/**
 * Created by bill_lv on 2016/4/26.
 */
public class CommitListAdapter extends BaseAdapter {

    private List<GithubCommitItem> items;
    private Context context;

    public CommitListAdapter(List<GithubCommitItem> items, Context context) {
        this.items = items;
        this.context = context;
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

        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_commit, parent, false);
            holder.msg = (AppCompatTextView) convertView.findViewById(R.id.msg);
            holder.date = (AppCompatTextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.msg.setText(items.get(position).commit.message);
        holder.date.setText(items.get(position).commit.author.date);
        return convertView;
    }

    class Holder {
        AppCompatTextView msg;
        AppCompatTextView date;
    }
}
