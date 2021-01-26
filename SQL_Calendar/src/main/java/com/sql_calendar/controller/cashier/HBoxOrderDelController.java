package com.sql_calendar.controller.cashier;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Render all items added to the Order
 * 
 * @author Hai Yen Le, Phuong Hong Nguyen
 */

public class HBoxOrderDelController implements Initializable {
    @FXML
    Label itemTag;
    @FXML
    Label quantityTag;
    @FXML
    Label priceTag;
    @FXML
    HBox box;

    private VBox parentVbox;
    private CashingController parentController;
    private NewItemPopupController popupController;

    public void setParentVbox(VBox box) {
        this.parentVbox = box;
    }

    public void setPopupController(NewItemPopupController controller) {
        this.popupController = controller;
    }

    public void setParentController(CashingController parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    // set product name + quantity + price
    public void setTag(String productName, int quantity, double price) {
        itemTag.setText(productName);
        quantityTag.setText(String.valueOf(quantity));
        priceTag.setText(String.valueOf(price));
    }

    // "Delete" red button is clicked
    public void delete() {
        parentController.minusTotal(Integer.parseInt(quantityTag.getText()), Double.parseDouble(priceTag.getText()));
        popupController.deleteItemFromOrderList(Integer.parseInt(quantityTag.getText()), itemTag.getText());
        parentVbox.getChildren().remove(box);
    }
}
