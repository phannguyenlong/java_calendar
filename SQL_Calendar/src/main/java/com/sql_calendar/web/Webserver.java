package com.sql_calendar.web;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class Webserver extends Thread {
    private ServerSocket serverSocket;
    private int port = 8080;

    public Webserver() throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }
    
    public void run() {
        while (true) {
            try {
                System.out.println("Mailing for client on port: " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                System.out.println("Just connected to: " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());

                System.out.println(in.readUTF());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());

                out.writeUTF("Thank for connecting to " + server.getLocalAddress() + "\nGoodbye");

                server.close();
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            Thread t = new Webserver();
            t.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}   
