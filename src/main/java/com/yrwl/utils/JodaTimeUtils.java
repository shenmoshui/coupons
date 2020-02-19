package com.yrwl.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.Date;

/**
 * @author  shentao
 * @date    2020-02-11
 */
public class JodaTimeUtils {

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String STANDARD_DATE = "yyyy-MM-dd";

    public static Date strToDate(String dateTimeStr, String formatStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date,String formatStr){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static Date strToDate(String dateTimeStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    /**
     * 默认日期转字符串
     * @param date
     * @return
     */
    public static String dateToStr(Date date){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

    /**
     * 增加日期
     * @param amount
     * @return
     */
    public static String adjustDate(int amount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, amount);

        return dateToStr(calendar.getTime(), "yyyy-MM-dd");
    }

    /**
     * 增加数
     * @param date
     * @param model
     * @param amount
     * @return
     */
    public static String adjustTime(Date date, int model, int amount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(model, amount);

        return dateToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 时间分钟差
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int distanceTime(String beginTime, String endTime){

        return (int)((strToDate(endTime).getTime() - strToDate(beginTime).getTime())/(1000*60));
    }

    public static void main(String[] args) {
        System.out.println(JodaTimeUtils.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
        System.out.println(JodaTimeUtils.strToDate("1970-01-01 09:00:30","yyyy-MM-dd HH:mm:ss"));
        System.out.println(adjustDate(-7));
        System.out.println(adjustTime(new Date(), Calendar.HOUR_OF_DAY, -24));
        System.out.println(distanceTime(JodaTimeUtils.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"), adjustTime(new Date(), Calendar.HOUR_OF_DAY, 24)));
    }
}
