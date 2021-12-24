package com.web.blog.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

public class test {
    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
       int year =  calendar.get(Calendar.YEAR);
       int month = calendar.get(Calendar.MONTH)+1;
       int day = calendar.get(Calendar.DATE);
       String num = year+""+month+""+day+"";
        System.out.println(simpleDateFormat.format(date));
    }
}
