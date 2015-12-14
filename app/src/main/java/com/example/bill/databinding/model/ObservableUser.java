package com.example.bill.databinding.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.bill.BR;


/**
 * Created by bill_lv on 2015/11/15.
 */
public class ObservableUser extends BaseObservable {

    private String firstName;
    private String lastName;
    private int age;

    public ObservableUser(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    public boolean isAdult() {
        if (age > 18) {
            return true;
        } else {
            return false;
        }
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR._all);
    }
}
