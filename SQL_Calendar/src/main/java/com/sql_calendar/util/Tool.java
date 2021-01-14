package com.sql_calendar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tool {
    public static Date convertStringtoDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Date getFirstDateOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static int getDayofWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }
    
    public static Date getLastDateOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static int getNumberOfDayInMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekofMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * This will return int array (day, month, year) of object date
     * @param date Object day
     */
    public static int[] getDayMonthYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int[] arr = { c.get(Calendar.DATE), c.get(Calendar.MONTH), c.get(Calendar.YEAR) };
        return arr;
    }
}
