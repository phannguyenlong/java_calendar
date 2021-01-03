package api.manager;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import util.DatabaseConnect;

@WebServlet(urlPatterns = "/manager/employee")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String query;

        if (name.equals("all")) {
            query = "SELECT * FROM Employee";
        } else {
            query = "SELECT ssn, (fname + ' ' + lname) as name, bdate, address, sex, type, phone\n" + "FROM employee\n"
                    + "WHERE fname + ' '  + lname LIKE '%" + name + "%';";
            ;
        }

        System.out.println(query);

        try {
            DatabaseConnect DB = new DatabaseConnect();
            DB.getConnection();
            ResultSet res = DB.doQuery(query);

            List<Map<String, Object>> json_resp = DB.ResultSetToJSON(res);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(resp.getOutputStream(), json_resp);

            DB.closeConnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
