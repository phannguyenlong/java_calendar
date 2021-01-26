package com.sql_calendar.controller.cashier;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.sql_calendar.resources.Employee;
import com.sql_calendar.resources.Order;
import com.sql_calendar.util.GetRequestModel;
import com.sql_calendar.util.Tool;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Order History Tab Controller
 * 
 * @author Hai Yen Le, Phuong Hong Nguyen
 */

public class OrderHistoryController implements Initializable {
    private Employee user;
    String orderDateTime, totalPrice, orderID, cashierName;

    @FXML
    VBox view;

    // round decimal total
    private static DecimalFormat df = new DecimalFormat("0.00");

    // setUser
    public void setUser(Employee user) {
        this.user = user;
        System.out.println(this.user);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        QueryInfoToHBox();
        System.out.println("Order History");
    }

    // Make request to server
    public void QueryInfoToHBox() {
        Thread makeRequest = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        GetRequestModel request = new GetRequestModel();
                        ArrayList<Order> list = request.makeRequest("/cashier/order/all", Order.class, "total=all");
                        for (Order data : list) {
                            Date d = new Date(Long.parseLong(data.getDate()));
                            orderDateTime = data.getTime() + " " + Tool.convertDateToString(d);
                            totalPrice = String.valueOf(df.format(Double.parseDouble(data.getTotal())));
                            orderID = data.getOrderID();
                            cashierName = data.getName();
                            renderHBoxOrderHistory(orderDateTime, totalPrice, orderID, cashierName);
                        }
                    }
                });
            }
        });
        makeRequest.start();
    }

    // render all Orders
    public void renderHBoxOrderHistory(String orderDateTime, String totalPrice, String orderID, String cashierName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../cashier/hboxHistory.fxml"));
        try {
            HBox record = loader.load();
            HBoxOrderHistoryController controller = loader.getController();
            controller.setHBoxTag(orderDateTime, totalPrice);
            controller.getInfo(orderID, totalPrice, orderDateTime, cashierName);
            view.getChildren().add(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}