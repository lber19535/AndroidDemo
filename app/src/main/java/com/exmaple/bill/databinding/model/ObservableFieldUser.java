package com.exmaple.bill.databinding.model;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by Bill Lv on 2015/11/21.
 */
public class ObservableFieldUser {
    public final ObservableField<String> firstName = new ObservableField<>();
    public final ObservableField<String> lastName = new ObservableField<>();
    public final ObservableInt age = new ObservableInt();
}
