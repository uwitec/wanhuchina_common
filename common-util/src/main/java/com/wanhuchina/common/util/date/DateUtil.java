package com.wanhuchina.common.util.date;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class DateUtil {
    private static SimpleDateFormat dateFormat;
    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    /**
     * Retrieves today start of day
     */
    public static Date getTodayStart() {
        return getDayStart(new Date());
    }

    /**
     * Retrieves this week start of week
     */
    public static Date getThisWeekStart() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Returns the time corresponding to the start of a day
     */
    public static Date getDayStart(Date d) {
        try {
            Date date = dateFormat.parse(dateFormat.format(d));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Returns the time corresponding to the end of a day specified as yyyy-MM-dd
     */
    public static Date getDayEnd(String dateString) {
        try {
            Date date = dateFormat.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            return calendar.getTime();
        } catch (ParseException e) {
            return null;
        }
    }
    
    
    
    
    /**
     * add by Allen date:2014-09-25 21:20 
     * Returns the time corresponding to the start of a day. GMT
     */
    public static Date getTodayByGMT() {
    	Date date = new Date(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Returns the time corresponding to the end of a day
     */
    public static Date getDayEnd(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
    /**
     * return GMT time end day by Charlie 2014-11-27
     */
    public static Date getGMTDayEnd(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * return GMT time start day , by kael 2015-06-12 
     */
    public static Date getGMTDayStart(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 000);
        return calendar.getTime();
    }

    public static Timestamp getDayStartTimestamp(String dateString, String aFormat) {
        Date date = getDayStart(DateFormatter.fromFormattedStringLocal(dateString, aFormat));
        return date != null ? new Timestamp(date.getTime()) : null;
    }

    public static String getDayStart() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = new Date(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 000);

        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return dateFormat.format(calendar.getTime());

    }

    public static Date getNextDayStart(Date dateBefore) {
        Date date = new Date(dateBefore.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 000);

        return calendar.getTime();

    }

    public static String getNextDayStartStr(Date dateBefore) {
        Date date = new Date(dateBefore.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 000);
        SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(calendar.getTime());
    }

    public static Date getPreDayStart() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = new Date(System.currentTimeMillis());
        date = DateUtils.addDays(date, -1);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 000);

        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return calendar.getTime();

    }

    /**
     * 是否是同一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1,Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date1).equals(df.format(date2));
    }

    public static Timestamp getDayEndTimestamp(String dateString, String aFormat) {
        Date date = getDayEnd(DateFormatter.fromFormattedStringLocal(dateString, aFormat));
        return date != null ? new Timestamp(date.getTime()) : null;
    }

    public static int getDaysOffset(final Date start, final Date end) {
        return Days.daysBetween(new DateTime(DateUtil.getDayStart(start)), new DateTime(DateUtil.getDayStart(end)))
                .getDays();
    }
    
    /**
     * 获取上月第一天凌晨 2015-11-01 00:00:00
     * 
     * @return Date
     */
    public static Date getLastMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.MONTH, -1); // 月数减1
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取上月最后一天凌晨 2015-11-30 00:00:00
     * 
     * @return Date
     */
    public static Date getLastMonthLastDay() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为当月1号
        calendar.add(Calendar.DAY_OF_YEAR, -1);// 日期-1
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    public static String fromDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=sdf.format(date);
        return str;
    }
}