package com.example.bill.third.dagger2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bill_lv on 2016/4/21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubCommitItem {
    @JsonProperty("commit")
    public Commit commit;
}
