package com.exmaple.bill.databinding.ui;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.exmaple.bill.App;
import com.exmaple.bill.R;
import com.exmaple.bill.databinding.ActivityDatabindingAttributeBinding;
import com.exmaple.bill.databinding.NameCard;

import android.widget.ImageView;

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

    @BindingAdapter({"xxx:imageUrl"})
    public static void loadImage(NameCard view, String url) {
        Toast.makeText(App.getAppContext(),"load img success",Toast.LENGTH_SHORT).show();
    }
}
