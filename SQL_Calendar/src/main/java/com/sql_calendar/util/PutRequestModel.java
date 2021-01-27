package com.sql_calendar.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class for make HTTP PUT resquest to server
 * @author Long Phan
 */
public class PutRequestModel {
    private String default_path = "http://localhost:8080/webserver";

    public PutRequestModel() {
        Properties props = new Properties();
        InputStream input = getClass().getResourceAsStream("../config.properties");
        
        try {
            props.load(input);
            this.default_path = props.getProperty("server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Make request with custom postData
     * @param path
     * @param postData
     * @return status code from server
     */
    public int makeRequest(String path, String postData) {
        int responseCode = 500;
        try {
            URL url = new URL(default_path + path + "?" + postData);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Config connection
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("User-Agent", "Java client");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Response code
            responseCode = conn.getResponseCode();

            // Read reply from server
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            System.out.println("Message recieved from server: ");
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    renderErrorBox(e);
                }
            });
            e.printStackTrace();
        }

        return responseCode;
    }

    public void renderErrorBox(Exception e) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("There is an error when connecting to server");
        alert.setContentText("Error: " + e.getMessage());

        alert.showAndWait();
    }

    public static void main(String[] args) {
        PutRequestModel resquest = new PutRequestModel();

        String parameter = "date=12/29/2020&essn=504-06-2817&eventID=1&status=present";
        int res_code = resquest.makeRequest("/manager/calendar/day/shift", parameter);
        if (res_code == 200) {
            System.out.println("Successfull make request");
        } else {
            System.out.println("Failed");
        }
    }

}
