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

@WebServlet(urlPatterns = "/manager/calendar/day/*")
public class DayViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String date = req.getParameter("date");
        String url = req.getRequestURI();
        String query = null;

        if (url.equals("/webserver/manager/calendar/day")) {
            query = "SET DATEFIRST 1;\r\n" + "SELECT \r\n" + "	essn, ev.eventID, ev.eventName, startDate, date, \r\n"
                    + "	FORMAT(startTime, 'hh\\:mm') as startTime, \r\n"
                    + "	FORMAT(endTime, 'hh\\:mm') as endTime, \r\n"
                    + "	(fname + ' ' + lname) AS name, phone, sex, type, eventType, status\r\n"
                    + "FROM eventInstance evI\r\n" + "JOIN employee e ON (evI.essn = e.ssn)\r\n"
                    + "RIGHT JOIN event ev ON (evI.eventID = ev.eventID)\r\n" + "	WHERE '" + date + "' = date\r\n"
                    + "	OR (ev.eventType = 'no repeat' AND '" + date + "' = ev.startDate)\r\n"
                    + "	OR (ev.eventType = 'daily' AND '" + date + "' <= ev.endDate AND ev.startDate <= '" + date
                    + "')\r\n" + "	OR (ev.eventType = 'weekly' \r\n" + "		AND DATEPART(dw, '" + date
                    + "') = DATEPART(dw, date) \r\n" + "		AND dbo.GetLastDayWeek('" + date
                    + "') <= ev.endDate\r\n" + "		AND ev.startDate <= dbo.GetFirstDayWeek('" + date + "'));\r\n";
        } else if (url.equals("/webserver/manager/calendar/day/hour")) {
            query = "SELECT DATEPART(hour,time) AS onHour,\r\n" + "       ISNULL(SUM(totalPrice),0) as hourlyIncome\r\n"
                    + "FROM orderList\r\n" + "GROUP BY CAST(date as date),\r\n" + "       DATEPART(hour,time)\r\n"
                    + "HAVING date = '" + date + "';\r\n";
        } else if (url.equals("/webserver/manager/calendar/day/shift")) {
            String startTime = req.getParameter("startTime");
            String endTime = req.getParameter("endTime");
            query = "SELECT SUM(totalPrice) as shiftIncome\r\n" + "FROM orderList\r\n" + "WHERE time <= '" + endTime
                    + "' AND time >= '" + startTime + "' AND date = '" + date + "'\r\n";
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
        System.out.println("Put is running");

        System.out.println(req.getRequestURI());
        if (req.getRequestURI().equals("/webserver/manager/calendar/day/shift")) {
            String essn = req.getParameter("essn");
            String eventID = req.getParameter("eventID");
            String date = req.getParameter("date");
            String status = req.getParameter("status");

            String query = "UPDATE eventInstance\r\n" + "SET status = '" + status + "'\r\n" + "WHERE essn = '" + essn
                    + "' AND eventID = '" + eventID + "' AND date = '" + date + "';\r\n";

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
}
