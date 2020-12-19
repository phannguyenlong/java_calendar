package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DatabaseConnect {
    String dbURL;
    String Db_Name;
    String userName;
    String password;
    Connection conn;

    public DatabaseConnect(String dbURL, String Db_Name, String userName, String password) {
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

    public List<Map<String, Object>> ResultSetToJSON(ResultSet rs) throws SQLException {
        List<Map<String, Object>> rows = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        while (rs.next()) {
            // Represent a row in DB. Key: Column name, Value: Column value
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String colName = rsmd.getColumnName(i);
                Object colVal = rs.getObject(i);
                row.put(colName, colVal);
            }
            rows.add(row);
        }

        return rows;
    }
}
