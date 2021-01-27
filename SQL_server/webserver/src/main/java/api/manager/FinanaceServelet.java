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

@WebServlet(urlPatterns = "/manager/finance/*")
public class FinanaceServelet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String date = req.getParameter("date");
        String url = req.getRequestURI();
        String query = null;

        if (url.equals("/webserver/manager/finance/day")) {
            query = "SELECT DATEPART(hour,time) AS number,\r\n" + " ISNULL(SUM(totalPrice),0) as total\r\n"
                    + "FROM orderList\r\n" + "GROUP BY CAST(date as date)," + " DATEPART(hour,time)\r\n"
                    + "HAVING date = '" + date + "';\r\n";
        } else if (url.equals("/webserver/manager/finance/week")) {
            query = "SET DATEFIRST 7;\r\n" + "SELECT DATEPART(dw, date) as number, SUM(totalPrice) as total\r\n" + "FROM orderList\r\n"
                    + "GROUP BY date\r\n" + "HAVING DATEPART(week, date) = DATEPART(week, '" + date + "');\r\n";
        } else if (url.equals("/webserver/manager/finance/month")) {
            query = "SELECT (DATEPART(week, date) - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,'" + date + "'), 0))+ 1) as number, SUM(TotalPrice) as total\r\n" + "FROM orderList\r\n"
                    + "WHERE date <= EOMONTH('" + date + "') AND date >= (CAST('" + date + "' as datetime)-DAY('" + date
                    + "')+1)\r\n" + "GROUP BY DATEPART(week, date);\r\n";
        } else {
            return;
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
