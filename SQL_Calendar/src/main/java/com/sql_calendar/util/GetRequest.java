package com.sql_calendar.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sql_calendar.resources.Student;


/**
 * The class is use for make request to the server
 * Call makeRequest funciton to use
 * @author Long Phan
 */
public class GetRequest {
    private String default_path = "http://localhost:8080/webserver";

    /**
     * Use for make a request
     * @param <T> All type in src/resouces
     * @param path api path
     * @param clazz Class type of Object, ex: Student.class
     * @return Arraylist<T>
     * @throws IOException
     */
    public <T> ArrayList<T> makeRequest(String path, Class<T> clazz) throws IOException {
        ArrayList<T> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("New Get Request was created");
        URL url = new URL(this.default_path + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            System.out.println("Message recieved from server: ");
            inputLine = in.readLine();

            String[] jsons = new String(inputLine.substring(1, inputLine.length() - 1)).split("},");

            for (int i = 0; i < jsons.length - 1; i++) 
                jsons[i] = jsons[i] + "}";

            for (String json : jsons) 
                list.add(mapper.readValue(json, clazz));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
            in.close();
        }

        return list;
    }
    
    public static void main(String[] args) throws IOException {
        GetRequest resquest = new GetRequest();
        
        ArrayList<Student> res = resquest.makeRequest("/database", Student.class);
        for (Student std : res) {
            System.out.println(std);
        }
    }
}
