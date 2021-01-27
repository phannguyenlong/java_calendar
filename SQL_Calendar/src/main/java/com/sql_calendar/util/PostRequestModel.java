package com.sql_calendar.util;

import java.net.*;
import java.util.ArrayList;
import java.util.Properties;
import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sql_calendar.resources.OrderItem;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class use for make HTTP /POST request to server Call makeRequest to use
 * (can recieve ArrayList of Object or single Object)
 * 
 * @author Long Phan
 */
public class PostRequestModel {
    private String default_path = "http://localhost:8080/webserver";

    public PostRequestModel() {
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
     * Make POST request to server with single Object
     * 
     * @param <T>    Any class in src/resources
     * @param path   api path
     * @param Object Object need to pass to server
     * @return status code response from server
     * @throws IOException
     */
    public <T> int makeRequest(String path, T Object) {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Connecting to server");
        int responseCode = 500;

        try {
            // Handle parameter
            String parameter = "name=" + URLEncoder.encode("Nguyen Long", "UTF-8");

            // Make connection
            URL url = new URL(default_path + path + "?" + parameter);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Handle post data
            String postData = "data=" + "[" + mapper.writeValueAsString(Object) + "]";

            // Config connection
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Java client");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Send post data to server
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(postData);
            out.flush();
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

    /**
     * Make POST request to server with List of Object
     * 
     * @param <T>  Object in src/resources
     * @param path api path
     * @param list List of Object need to pass to server
     * @return status code response from server
     */
    public <T> int makeRequest(String path, ArrayList<T> list, String parameter) {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Connecting to server");
        int responseCode = 500;

        try {
            // Make connection
            URL url = new URL(default_path + path + "?" + parameter);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Handle Post data
            String postData = "data=[";
            for (T Object : list) {
                postData += mapper.writeValueAsString(Object) + ",";
            }

            postData = new String(postData.substring(0, postData.length() - 1) + "]");

            // Config connection
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Java client");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Send post data to server
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(postData);
            out.flush();

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

    /**
     * Make POST request with custom post data
     * 
     * @param path
     * @param postData
     * @return
     */
    public int makeRequest(String path, String postData) {
        int responseCode = 500;
        try {
            URL url = new URL(default_path + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Config connection
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Java client");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Send post data to server
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(postData);
            out.flush();

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

    public static void main(String[] args) throws IOException {
        ArrayList<OrderItem> list = new ArrayList<>();
        PostRequestModel resquest = new PostRequestModel();

        list.add(new OrderItem("5", "1"));
        list.add(new OrderItem("6", "1"));
        list.add(new OrderItem("2", "2"));

        String parameter = "date=12/2/2020&time=7:00&essn=369-60-6431";
        int res_code = resquest.makeRequest("/cashier/order/new", list, parameter);
        if (res_code == 200) {
            System.out.println("Successfull make request");
        } else {
            System.out.println("Failed");
        }
    }
}
