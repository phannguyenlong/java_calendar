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

@WebServlet(urlPatterns = "/manager/calendar/week")
public class WeekViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String date = req.getParameter("date");

        String query = "SET DATEFIRST 1;" +
            "SELECT " +
            "	essn, ev.eventID, ev.eventName, startDate, date, " +
            "	FORMAT(startTime, 'hh\\:mm') as startTime, " +
            "	FORMAT(endTime, 'hh\\:mm') as endTime, " +
            "	(fname + ' ' + lname) AS name, phone, sex, type, eventType, status\n" +
            "FROM eventInstance evI\n" +
            "JOIN employee e ON (evI.essn = e.ssn)\n" +
            "RIGHT JOIN event ev ON (evI.eventID = ev.eventID)\n" +
            "	WHERE DATEPART(week, '"+ date + "') = DATEPART(week, date)" +
            "	OR (ev.eventType = 'no repeat' AND DATEPART(week, '" + date + "') = DATEPART(week, ev.startDate))\n" +
            "	OR (ev.eventType = 'daily' AND '" + date + "' <= ev.endDate AND ev.startDate <= '" + date + "')\n" +
            "	OR (ev.eventType = 'weekly' AND dbo.GetLastDayWeek('" + date + "') <= ev.endDate AND ev.startDate <= dbo.GetFirstDayWeek('" + date + "'))";

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
