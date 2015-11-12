package com.exmaple.bill.third.eventbus.ui.msg;

/**
 * Created by bill_lv on 2015/11/12.
 */
public class PriorityMessageEvent {
    public static int PRIORITY = 1;
    public final String message;

    public PriorityMessageEvent(String message) {
        this.message = message;
    }
}
