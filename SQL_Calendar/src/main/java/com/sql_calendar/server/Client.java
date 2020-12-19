package com.sql_calendar.server;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) {
        String serverName = "localhost";
        int port = 8080;

        try {
            System.out.println("Connecting to " + serverName + "on port" + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to: " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeUTF("Hello from " + client.getLocalAddress());
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server say: " + in.readUTF());
            client.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
