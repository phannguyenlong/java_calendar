package api;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;

// Others
import util.DatabaseConnect;

@WebServlet(urlPatterns = "/database")
public class DatabaseServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private static String DB_URL = "jdbc:sqlserver://localhost:1433;";
    private static String Db_Name = "Test";
    private static String USER_NAME = "sa";
    private static String PASSWORD = "123";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Run database");

        // String requestUrl = req.getRequestURI();
        // String name = requestUrl.substring("/database/".length());

        System.out.println(req.getParameter("name"));

        try {
            DatabaseConnect DB = new DatabaseConnect(DB_URL, Db_Name, USER_NAME, PASSWORD);
            DB.getConnection();
            ResultSet res = DB.doQuery("select * from student");
            // Display data
            // while (res.next()) {
            //     resp.getOutputStream().println(res.getInt(1) + "  " + res.getString(2) + "  " + res.getString(3));
            // }
            List<Map<String, Object>> json_resp = DB.ResultSetToJSON(res);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(resp.getOutputStream(), json_resp);
            

            DB.closeConnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        resp.getOutputStream().print("hello from database");
    }
}
