package com.sql_calendar.controller.cashier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Render all Orders inside Order History Frame
 * 
 * @author Hai Yen Le, Phuong Hong Nguyen
 */

public class HBoxOrderHistoryController implements Initializable {
    public Stage stage;
    String orderIDValue, totalPrice, orderDateTime, cashierName;

    @FXML
    Label orderTag;
    @FXML
    Label totalTag;
    @FXML
    HBox record;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    // Generate More Info Popup window
    public void popupOrderHistory(String orderID, String total, String date, String cashierName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../cashier/popupOrderHistory.fxml"));
        stage = new Stage();
        stage.setTitle("Order History");
        try {
            stage.setScene(new Scene(loader.load()));
            PopupOrderHistoryController controller = loader.getController();
            controller.renderPopupMoreInfo(orderID);
            controller.setLabels(total, date, cashierName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getInfo(String ordID, String sumPrice, String dateTime, String name) {
        orderIDValue = ordID;
        totalPrice = sumPrice;
        orderDateTime = dateTime;
        cashierName = name;
    }

    // "More" button
    public void more() {
        popupOrderHistory(orderIDValue, totalPrice, orderDateTime, cashierName);
        stage.show();
    }

    // Set HBox info
    public void setHBoxTag(String period, String total) {
        orderTag.setText(period);
        totalTag.setText(total);
    }

}