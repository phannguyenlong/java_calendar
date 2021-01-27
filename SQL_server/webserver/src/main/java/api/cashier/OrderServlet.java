package api.cashier;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import util.DatabaseConnect;

@WebServlet("/cashier/order/*")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = null;

        if (req.getRequestURI().equals("/webserver/cashier/order/all")) {
            query = "SELECT orderID, date, time, (lname + ' ' + fname) as name, ISNULL(totalPrice, 0) as total\r\n"
                    + "FROM orderList o\r\n" + "	JOIN employee e on (o.essn = e.ssn)\r\n" + "ORDER BY date DESC, time DESC;";
        } else if (req.getRequestURI().equals("/webserver/cashier/order")) {
            String orderID = req.getParameter("orderID");
            query = "SELECT o.itemID, o.quantity, i.price FROM orderItem o\r\n"
                    + "	JOIN item i on (o.itemID = i.itemID)\r\n" + "WHERE o.orderID = " + orderID + ";";
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().equals("/webserver/cashier/order/new")) {
            String date = req.getParameter("date");
            String time = req.getParameter("time");
            String essn = req.getParameter("essn");
            String listOrderItem = req.getParameter("data");

            System.out.println(listOrderItem);

            String query = "INSERT INTO orderList (date, time, essn)\r\n" + "VALUES ('" + date + "', '" + time + "', '"
                    + essn + "');\r\n";

            query += "declare @temp TINYINT; SET @temp = (SELECT max(orderID) from orderList);\r\n";

            try {
                DatabaseConnect DB = new DatabaseConnect();
                ArrayList<JsonNode> list = DB.JsonToJsonNode(listOrderItem);
                for (JsonNode node : list) {
                    String itemID = node.path("itemID").textValue();
                    String quantity = node.path("quantity").textValue();
                    query += "INSERT INTO orderItem(orderID, itemID, quantity) VALUES (@temp, '" + itemID + "', '"
                            + quantity + "');\r\n";
                }

                query += "UPDATE orderList\r\n" + "SET totalPrice = magic_table.total\r\n" + "FROM orderList\r\n"
                        + "JOIN (\r\n" + "	SELECT o.orderID, sum(o.quantity * i.price) as total\r\n"
                        + "	FROM orderItem o\r\n" + "	JOIN item i\r\n" + "		ON o.itemID = i.itemID\r\n"
                        + "		GROUP BY orderID\r\n" + "	HAVING orderID = @temp\r\n" + ") magic_table\r\n"
                        + "ON orderList.orderID = @temp;";

                System.out.println(query);

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
    }
}
