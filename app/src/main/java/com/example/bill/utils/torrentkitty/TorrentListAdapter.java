package com.example.bill.utils.torrentkitty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.example.bill.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bill_lv on 2015/12/17.
 */
public class TorrentListAdapter extends BaseAdapter {

    private List<MagnetContent> contents;
    private Context context;

    public TorrentListAdapter(List<MagnetContent> contents, Context context) {
        this.contents = contents;
        this.context = context;
    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public Object getItem(int position) {
        return contents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_torrent, parent, false);

            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.torrentNameTv.setText(contents.get(position).getName());
        return convertView;
    }

    class Holder {
        @BindView(R.id.torrent_name)
        CheckedTextView torrentNameTv;

        public Holder(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
