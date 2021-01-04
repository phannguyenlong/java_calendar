package api.manager.calendar;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DatabaseConnect;

@WebServlet(urlPatterns = "/manager/calendar/event")
public class EventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ssn = req.getParameter("ssn");
        String eventID = req.getParameter("eventID");
        String date = req.getParameter("date");

        String query = "insert into eventInstance (essn, eventID, date, status) " + "values ('" + ssn + "', " + eventID
                + ", '" + date + "', NULL);";

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
        String eventID = req.getParameter("eventID");
        String date = req.getParameter("date");

        String query = "DELETE FROM eventInstance WHERE " + "essn = '" + ssn + "' AND eventID = " + eventID
                + " AND date = '" + date + "';";

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
