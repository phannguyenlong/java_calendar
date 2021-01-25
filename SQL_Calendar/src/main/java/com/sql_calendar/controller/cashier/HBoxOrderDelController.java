package com.sql_calendar.controller.cashier;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HBoxOrderDelController implements Initializable {
	@FXML
	Label itemTag;
	@FXML 
	Label quantityTag;
	@FXML 
	Label priceTag;
	@FXML
	HBox box;
	
	// Cashing VBox
	private VBox parentVbox;
	
	// call CashingController
	private CashingController parentController;
	
	private NewItemPopupController popupController;

	// setParentVbox
    public void setParentVbox(VBox box) {
    	this.parentVbox = box;
    }
    
    public void setPopupController(NewItemPopupController controller) {
    	this.popupController = controller;
    }
    
    // setParentController
    public void setParentController(CashingController parentController) {
        this.parentController = parentController;
    }
    
    // set initialize 
	@Override
	public void initialize(URL location, ResourceBundle resources) {}
	
	// setTag - take productName + quantity + price = as Text
	public void setTag(String productName, int quantity,double price) {
		itemTag.setText(productName);
		quantityTag.setText(String.valueOf(quantity));
		priceTag.setText(String.valueOf(price));
	}

	// "Delete" RED button Clicked
	public void delete() {
		// take quantity,price => deduce Total
		parentController.minusTotal(Integer.parseInt(quantityTag.getText()),Double.parseDouble(priceTag.getText()));
		// reduce quantity in list of items
		popupController.deleteItemFromOrderList(Integer.parseInt(quantityTag.getText()),itemTag.getText());
		// remove HBox item
		parentVbox.getChildren().remove(box);
	}
	
}
