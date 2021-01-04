package api.manager.calendar;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DatabaseConnect;

@WebServlet(urlPatterns = "/manager/calendar/event/action")
public class EventActionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eventName = req.getParameter("eventName");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String eventType = req.getParameter("eventType");

        String query = "INSERT INTO event (eventName, startDate, endDate, startTime, endTime, eventType)\r\n"
                + "VALUES ('" + eventName + "','" + startDate + "','" + endDate + "','" + startTime + "','" + endTime
                + "','" + eventType + "');";

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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String eventID = req.getParameter("eventID");

        String query = "DELETE FROM event WHERE eventID = " + eventID + ";";

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
