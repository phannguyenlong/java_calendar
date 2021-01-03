package com.sql_calendar.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteRequestModel {
    private String default_path = "http://localhost:8080/webserver";
    
    public int makeRequest(String path, String parameter) throws IOException {
        URL url = new URL(default_path + path + "?" + parameter);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Config connection
        conn.setDoOutput(true);
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("User-Agent", "Java client");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Response code
        int responseCode = conn.getResponseCode();

        // Read reply from server
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            System.out.println("Message recieved from server: ");
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
        } catch (Exception e) {
            System.out.println("Cannot connect to server");
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return responseCode;
    }
    
    public static void main(String[] args) {
        DeleteRequestModel resquest = new DeleteRequestModel();

        String parameter = "date=12/28/2020&ssn=765-59-1185&eventID=1";
        try {
            int res_code = resquest.makeRequest("/manager/calendar/event", parameter);
            if (res_code == 200) {
                System.out.println("Successfull make request");
            } else {
                System.out.println("Failed");
            }
        } catch (IOException e) {
            System.out.println("Error from server");
        }
    }
}
