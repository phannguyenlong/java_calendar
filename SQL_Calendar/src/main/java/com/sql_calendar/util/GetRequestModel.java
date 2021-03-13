package com.sql_calendar.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sql_calendar.resources.Employee;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The class is use for make HTTP /GET request to the server Call makeRequest
 * function to use
 * 
 * @author Long Phan
 */
public class GetRequestModel {
    private String default_path;

    public GetRequestModel() {
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
     * Use for make a request
     * 
     * @param <T>   All type in src/resouces
     * @param path  api path
     * @param clazz Class type of Object, ex: Student.class
     * @return Arraylist<T>
     * @throws IOException
     */
    public <T> ArrayList<T> makeRequest(String path, Class<T> clazz, String parameter) {
        ArrayList<T> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("New Get Request was created");
        HttpURLConnection conn = null;

        // Make connection
        try {
            URL url = new URL(this.default_path + path + "?" + parameter);
            conn = (HttpURLConnection) url.openConnection();
            // Config Connection
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Java client");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        } catch (IOException e) {
            Platform.runLater(new Runnable(){
                public void run() { renderErrorBox(e); };
            });
            e.printStackTrace();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            System.out.println("Message recieved from server: ");
            inputLine = in.readLine();

            if (!inputLine.equals("[]")) {
                String[] jsons = new String(inputLine.substring(1, inputLine.length() - 1)).split("},");

                for (int i = 0; i < jsons.length - 1; i++)
                    jsons[i] = jsons[i] + "}";

                for (String json : jsons)
                    list.add(mapper.readValue(json, clazz));
            }
            in.close();
        } catch (IOException e) {
            Platform.runLater(new Runnable() {
                public void run() { renderErrorBox(e); };
            });
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return list;
    }

    public void renderErrorBox(Exception e) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("There is an error when connecting to server");
        alert.setContentText("Error: " + e.getMessage());

        alert.showAndWait();
    }

    public static void main(String[] args) throws IOException {
        GetRequestModel resquest = new GetRequestModel();

        String parameter = "name=all";
        ArrayList<Employee> res = resquest.makeRequest("/manager/employee", Employee.class, parameter);
        for (Employee std : res) {
            System.out.println(std);
        }

        if (res.isEmpty()) { // if list return nothing
            System.out.println("Nothing here");
        }
    }
}
