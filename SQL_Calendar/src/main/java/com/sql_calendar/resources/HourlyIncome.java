package com.sql_calendar.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Object to receive query for Hourly income in database
 * 
 * @author Vinh Nguyen
 */
public class HourlyIncome {
    private String onHour;
    private String hourlyIncome;

    public String getOnHour() {
        return onHour;
    }

    public void setOnHour(String onHour) {
        this.onHour = onHour;
    }

    public String getHourlyIncome() {
        return hourlyIncome;
    }

    public void setHourlyIncome(String hourlyIncome) {
        this.hourlyIncome = hourlyIncome;
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
