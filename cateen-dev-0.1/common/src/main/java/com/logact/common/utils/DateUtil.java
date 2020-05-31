package com.logact.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: logact
 * @date: Created in 2020/5/25 11:21
 * @description:
 */
public class DateUtil {

    public static void main(String[] args) {
        DateFormat df = DateFormat.getDateInstance();
        String format = df.format(new Date());
        System.out.println(timeCompare("2020-05-26"));
    }
    public static boolean isExpired(Date date){
        if(timeCompare(date)<0)return true;
        return false;
    }
    public static boolean isExpired(String date){
        if(timeCompare(date)<0)return true;
        return  false;
    }
    public static int  timeCompare(Date date){
        DateFormat df = DateFormat.getDateInstance();
        String formatDate = df.format(date);
        return timeCompare(formatDate);
    }
    public static int timeCompare(String t1){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        try {
            c1.setTime(formatter.parse(t1));
            DateFormat df  = DateFormat.getDateInstance();
            c2.setTime(formatter.parse(df.format(new Date())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result=c1.compareTo(c2);
        return result;
    }
    public static String DateToString(Date date){
        DateFormat df = DateFormat.getDateInstance();
        return df.format(date);
    }

}
