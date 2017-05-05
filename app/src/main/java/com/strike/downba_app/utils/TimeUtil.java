package com.strike.downba_app.utils;

import android.text.TextUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lengqi on 2016/3/9.
 */
public class TimeUtil {

    //日期转时间戳
    public static long dateToLong(String dateStr,String formartStr){
        if (formartStr == null) {
            formartStr = "yyyy-MM-dd hh:mm:ss";
        }
        long ts=0;
        SimpleDateFormat format = new SimpleDateFormat(formartStr);
        if (!TextUtils.isEmpty(dateStr)) {
            try {
                ts = format.parse(dateStr).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return ts;
    }
    /**
     * 转换日期格式.
     *
     * @param date          date 需要转换的日期
     * @param dateFormat    dateFormat 原日期格式
     * @param newDateFormat newDateFormat 新日期格式
     * @return String
     * @throws ParseException the parse exception
     * @version
     */
    public static String format(String date, String dateFormat, String newDateFormat) throws ParseException {
        if (date == null || date.trim().equals("") || dateFormat == null || dateFormat.equals("")
                || newDateFormat == null || newDateFormat.equals("")) {
            return "";
        }
        SimpleDateFormat oldTimeFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        Date oldDate = oldTimeFormat.parse(date);
        return format(oldDate, newDateFormat);
    }

    /**
     * 转换日期格式.
     *
     * @param ca            Calendar
     * @param newDateFormat newDateFormat
     * @return String
     * @throws ParseException the parse exception
     * @version
     */
    public static String format(Calendar ca, String newDateFormat) throws ParseException {
        return format(ca.getTime(), newDateFormat);
    }

    /**
     * 转换日期格式.
     *
     * @param date          date
     * @param newDateFormat newDateFormat
     * @return String
     * @throws ParseException the parse exception
     * @version
     */
    public static String format(Date date, String newDateFormat) throws ParseException {
        SimpleDateFormat newTimeFormat = new SimpleDateFormat(newDateFormat, Locale.ENGLISH);
        return newTimeFormat.format(date);
    }

    /**
     * 计算两个时间点的差值.
     *
     * @param starttime 开始时间
     * @param endtime   结束时间
     * @param format    时间格式，如：yyyy-MM-dd HH:mm:ss
     * @return long 时间差，单位秒
     * @throws ParseException the parse exception
     * @version
     */
    public static long computeTime(String starttime, String endtime, String format) throws ParseException {
        if (starttime == null || starttime.equals("") || endtime == null || endtime.equals("") || format == null
                || format.equals("")) {
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        Date start = sdf.parse(starttime);
        Date end = sdf.parse(endtime);
        long dateS = (end.getTime() - start.getTime()) / 1000; // 秒数
        return dateS;
    }
    /********计算某个时间与当前时间差********/
    public static Long diffTime(Timestamp ts){
        long t = System.currentTimeMillis();//
        long endTime = ts.getTime();
        return endTime - t;
    }
    public static String TimestampToString(Timestamp ts,String formart){
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat(formart);
        try {
            //方法一
            tsStr = sdf.format(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tsStr;
    }
    /****date转String****/
    public static String dateToString(Date d,String format){
        DateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }
    /******long转时间****/
    public static String longToDateStr(long m,String formart){
        if (formart == null) {
            formart = "yyyy-MM-dd";
        }
        if (m < 1000000000000L) {
            m = m*1000L;
        }
        DateFormat sdf = new SimpleDateFormat(formart);
        Date d = new Date(m);
        System.out.println(sdf.format(d));
        return sdf.format(d);
    }

    /**
     * @param time 单位秒
     * @return xx天xx小时xx分钟
     */
    public static String getDurationTime(long time) {
        String result = "";
        int perMin = 60;
        int perHour = 60 * perMin;
        int perDay = 24 * perHour;
        long leftSecond = 0;
        int durationMins = 0;
        int durationHours = 0;
        int durationDays = 0;
        durationDays = (int) ((time + 59) / perDay);
        leftSecond = time % perDay;
        durationHours = (int) (leftSecond / perHour);
        leftSecond = leftSecond % perHour;
        durationMins = (int) (leftSecond / perMin);
        if (durationDays > 0) {
            result = String.format("%d天%d小时%d分", durationDays, durationHours, durationMins);
        } else if (durationHours > 0) {
            result = String.format("%d小时%d分", durationHours, durationMins);
        } else if (durationMins > 0) {
            result = String.format("%d分", durationMins);
        } else {
            result = "0分";
        }
        return result;
    }

    /**
     * @param timeStr 单位秒
     * @return xx天xx小时xx分钟
     */
    public static String getDurationTime(String timeStr) {
        String result = "";
        int perMin = 60;
        int perHour = 60 * perMin;
        int perDay = 24 * perHour;
        long leftSecond = 0;
        int durationMins = 0;
        int durationHours = 0;
        int durationDays = 0;
        long time = 0;
        try {
            time = Long.parseLong(timeStr) + 59;
        } catch (Exception e) {
            e.printStackTrace();
        }
        durationDays = (int) (time / perDay);
        leftSecond = time % perDay;
        durationHours = (int) (leftSecond / perHour);
        leftSecond = leftSecond % perHour;
        durationMins = (int) (leftSecond / perMin);
        if (durationDays > 0) {
            result = String.format("%d天%d小时%d分", durationDays, durationHours, durationMins);
        } else if (durationHours > 0) {
            result = String.format("%d小时%d分", durationHours, durationMins);
        } else if (durationMins > 0) {
            result = String.format("%d分", durationMins);
        } else {
            result = "0分";
        }
        return result;
    }
}
