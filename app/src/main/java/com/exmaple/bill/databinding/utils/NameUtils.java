package com.exmaple.bill.databinding.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Bill Lv on 2015/11/20.
 */
public class NameUtils {
   public static final String[] FIRST_NAMES = {"Peter", "Bill", "Jessie", "Alex", "Tim"};
   public static final String[] LAST_NAMES = {"Gates", "Phillips", "Cook", "Jobs"};
   public static final List<String> TEST_STR_LIST = Arrays.asList(FIRST_NAMES);

   public static final int FIELD_FIRST_NAME = 0;
   public static final int FIELD_LAST_NAME = 1;

   public static String getRandomFirstName(){
      int index = new Random().nextInt(FIRST_NAMES.length);
      return FIRST_NAMES[index];
   }

   public static String getRandomLastName(){
      int index = new Random().nextInt(LAST_NAMES.length);
      return LAST_NAMES[index];
   }
}
