package com.sql_calendar.util;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
import com.fasterxml.jackson.databind.ObjectMapper;

// Other class
import com.sql_calendar.resources.Student;

public class PostRequestModel {
    private String default_path = "http://localhost:8080/webserver";

    public <T> int makeRequest(String path, T Object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Connecting to server");

        // Handle parameter
        String parameter = "name=" + URLEncoder.encode("Nguyen Long", "UTF-8");

        // Make connection
        URL url = new URL(default_path + path + "?" + parameter);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        String postData = "data=" + "[" + mapper.writeValueAsString(Object) + "]";

        // Config connection
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", "Java client");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
        out.write(postData);
        out.flush();

        int responseCode = conn.getResponseCode();

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

    public <T> int makeRequest(String path, ArrayList<T> list) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Connecting to server");

        // Handle parameter
        String parameter = "name=" + URLEncoder.encode("Nguyen Long", "UTF-8");

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

        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
        out.write(postData);
        out.flush();

        int responseCode = conn.getResponseCode();

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

    public static void main(String[] args) throws IOException {
        ArrayList<Student> list = new ArrayList<>();

        list.add(new Student(5, "long", "HCM"));
        list.add(new Student(6, "thao", "q9"));
        list.add(new Student(7, "duy", "Binh tho"));
        
        PostRequestModel resquest = new PostRequestModel();
        int res_code = resquest.makeRequest("/database", list);
        if (res_code == 200) {
            System.out.println("Successfull make request");
        } else {
            System.out.println("Failed");
        }
    }
}
