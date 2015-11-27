package com.exmaple.bill.anim.demo;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exmaple.bill.R;
import com.exmaple.bill.databinding.ListItemNameBinding;
import com.exmaple.bill.databinding.model.NameRecyclerAdapter;
import com.exmaple.bill.databinding.model.User;
import com.exmaple.bill.databinding.utils.NameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by bill_lv on 2015/11/25.
 */
public class ActivityLayoutAnimation extends AppCompatActivity {

    @Bind(R.id.target_lv)
    LinearLayout targetLv;
    List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation);


        for (int i = 0; i < 10; i++) {
            User user = new User(NameUtils.getRandomFirstName(), NameUtils.getRandomLastName());
            userList.add(user);
        }

        ButterKnife.bind(this);

        LayoutTransition transition = new LayoutTransition();
        ObjectAnimator addAnimator = ObjectAnimator.ofFloat(null, "alpha", 0f, 1f);
        addAnimator.setDuration(3000);
        transition.setAnimator(LayoutTransition.APPEARING, addAnimator);
        ObjectAnimator removeAnimator = ObjectAnimator.ofFloat(null, "alpha", 1f, 0f);
        removeAnimator.setDuration(3000);
        transition.setAnimator(LayoutTransition.DISAPPEARING, removeAnimator);
//        transition.setAnimator(LayoutTransition.CHANGE_APPEARING, addAnimator);
//        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, removeAnimator);

        targetLv.setLayoutTransition(transition);

    }

    public void addItem(View v) {
        User user = new User(NameUtils.getRandomFirstName(), NameUtils.getRandomLastName());
        LinearLayout item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_name_card, null);
        ((TextView)item.findViewById(R.id.first_name)).setText(user.getFirstName());
        ((TextView)item.findViewById(R.id.last_name)).setText(user.getLastName());

        targetLv.addView(item);
    }

    public void addIndexItem(View v){
        User user = new User(NameUtils.getRandomFirstName(), NameUtils.getRandomLastName());
        LinearLayout item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_name_card, null);
        ((TextView)item.findViewById(R.id.first_name)).setText(user.getFirstName());
        ((TextView)item.findViewById(R.id.last_name)).setText(user.getLastName());

        targetLv.addView(item, 1);
    }

    public void removeItem(View v) {
        if (targetLv.getChildCount() > 0){
            targetLv.removeViewAt(0);
        }
    }


}
