package com.exmaple.bill.designpattern.mvvm.utils;

import com.exmaple.bill.designpattern.mvvm.model.User;

/**
 * Created by bill_lv on 2015/11/15.
 */
public class StringUtils {

    public static String capitalize(String str){
        return str.toUpperCase();
    }

    public static <T> String generic(T t){
        return t.toString();
    }
    public static String g(Object o){
        return generic(o);
    }

}
