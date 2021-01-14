package com.sql_calendar.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class use for making HTTP DELETE Request to server
 * @author Long Phan
 */
public class DeleteRequestModel {
    private String default_path = "http://localhost:8080/webserver";

    /**
     * Make HTTP DELETE Request with custom parameter
     * @param path
     * @param parameter
     * @return status code from server
     */
    public int makeRequest(String path, String parameter) {
        int responseCode = 500;
        try {
            URL url = new URL(default_path + path + "?" + parameter);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Config connection
            conn.setDoOutput(true);
            conn.setRequestMethod("DELETE");
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
        DeleteRequestModel resquest = new DeleteRequestModel();

        String parameter = "eventID=7";
        int res_code = resquest.makeRequest("/manager/calendar/event/action", parameter);
        if (res_code == 200) {
            System.out.println("Successfull make request");
        } else {
            System.out.println("Failed");
        }
    }
}
