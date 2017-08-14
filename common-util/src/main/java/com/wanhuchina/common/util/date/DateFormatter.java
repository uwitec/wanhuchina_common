package com.wanhuchina.common.util.date;

import com.google.common.base.Strings;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {

    private static transient Logger log = LoggerFactory.getLogger(DateFormatter.class);

    public static String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static String DATE_FORMAT = "yyyy-MM-dd";
    public static String MOUNTH_YEAR_FORMAT = "yyyy-MM";
    public static String DATE_TIME_FORMAT_SQL = "yyyy-MM-ddHH:mm:ss";
    public static String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static void main(String[] args) {
        System.out.println(getDateFromStr("2015-09-09"));
        System.out.println(getDateFromStr("2015-09-09 12:34:00"));
        
        System.out.println(getDateFromStr("2015-09-09 28a34:00"));
        System.out.println(getDateFromStr("2015-09-09 28:3"));
    }
    public static Date fromFormattedString(String date) {
        return fromFormattedString(date, DATE_TIME_FORMAT);
    }

    public static Timestamp timeStampLocalFromFormattedString(String date) {
        Timestamp newDate = fromFormattedStringLocalToTimestamp(date, DATE_TIME_FORMAT);
        if (newDate == null) {
            newDate = fromFormattedStringLocalToTimestamp(date, DATE_FORMAT);
        }
        if (newDate == null) {
            newDate = fromFormattedStringLocalToTimestamp(date, MOUNTH_YEAR_FORMAT);
        }
        if (newDate == null) {
            newDate = fromFormattedStringLocalToTimestamp(date, DATE_TIME_FORMAT_SQL);
        }

        return newDate;
    }

    public static Date dateLocalFromFormattedString(String date) {
        Date newDate = fromFormattedStringLocal(date, DATE_TIME_FORMAT);
        if (newDate == null) {
            newDate = fromFormattedStringLocal(date, DATE_FORMAT);
        }
        if (newDate == null) {
            newDate = fromFormattedStringLocal(date, MOUNTH_YEAR_FORMAT);
        }
        if (newDate == null) {
            newDate = fromFormattedStringLocal(date, DATE_TIME_FORMAT_SQL);
        }

        return newDate;
    }

    public static Date getSpecialDayStart(String date) {
        Date newDate = fromFormattedStringLocal(date, DATE_TIME_FORMAT);
        if (newDate == null) {
            newDate = DateUtil.getDayStart(fromFormattedStringLocal(date, DATE_FORMAT));
        }

        return newDate;
    }

    public static Date getSpecialDayEnd(String date) {
        Date newDate = fromFormattedStringLocal(date, DATE_TIME_FORMAT);
        if (newDate == null) {
            newDate = DateUtil.getDayEnd(fromFormattedStringLocal(date, DATE_FORMAT));
        }

        return newDate;
    }

    public static Timestamp getDayStartTimestamp(String dateString, String aFormat) {
        Date date = DateUtil.getDayStart(fromFormattedStringLocal(dateString, aFormat));
        return date != null ? new Timestamp(date.getTime()) : null;
    }

    /**
     * Return a Date instance from string representation in given format
     * 
     * @param aDate
     *            - the string date
     * @param aFormat
     *            - the date format
     */
    public static Date fromFormattedString(String aDate, String aFormat) {

        Date date = null;

        if ((aDate != null && !aDate.isEmpty()) && aFormat != null) {
            SimpleDateFormat format = new SimpleDateFormat(aFormat);
            try {
                date = format.parse(aDate);
            } catch (Exception ex) {
                log.warn("Failed to parse timestamp aDate={}, aFormat={}", aDate, aFormat, ex);
            }
        }

        return date;
    }

    public static Date fromFormattedStringLocal(String aDate, String aFormat) {

        Date date = null;

        if ((aDate != null && !aDate.isEmpty()) && aFormat != null) {
            SimpleDateFormat format = new SimpleDateFormat(aFormat, Locale.getDefault());
            try {
                date = format.parse(aDate);
            } catch (Exception ex) {
                log.warn("Failed to parse timestamp aDate={}, aFormat=", aDate, aFormat, ex);
            }
        }

        return date;
    }

    public static Timestamp fromFormattedStringToTimestamp(String aDate, String aFormat) {

        Timestamp timestamp = null;

        if ((aDate != null && !aDate.isEmpty()) && aFormat != null) {
            SimpleDateFormat format = new SimpleDateFormat(aFormat);
            try {
                Date date = format.parse(aDate);
                timestamp = new Timestamp(date.getTime());
            } catch (Exception ex) {
                log.warn("Failed to parse timestamp aDate={}, aFormat={}", aDate, aFormat, ex);
            }
        }

        return timestamp;
    }

    public static Timestamp fromFormattedStringLocalToTimestamp(String aDate, String aFormat) {

        Timestamp timestamp = null;

        if ((aDate != null && !aDate.isEmpty()) && aFormat != null) {
            SimpleDateFormat format = new SimpleDateFormat(aFormat);
            try {
                Date date = format.parse(aDate);
                timestamp = new Timestamp(date.getTime());
            } catch (Exception ex) {
                log.warn("Failed to parse timestamp aDate={}, aFormat={}", aDate, aFormat, ex);
            }
        }

        return timestamp;
    }

    public static String fromGmtDate(Date date) {
        return fromGmtDate(date, DATE_TIME_FORMAT);
    }

    public static String fromDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static String fromGmtDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * Parse a date from string using a specific formatter
     * 
     * @param date
     * @param dateFormat
     * @return
     */
    public static Date parseDate(String date, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            log.warn("Failed to parse date {} using format {}", date, dateFormat.toPattern(), e);
        }
        return null;
    }

    /**
     * Add one or more months to a specific date
     *
     * @param date
     * @param months
     * @return
     */
    public static Date addMonths(Date date, int months) {
        return DateUtils.addMonths(date, months);
    }

    /**
     * Add one or more days to a specific date
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        return DateUtils.addDays(date, days);
    }
    
    public static Date getDateFromStr(String start) {
        Date startDate = null;
        if (Strings.isNullOrEmpty(start)) {
            return null;
        }
        if (start.length() == 10) { //yyyy-MM-dd
            startDate = DateUtil.getDayStart(DateFormatter.fromFormattedString(start, DateFormatter.DATE_FORMAT));
        } else if (start.length() == 19){//yyyy-MM-dd HH:mm:dd
            startDate = DateFormatter.fromFormattedString(start, DateFormatter.DATE_TIME);
        }
        return startDate;
    }

}
