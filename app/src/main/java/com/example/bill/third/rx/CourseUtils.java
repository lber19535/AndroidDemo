package com.example.bill.third.rx;

import java.util.Random;

/**
 * Created by bill_lv on 2015/12/7.
 */
public class CourseUtils {

    private static String[] courses = {"English","Math","Physical","Programming","Geography","Sport","Music"};

    public static String getRandomCourse(){
        int index = new Random().nextInt(courses.length);
        return courses[index];
    }
}
