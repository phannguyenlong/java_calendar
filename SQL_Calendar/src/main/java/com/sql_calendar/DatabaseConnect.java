package com.sql_calendar;

import java.sql.Connection;
import java.sql.Statement;
// import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class DatabaseConnect {
    String dbURL;
    String Db_Name;
    String userName;
    String password;
    Connection conn;
    
    DatabaseConnect(String dbURL, String Db_Name, String userName, String password) {
        this.dbURL = dbURL;
        this.Db_Name = Db_Name;
        this.userName = userName;
        this.password = password;

        // this.conn = getConnection();
    }

    public Connection getConnection() {
        conn = null;
        dbURL = dbURL + "databaseName=" + Db_Name;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(dbURL, userName, password);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }

    public ResultSet doQuery(String query) throws Exception {
        Statement stmt = conn.createStatement();
        // execute query
        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }
    
    public void closeConnect() throws Exception {
        conn.close();
    }
}
