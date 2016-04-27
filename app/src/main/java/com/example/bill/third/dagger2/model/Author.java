package com.example.bill.third.dagger2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/4/21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    @JsonProperty("name")
    public String name;
    @JsonProperty("email")
    public String email;
    @JsonProperty("date")
    public String date;
}
