package com.xiuye.util.algorithm.basics;

import com.xiuye.util.X;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class Time {

    private long mSec;
    private long sec;
    private long min;
    private long hour;
    private long daysOfMon;
    private long mon;
    private long year;


    public static boolean isLeap(long year) {
//        能被4整除，但不能被100整除；
//        能被400整除。
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /**
     * The number of days in a 400 year cycle.
     * 公式口诀:4年一闰，百年不闰，4百年又闰
     * 400*365 + 100*4 - 4 + 1
     * 四百年的总天数
     * 公式资料:
     * 关于公历闰年是这样规定的：地球绕太阳公转一周叫做一回归年，一回归年长365日5时48分46秒。
     * 地球绕太阳转一圈需要365天5时48分46秒，也就是365.2422天
     * 现时的公历以回归年为“年”的计算基础，
     * 而一个回归年大约等于365.24220日。
     * 因为在平年公历只计算365日，结果四年后便会累积0.24220×4=0.9688日，大约等于一日，
     * 所以便逢四年增加一日闰日以抵销这0.9688日。
     * 但是还是稍微的少了点，这样一时没有多少时间，可是到了一个百年时就会相差了大约0.78天，即多加了。
     * 所以要再次规定细节，到100年是不在加，这样又会每100年的时间少了0.22天，
     * 到了400年时，差了大约0.9天了，所以那个百年是要再加上润日才可以基本对应的。
     * 但是这样仍然会有些许的误差，但是已经相当的小了
     */
    private static final int DAYS_PER_CYCLE = 146097;
    /**
     * The number of days from year zero to year 1970.
     * There are five 400 year cycles from year zero to 2000.
     * There are 7 leap years from 1970 to 2000.
     * 从0000年到1970年的总天数
     * 2000的总天数减去30年的总天数，就是0000~1970年01,01,00:00:00的总天数
     */
    static final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

    /**
     * 1 s = 1000 ms
     * 1 ms = 1000_000 ns
     * 1 s = 1000_000_000 ns
     * milliseconds from 1970-01-01 00:00:00 to now
     * DAYS_0000_TO_1970 = 719528
     */
    public void time() {

        LocalDate date = LocalDate.now();

        long beginYear = 1970;
        //从1970年1月1日0时0分0秒到现在的毫秒数
        long mSecTotal = System.currentTimeMillis();
//        X.lg(mSecTotal);
        //计算时分秒
        //余下的毫秒数
        mSec = mSecTotal % 1000;
        //总秒数
        long secTotal = mSecTotal / 1000;
        //秒
        sec = secTotal % 60;
        //总分钟数
        long minTotal = secTotal / 60;
        //分
        min = minTotal % 60;
        //总小时数
        long hourTotal = minTotal / 60;
        //时
        hour = hourTotal % 24;

        //计算年月日
        //总天数
        long dayTotal = hourTotal / 24;
        long days = dayTotal;

//        day = dayTotal % 30;
//        long yearTotal = dayTotal / 30;
//        year = dayTotal / 365 + beginYear;
        days = 0;
//        计算年月日
        /**
         * 从0000年到1970,01,01,00:00:00的总天数 + 1970,01,01,00:00:00到如今的总天数
         * =从0000~now的总天数
         */
        long zeroDay = days + DAYS_0000_TO_1970;
        /**
         * 第0000年是闰年？所以1月31天，2月19天，所以60天
         * 从0000年03月01日起到现在的总天数
         * 所以 -60,
         * 为什么要这么做呢？
         * 每四年循环，闰日(不是闰年)都是最后一天？
         * zeroDay:是从0000年03月01日起到现在的总天数
         */
        // find the march-based year
        zeroDay -= 60;  // adjust to 0000-03-01 so leap day is at end of four year cycle
        /**
         * ?
         */
        long adjust = 0;
        /**
         * 负的天数 公元前？
         */
        if (zeroDay < 0) {
            /**
             * 调整负的年份 到 正的年份
             * DAYS_PER_CYCLE：四百年的总天数
             * adjustCycles：几个（倍）四百年
             */
            // adjust negative years to positive for calculation
            long adjustCycles = (zeroDay + 1) / DAYS_PER_CYCLE - 1;
            //adjust 调整的年数 负的！
            adjust = adjustCycles * 400;
            //
            zeroDay += -adjustCycles * DAYS_PER_CYCLE;
        }
        /**
         * zeroDay:是从0000年03月01日起到现在的总天数
         * 为啥要乘以400?
         * 为啥要加591?
         * 为啥要除以400年的总天数?
         * (0000年到如今的总天数/400年天数)*400年=多少年，如果有小数那就是正确的年份,
         * 计算机整数除法，会小于 浮点数实际年！！！
         * 计算机由于整除的影响，先让被除数乘以400年在除以400年总天数！
         *  591=400+191
         *  zeroDay + 1天多点
         *  实在不明白为什么是591?
         *
         */
        X.lg(zeroDay, DAYS_PER_CYCLE, zeroDay / DAYS_PER_CYCLE, zeroDay / DAYS_PER_CYCLE * 400, (double) zeroDay / DAYS_PER_CYCLE * 400);
        X.lg(400d * zeroDay / DAYS_PER_CYCLE, 400 * zeroDay / DAYS_PER_CYCLE, 400 * zeroDay % DAYS_PER_CYCLE, 591 / DAYS_PER_CYCLE);
        X.lg((400 * zeroDay + 591) / DAYS_PER_CYCLE);
        long yearEst = (400 * zeroDay + 591) / DAYS_PER_CYCLE;
        /**
         * 公式口诀:4年一闰，百年不闰，4百年又闰
         * 根据公式算出0000年到该年份的总天数，包括闰年的闰日         *
         * 然后zeroDay - 它
         * yearEst实际算出的年份可能要少一年，比如days=0，yearEst本该是1970，但是yearEst=1969
         * 所以根据yearEst算出的天数要比zeroDay实际天数少
         * doyEst:在今年这年已经度过的天数
         */
        long doyEst = zeroDay - (365 * yearEst + yearEst / 4 /*4 闰年*/ - yearEst / 100/*100 不闰年*/ + yearEst / 400/*400 闰年*/);
        if (doyEst < 0) {
            // fix estimate
            yearEst--;
            doyEst = zeroDay - (365 * yearEst + yearEst / 4 - yearEst / 100 + yearEst / 400);
        }
        yearEst += adjust;  // reset any negative year
        int marchDoy0 = (int) doyEst;

        /**
         * 除开1月份，2月份，剩下的月份（5个月份）半年天数是一样的！！！
         * 3    4   5   6   7
         * 31   30  31  30  31
         *
         * 12   11  10  9   8
         * 31   30  31  30  31
         *
         * 31+30+31+30+31 = 153！！！
         *
         * 如果不是整除，也就是浮点数除法
         * marchDoy0/半年（5个月）天数*5 = 实际月份！！！
         *
         *
         */
        // convert march-based values back to january-based
        int marchMonth0 = (marchDoy0 * 5 + 2) / 153;
        /**
         * 前面的计算都是 从 0000,03,01,00:00:00开始的，算的都是400的闰年整数倍
         * 所以必须算的月份加上2个月
         * 因为月份只有12个月，所以要模运算，取余运算
         */
        mon = (marchMonth0 + 2) % 12 + 1;
        /**
         * 半年（5个月）153
         * 306=153+153
         * 也就是3,4,5,6,7加上8,9,10,11,12 月份的天数.
         */
        daysOfMon = marchDoy0 - (marchMonth0 * 306 + 5) / 10 + 1;
        yearEst += marchMonth0 / 10;

        // check year now we are certain it is correct
//        year = YEAR.checkValidIntValue(yearEst);
        if (yearEst < -999999999 || yearEst > 999999999) {
            throw new DateTimeException("Invalid value for year (valid values " + this + "): " + yearEst);
        }
        year = yearEst;

//        return new LocalDate(year, month, dom);

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

        X.lg(year, mon, daysOfMon, days, hour, min, sec, mSec);
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
        X.of(-2 / 2).lgItself();
        X.of(6 / -2).lgItself();
    }
}
