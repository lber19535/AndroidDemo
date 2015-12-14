package com.example.bill.databinding.model.other;

/**
 * Created by bill_lv on 2015/11/15.
 */
public class User {

    private String firstName;
    private String lastName;

    public User(String fisrtName, String lastName) {
        this.firstName = fisrtName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
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
}
