package com.sql_calendar.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Object to receive query for Order Item in database
 * 
 * @author Vinh Nguyen
 */
public class OrderItem {
    private String itemID;
    private String quantity;
    private String price;

    public OrderItem(String itemID, String quantity) {
        this.itemID = itemID;
        this.quantity = quantity;
    }

    @JsonCreator
    public OrderItem(
            @JsonProperty("itemID") String itemID,
            @JsonProperty("quantity") String quantity,
            @JsonProperty("price") String price) {
        this.itemID = itemID;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
