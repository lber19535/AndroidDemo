package com.example.bill.app.design;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

/**
 * Created by bill_lv on 2016/4/13.
 */
class ReAdapter extends RecyclerView.Adapter<VH> {

    List<Map<String, String>> data;
    BottomSheetBehavior b;

    public ReAdapter(List<Map<String, String>> data) {
        this.data = data;
//        this.b = behavior;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.tv.setText(data.get(position).get("key"));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
