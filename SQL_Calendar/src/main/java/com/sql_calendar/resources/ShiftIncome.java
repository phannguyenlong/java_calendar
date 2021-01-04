package com.sql_calendar.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ShiftIncome {
    String shiftIncome;

    public String getShiftIncome() {
        return shiftIncome;
    }

    public void setShiftIncome(String shiftIncome) {
        this.shiftIncome = shiftIncome;
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
