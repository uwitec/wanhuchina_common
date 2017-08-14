package com.wanhuchina.common.util.date;

/**
 * Created by 170244 on 2017/2/17.
 */
public class DatePattern {
    private String fullDate;
    private Integer year;
    private Integer month;
    private Integer day;

    public DatePattern() {
    }

    public DatePattern(String fullDate) {
        this.fullDate = fullDate;
    }

    public DatePattern splitFullDate() {
        DatePattern datePattern = new DatePattern();
        if (fullDate == null || "".equals(fullDate)) {
            return null;
        }

        if (fullDate.matches("^201[0-9]$")) {
            datePattern.year = Integer.parseInt(fullDate);
            return datePattern;
        } else if (fullDate.matches("^201[0-9](0[1-9]|1[0-2])$")) {
            datePattern.year = Integer.parseInt(fullDate.substring(0, 4));
            datePattern.month = Integer.parseInt(fullDate.substring(4, 6));
            return datePattern;
        } else if (fullDate.matches("^201[0-9](0[1-9]|1[0-2])([0-2][0-9]|[3][0-1])$")) {
            datePattern.year = Integer.parseInt(fullDate.substring(0, 4));
            datePattern.month = Integer.parseInt(fullDate.substring(4, 6));
            datePattern.day = Integer.parseInt(fullDate.substring(6, 8));
            return datePattern;
        }
        {
            return null;
        }
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getFullDate() {
        return fullDate;
    }
}
