package com.sql_calendar.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Just a simple class for Testing
 */
public class Student {
    private int id;
    private String name;
    private String address;


    @JsonCreator
    public Student(
            @JsonProperty("id") int id, 
            @JsonProperty("name") String name, 
            @JsonProperty("address") String address) 
    {
        this.id = id;
        this.name = name;
        this.address = address;
    }
    
    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id + "|" + address + "|" + name;
    }
}
