package com.sql_calendar.controller.cashier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sql_calendar.resources.Item;
import com.sql_calendar.resources.Order;
import com.sql_calendar.util.GetRequestModel;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HBoxOrderHistoryController implements Initializable {
	public Stage stage;
	
	@FXML
	Label orderTag;
	@FXML 
	Label totalTag;
	@FXML
	HBox record;
	
    // set initialize 
    @Override
	public void initialize(URL location, ResourceBundle resources) {
//    	load popupOrderHistory
    	popupOrderHistory();
		System.out.println("Order History tab");
	}
	
	
     // Generate Popup-Order-History scene = load popup + load ParentController + ParentVBox
 		private void popupOrderHistory() {
 		    FXMLLoader loader = new FXMLLoader(getClass().getResource("../../cashier/popupOrderHistory.fxml"));
 	        stage = new Stage();
 	        stage.setTitle("Order History");
 		    try {
 		        stage.setScene(new Scene(loader.load()));
 		    } catch (IOException e) {
 		        e.printStackTrace();
 		    }
 		}
	
 		// "More" red button Clicked = render Popup
 		public void more() {
 			System.out.println("Button More Clicked!!!!!");
 			stage.show();
 		}
 		
 		
	// setTag - take productName + quantity + price = as Text
	public void setHBoxTag(String period, String total) {
		orderTag.setText(period);
		totalTag.setText(total);
		System.out.println("Hiiii");
		
	}
	
		
}