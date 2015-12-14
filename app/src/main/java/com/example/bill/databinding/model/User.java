package com.example.bill.databinding.model;

/**
 * Created by bill_lv on 2015/11/15.
 */
public class User {

    private String firstName;
    private String lastName;
    private boolean isupper;

    public User(String fisrtName, String lastName) {
        this.firstName = fisrtName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isupper() {
        return isupper;
    }

    public void setIsupper(boolean isupper) {
        this.isupper = isupper;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
