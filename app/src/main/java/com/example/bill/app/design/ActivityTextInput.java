package com.example.bill.app.design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.bill.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bill_lv on 2016/4/12.
 */
public class ActivityTextInput extends AppCompatActivity {

    @BindView(R.id.text_input)
    TextInputLayout textInputLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input);
        ButterKnife.bind(this);

        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // the setError method have a bug, it lets the textview which show error message gone when setErrorEnable to true or false again
                // so, in this case just set error message empty replace setErrorEnable false
                if (s.length() < 4){
//                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("name length must greater than 4");
                }else {
                    textInputLayout.setCounterEnabled(true);
//                    textInputLayout.setErrorEnabled(false);
                    textInputLayout.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
