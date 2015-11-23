package com.exmaple.bill.databinding.model;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exmaple.bill.R;
import com.exmaple.bill.databinding.ListItemNameBinding;

import java.util.List;

/**
 * Created by bill_lv on 2015/11/23.
 */
public class NameRecyclerAdapter extends RecyclerView.Adapter<Holder> {

    private List<User> userList;

    public NameRecyclerAdapter(List<User> userList) {
        this.userList = userList;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemNameBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_name, parent, false);
        Holder holder = new Holder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.getBinding().setUser(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

}

class Holder extends RecyclerView.ViewHolder {
    ListItemNameBinding binding;

    public Holder(View itemView) {
        super(itemView);
    }

    public void setBinding(ListItemNameBinding bind) {
        this.binding = bind;
    }

    public ListItemNameBinding getBinding() {
        return binding;
    }
}
