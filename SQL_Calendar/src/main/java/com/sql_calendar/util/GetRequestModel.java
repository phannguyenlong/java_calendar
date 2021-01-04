package com.sql_calendar.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sql_calendar.resources.Employee;
import com.sql_calendar.resources.EventInstance;
import com.sql_calendar.resources.HourlyIncome;
import com.sql_calendar.resources.Item;
import com.sql_calendar.resources.Order;
import com.sql_calendar.resources.OrderItem;
import com.sql_calendar.resources.ShiftIncome;

/**
 * The class is use for make HTTP /GET request to the server
 * Call makeRequest funciton to use
 * @author Long Phan
 */
public class GetRequestModel {
    private String default_path = "http://localhost:8080/webserver";

    /**
     * Use for make a request
     * @param <T> All type in src/resouces
     * @param path api path
     * @param clazz Class type of Object, ex: Student.class
     * @return Arraylist<T>
     * @throws IOException
     */
    public <T> ArrayList<T> makeRequest(String path, Class<T> clazz, String parameter) throws IOException {
        ArrayList<T> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("New Get Request was created");

        // Make connection
        URL url = new URL(this.default_path + path + "?" + parameter);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // Config Connection
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Java client");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            System.out.println("Message recieved from server: ");
            inputLine = in.readLine();

            // System.out.println(inputLine);
            if (!inputLine.equals("[]")) {
                String[] jsons = new String(inputLine.substring(1, inputLine.length() - 1)).split("},");

                for (int i = 0; i < jsons.length - 1; i++)
                    jsons[i] = jsons[i] + "}";

                for (String json : jsons)
                    list.add(mapper.readValue(json, clazz));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
            in.close();
        }

        return list;
    }
    
    public static void main(String[] args) throws IOException {
        GetRequestModel resquest = new GetRequestModel();
        
        String parameter = "orderID=116";
        ArrayList<OrderItem> res = resquest.makeRequest("/cashier/order", OrderItem.class, parameter);
        for (OrderItem std : res) {
            System.out.println(std);
        }

        if (res.isEmpty()) { // if list return nothing
            System.out.println("Nothing here");
        }
    }
}
