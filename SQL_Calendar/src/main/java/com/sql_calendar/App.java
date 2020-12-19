package com.sql_calendar;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.io.*;

// Other Java Class

public class App {

    public static void main(String[] args) throws IOException{
        System.out.println("Connecting to server");
        URL url = new URL("http://localhost:8080/webserver/test");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        String postData = "name=" + URLEncoder.encode("Phan Nguyen Long", "UTF-8");

        // Config connection
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", "Java client");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
        out.write(postData);
        out.flush();

        int responseCode = conn.getResponseCode();
		if(responseCode == 200){
			System.out.println("POST was successful.");
		}
		else if(responseCode == 401){
			System.out.println("Wrong password.");
		}

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
    }
}