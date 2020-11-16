package com.sql_calendar;

import java.sql.ResultSet;

// Other Java Class

public class App {
    private static String DB_URL = "jdbc:sqlserver://localhost:1433;";
    private static String Db_Name = "Test";
    private static String USER_NAME = "sa";
    private static String PASSWORD = "123";

    public static void main(String[] args) {
        try {
            DatabaseConnect DB = new DatabaseConnect(DB_URL, Db_Name, USER_NAME, PASSWORD);
            DB.getConnection();
            ResultSet res = DB.doQuery("select * from student");
            // Display data
            while (res.next()) {
                System.out.println(res.getInt(1) + "  " + res.getString(2) + "  " + res.getString(3));
            }

            DB.closeConnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}