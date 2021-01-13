package api.manager.calendar;

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

@WebServlet("/manager/calendar/month")
public class MonthViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String date = req.getParameter("date");

        String query = "SELECT * , SUM(temp.dayIncome) OVER (ORDER BY date) as monthIncome\n"
                + "FROM (\n"
                + "SELECT\n" 
	            + "date,\n" 
	            + "SUM(totalPrice) as dayIncome\n"
                + "FROM orderList\n"
                + "WHERE Month(date) = Month('" + date + "') AND\n"
                + "Year(date) = Year('" + date + "')\n"
	            + "GROUP BY date\n"
                +") AS temp;";

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
