package com.sql_calendar.util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 * This class use to store some function in order to deal with data
 * @author Long Phan
 */
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

    public static String convertDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(date);
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

    public static Date getFirstDayofWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        return c.getTime();
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

    public static Date getFirstDayofNextMonth(Date date) {
        Calendar today = Calendar.getInstance();
        today.clear();
        today.setTime(date);
        Calendar next = Calendar.getInstance();
        next.clear();
        next.set(Calendar.YEAR, today.get(Calendar.YEAR));
        next.set(Calendar.MONTH, today.get(Calendar.MONTH) + 1);
        next.set(Calendar.DAY_OF_MONTH, 1); // optional, default: 1, our need
        return next.getTime();
    }

    public static Date getFirstDayofPrevMonth(Date date) {
        Calendar today = Calendar.getInstance();
        today.clear();
        today.setTime(date);
        Calendar next = Calendar.getInstance();
        next.clear();
        next.set(Calendar.YEAR, today.get(Calendar.YEAR));
        next.set(Calendar.MONTH, today.get(Calendar.MONTH) - 1);
        next.set(Calendar.DAY_OF_MONTH, 1); // optional, default: 1, our need
        return next.getTime();
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

    public static String getMonthName(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    public static Time convertStringToTime(String t) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            return new Time(formatter.parse(t).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Use for add or subtract x day
     * @param x number of day
     * @param option 1 for plus, 0 for minus
     */
    public static Date plusOrMinusDay(Date date, int x, int option) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        x = option == 1 ? x : -x;
        c.add(Calendar.DATE, x);
        return c.getTime();
    }

    public static boolean isSameWeek(Date date1, Date date2) {
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(date1);
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date2);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }

    public static double converTimeStringToDouble(String t) {
        String[] temp = t.split(":");
        double m = Double.parseDouble(temp[1]) / 60;
        return Double.parseDouble(temp[0]) + m;
    }

    /**
     * t1 - t2
     * @param t1
     * @param t2
     * @return t1 - t2
     */
    public static double compare2Time(Time t1, Time t2) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String time1 = df.format(t1.getTime());
        String time2 = df.format(t2.getTime());
        return converTimeStringToDouble(time1) - converTimeStringToDouble(time2);
    }

    public static void main(String[] args) {
        System.out.println(compare2Time(Time.valueOf(LocalTime.now()), convertStringToTime("23:00")));
    }
}
