package com.sql_calendar.controller.cashier;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Render all Items of specific Order inside Popup Frame
 * 
 * @author Hai Yen Le, Phuong Hong Nguyen
 */

public class HBoxOrderMoreInfoController implements Initializable {
	@FXML
	Label itemInfo;
	@FXML 
	Label quantityInfo;
	@FXML 
	Label priceInfo;
	@FXML
	HBox OrderInfo;
    
	public void initialize(URL location, ResourceBundle resources) {}
	
	// set product name + quantity + price
	public void setOrderInfo(String productOrder, String quantityOrder, String priceOrder) {
		itemInfo.setText(productOrder);
		quantityInfo.setText(quantityOrder);
		priceInfo.setText(priceOrder);
	}
}