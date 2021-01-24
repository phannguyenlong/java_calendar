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
	HBox fuck;
	
	private VBox parentVbox;
	private CashingController parentController;


    public void setParentVbox(VBox box) {
    	this.parentVbox = box;
    }
    
    public void setParentController(CashingController parentController) {
        this.parentController = parentController;
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("order selected");
		
	}
	
	public void setTag(String productName, int quantity,double price) {
		System.out.println(price);
		itemTag.setText(productName);
		quantityTag.setText(String.valueOf(quantity));
		priceTag.setText(String.valueOf(price));
	}
	
	public void delete() {
		double sum = Integer.parseInt(quantityTag.getText()) * Double.parseDouble(priceTag.getText());
		parentController.minusTotal(sum);

		parentVbox.getChildren().remove(fuck);
		
	}
	
}
