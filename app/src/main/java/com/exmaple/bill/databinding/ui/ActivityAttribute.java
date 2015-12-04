package com.exmaple.bill.databinding.ui;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.exmaple.bill.App;
import com.exmaple.bill.R;
import com.exmaple.bill.databinding.ActivityDatabindingAttributeBinding;
import com.exmaple.bill.databinding.view.NameCardView;

/**
 * Created by bill_lv on 2015/11/23.
 */
public class ActivityAttribute extends AppCompatActivity {

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ActivityAttribute.this,"OPS !!",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDatabindingAttributeBinding bind = DataBindingUtil.setContentView(this, R.layout.activity_databinding_attribute);
        bind.setActivity(this);
        bind.setImageUrl("xxxx");
    }

    // 被
//    @BindingAdapter({"xxx:imageUrl"})
//    public static void loadImage(NameCardView view, String url) {
//        Toast.makeText(App.getAppContext(),"load img success",Toast.LENGTH_SHORT).show();
//    }
}
