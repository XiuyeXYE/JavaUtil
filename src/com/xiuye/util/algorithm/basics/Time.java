package com.xiuye.util.algorithm.basics;

import com.xiuye.util.X;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class Time {

    private long mSec;
    private long sec;
    private long min;
    private long hour;
    private long day;
    private long mon;
    private long year;


    public static boolean isLeap(long year) {
//        能被4整除，但不能被100整除；
//        能被400整除。
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public void time() {
        long beginYear = 1970;
        long mSecTotal = System.currentTimeMillis();
        X.lg(mSecTotal);
        mSec = mSecTotal % 1000;
        long secTotal = mSecTotal / 1000;
        sec = secTotal % 60;
        long minTotal = secTotal / 60;
        min = minTotal % 60;
        long hourTotal = minTotal / 60;
        hour = hourTotal % 24;
        long dayTotal = hourTotal / 24;
        long days = dayTotal;
        day = dayTotal % 30;
        long yearTotal = dayTotal / 30;
        year = dayTotal / 365 + beginYear;


//        long nonleapYearTime = 365L*24*60*60*1000;
//        X.lg(nonleapYearTime);
//        long leapYearTime = 366L*24*60*60*1000;
//        X.lg(leapYearTime);
//        long fourYearTime = 3*nonleapYearTime+leapYearTime;
//        X.lg(fourYearTime);
//
//        long year = mSecTotal%fourYearTime;
//        long lastYear = mSecTotal/fourYearTime;
//        X.lg(year,lastYear,lastYear*4+beginYear);

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format2.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        X.lg(year, mon, day, days, hour, min, sec, mSec);
        X.lg(format1.format(new Date()), format1.toLocalizedPattern());
        X.lg(format2.format(new Date()), format1.toLocalizedPattern());
        LocalDateTime now = LocalDateTime.now();
        X.lg(now.format(df), df.toString());

    }

    public static void main(String[] args) {
        new Time().time();
//        X.lg(1970,isLeap(1970));
//        X.lg(1971,isLeap(1971));
//        X.lg(1972,isLeap(1972));
//        X.lg(1973,isLeap(1973));
//        X.lg(1974,isLeap(1974));
//        X.lg(1975,isLeap(1975));
//        X.lg(1976,isLeap(1976));
//        X.lg(2000,isLeap(2000));
//        X.lg(2020,isLeap(2020));
    }
}
