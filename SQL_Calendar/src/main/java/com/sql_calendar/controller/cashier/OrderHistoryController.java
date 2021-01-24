package com.sql_calendar.controller.cashier;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sql_calendar.resources.Employee;
import com.sql_calendar.resources.Order;
import com.sql_calendar.util.GetRequestModel;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// F1fDUcwlZLk

public class OrderHistoryController implements Initializable {
	private Employee user;

    @FXML 
    VBox view;
    
    ArrayList<HBox> hboxList;

    // setUser = take User (essn...) table
    public void setUser(Employee user) {
        this.user = user;
        System.out.println(this.user);
    }

    // run initialize (load initScreen)
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	QueryInfoToHBox();
    	    	
    	System.out.println("Order History");
//        initScreen();
    }
    

	 // render HBox + make request from server
	    private void QueryInfoToHBox() {
	    	Thread makeRequest = new Thread(new Runnable() {
	 			@Override
	 			public void run() {
	 				Platform.runLater(new Runnable() {
	                     public void run() {
							GetRequestModel request = new GetRequestModel();
 						
	 						// search parameter for HBox
	 						// Order(orderID, date, time, name, total)
	 						String parameter="total=all"; 
	 						
	 						// GET request -all order item 
					        ArrayList<Order> list = request.makeRequest("/cashier/order/all", Order.class,parameter);
	 					        
	 					    // run loop for displaying each item name on Popup scene 
	 				        for (Order data : list) {
	 				        	System.out.println(data);
	 					       	String orderDateTime = data.getTime() + " " + data.getDate();
	 					      	String totalPrice = data.getTotal();
	 				        	renderHBoxOrderHistory(orderDateTime,totalPrice);
	 					    }
	                     }
	 				});
	 			}
	 	    });
	 	    // start Request
	 	    makeRequest.start();
		}

    
// renderHBoxOrderHistory
    public void renderHBoxOrderHistory(String orderDateTime, String totalPrice) {	
 			 			
 			// load HBox (into Cashing UI) 
 			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../cashier/hboxHistory.fxml"));
 			try {
 				HBox record = loader.load();
 				
 				// load HBox Controller
 				HBoxOrderHistoryController controller = loader.getController();
 				
 				// set the selected Date+Time, Total already query 
 				controller.setHBoxTag(orderDateTime, totalPrice);
	 	        
 		        // add the new HBox = record (already insert info) into parentVBox (view) in Cashing 
 				view.getChildren().add(record);
 				
 	 	        
 			} catch (IOException e) {
 				e.printStackTrace();
 			}
 		}
}


