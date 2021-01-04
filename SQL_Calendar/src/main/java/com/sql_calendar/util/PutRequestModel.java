package com.sql_calendar.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PutRequestModel {
    private String default_path = "http://localhost:8080/webserver";
    
    public int makeRequest(String path, String postData) throws IOException {
        URL url = new URL(default_path + path + "?" + postData);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Config connection
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
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
        PutRequestModel resquest = new PutRequestModel();

        String parameter = "date=12/29/2020&essn=504-06-2817&eventID=1&status=present";
        try {
            int res_code = resquest.makeRequest("/manager/calendar/day/shift", parameter);
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
