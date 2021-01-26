package com.sql_calendar.controller.cashier;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sql_calendar.resources.Item;
import com.sql_calendar.resources.OrderItem;
import com.sql_calendar.util.GetRequestModel;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Order History Popup Controller
 * 
 * @author Hai Yen Le, Phuong Hong Nguyen
 */

public class PopupOrderHistoryController implements Initializable {
    @FXML
    VBox moreinfopopup;
    @FXML
    Label totalHistory;
    @FXML
    Label dateHistory;
    @FXML
    Label cashierHistory;
    @FXML
    JFXButton backButton;

    // round decimal total
    private static DecimalFormat df = new DecimalFormat("0.00");

    public void initialize(URL location, ResourceBundle resources) {}

    // Make request to server
    public void renderPopupMoreInfo(String orderID) {
        Thread makeRequest = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        GetRequestModel request = new GetRequestModel();
                        ArrayList<Item> itemList = request.makeRequest("/cashier/item/all", Item.class, "itemName=all");
                        ArrayList<OrderItem> orderHistoryList = request.makeRequest("/cashier/order", OrderItem.class,
                                "orderID=" + orderID);
                        for (Item data : itemList) {
                            for (OrderItem dataHistory : orderHistoryList) {
                                if (data.getItemID().equals(dataHistory.getItemID())) {
                                    renderHBoxOrderInfo(data.getItemName(), dataHistory.getQuantity(),
                                            String.valueOf(df.format(Double.parseDouble(dataHistory.getPrice()))));
                                }
                            }
                        }
                    }
                });
            }
        });
        makeRequest.run();
    }

    // render specific Order Info 
    public void renderHBoxOrderInfo(String productOrder, String quantityOrder, String priceOrder) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../cashier/hboxOrder.fxml"));
        try {
            HBox OrderInfo = loader.load();
            HBoxOrderMoreInfoController controller = loader.getController();
            controller.setOrderInfo(productOrder, quantityOrder, priceOrder);
            moreinfopopup.getChildren().add(OrderInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Set Total, Date, Time, Cashier name
    public void setLabels(String total, String date, String cashierName) {
        totalHistory.setText("Total: " + total + " €");
        dateHistory.setText("Date: " + date);
        cashierHistory.setText("Cashier: " + cashierName);
    }

    // "Back" button
    public void handleBackButtonAction() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}