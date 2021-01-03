package com.sql_calendar.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MonthView {
    private String date;
    private String dayIncome;
    private String monthIncome;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayIncome() {
        return dayIncome;
    }

    public void setDayIncome(String dayIncome) {
        this.dayIncome = dayIncome;
    }

    public String getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(String monthIncome) {
        this.monthIncome = monthIncome;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
