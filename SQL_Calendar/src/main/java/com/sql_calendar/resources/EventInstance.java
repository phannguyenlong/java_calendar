package com.sql_calendar.resources;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EventInstance {
    private String essn;
    private int eventID;
    private String eventName;
    private Date startDate;
    private Date endDate;
    private Date date;
    private String startTime;
    private String endTime;
    private String name;
    private String phone;
    private String sex;
    private String type;
    private String eventType;
    private String status;

    public EventInstance deepCopy() {
        EventInstance p = new EventInstance();
        p.setEssn(essn);
        p.setEventID(eventID);
        p.setEventName(eventName);
        p.setStartDate(startDate);
        p.setDate(date);
        p.setStartTime(startTime);
        p.setEndTime(endTime);
        p.setName(name);
        p.setPhone(phone);
        p.setSex(sex);
        p.setType(type);
        p.setEventType(eventType);
        p.setStatus(status);
        return p;
    }

    public String getEssn() {
        return essn;
    }

    public void setEssn(String essn) {
        this.essn = essn;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public EventInstance makeSampleEvent() {
        EventInstance e = new EventInstance();
        e.setStartDate(this.getStartDate());
        e.setStartTime(this.getStartTime());
        e.setEndTime(this.getEndTime());
        e.setEventID(this.getEventID());
        e.setEventName(this.getEventName());
        e.setEventType(this.getEventType());

        return e;
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
