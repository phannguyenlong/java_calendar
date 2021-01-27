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
            query = "SELECT ssn, (fname + ' ' + lname) as name, bdate, address, sex, type, phone FROM Employee";
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ssn = req.getParameter("ssn");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String type = req.getParameter("type");

        String query = "UPDATE employee SET address = '" + address + "', phone = '" + phone + "', type = '" + type
                + "' WHERE ssn = '" + ssn + "';";

        System.out.println(query);

        try {
            DatabaseConnect DB = new DatabaseConnect();
            DB.getConnection();
            DB.doQuery(query);

            DB.closeConnect();
        } catch (Exception e) {
            if (e.getLocalizedMessage().equals("The statement did not return a result set.")) {
                resp.setStatus(200);
            } else {
                resp.setStatus(500);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ssn = req.getParameter("ssn");
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");
        String address = req.getParameter("address");
        String bdate = req.getParameter("bdate");
        String phone = req.getParameter("phone");
        String type = req.getParameter("type");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String sex = req.getParameter("sex");

        String query = "INSERT INTO employee (ssn, fname, lname, bdate, address, sex, phone, type, username, password)"
                + "VALUES ('" + ssn + "','" + fname + "','" + lname + "','" + bdate + "','" + address + "','" + sex
                + "','" + phone + "','" + type + "','" + username + "','" + password + "');";

        System.out.println(query);

        try {
            DatabaseConnect DB = new DatabaseConnect();
            DB.getConnection();
            DB.doQuery(query);

            DB.closeConnect();
        } catch (Exception e) {
            if (e.getLocalizedMessage().equals("The statement did not return a result set.")) {
                resp.setStatus(200);
            } else {
                resp.setStatus(500);
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ssn = req.getParameter("ssn");

        String query = "DELETE FROM employee WHERE ssn = '" + ssn + "';";

        System.out.println(query);

        try {
            DatabaseConnect DB = new DatabaseConnect();
            DB.getConnection();
            DB.doQuery(query);

            DB.closeConnect();
        } catch (Exception e) {
            if (e.getLocalizedMessage().equals("The statement did not return a result set.")) {
                resp.setStatus(200);
            } else {
                resp.setStatus(500);
            }
        }
    }
}
