package com.sql_calendar.controller.cashier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sql_calendar.resources.Item;
import com.sql_calendar.util.GetRequestModel;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewItemPopupController implements Initializable {
	String selectedItem;
	double price;
	@FXML
	Label quantity;
	@FXML
	FlowPane flowPane;
	
	// call CashingController
	private CashingController parentController;
	
	// call parentVBox 
	private VBox parentVbox;

	// setParentController = take from CashingController
    public void setParentController(CashingController parentController) {
        this.parentController = parentController;
    }
    
    // setParentBox = take parentVBox
    public void setParentVbox(VBox box) {
    	this.parentVbox = box;
    }
    
    // run initialize + render Popup-New-Item + set initial quantity value "1" 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Cashing controller");
		quantity.setText("1");		
        renderPopupNewItem();
	}
	
	// render Popup + make request from server + 
    private void renderPopupNewItem() {
        Thread makeRequest = new Thread(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
                    public void run() {
						GetRequestModel request = new GetRequestModel();
											
						// search parameter all item name
						String parameter="itemName=all"; 
						
						// GET request -all order item 
				        ArrayList<Item> list = request.makeRequest("/cashier/item/all", Item.class,parameter);
				        
				        // run loop for displaying each item name on Popup scene 
				        for (Item data : list) {
				        	
				        	// manually create Button for product Item on Popup scene + getItemName
				        	JFXButton productName = new JFXButton(data.getItemName());
				        	productName.setStyle("-fx-background-color:  #7a7b6d;-fx-background-radius:20;-fx-text-fill: white;-fx-font-size: 12");
				        	productName.setPadding(new Insets(10));
				        	productName.setPrefSize(300, 20);
				        	
				        	// From Button clicked => query another parameter via user/our action
				            productName.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
				                
				            	@Override
				                public void handle(ActionEvent event) {
				            	
				            		// get String to compare in the list/table to get another info (price, in this case)
				            		selectedItem=productName.getText();
				            		
				            		// getPrice (type: double!!)
				            		for(Item data : list) {
				            			if(productName.getText()==data.getItemName()) {
				            				price = Double. parseDouble(data.getPrice());
				            			}
				            		}
				            		
				                }
				            });
				            // add productName into flowPane on the Popup
				        	flowPane.getChildren().add(productName);
				        }
                    }
				});
			}
        });
        
        // start Request
        makeRequest.start();
	}
    
    
    // quantity -1
	public void minusButton() {
		int a = Integer.parseInt(quantity.getText()) - 1;
		a = a < 1 ? 1 : a;
		quantity.setText(String.valueOf(a));
	}
	
	// quantity +1
	public void plusButton() {
		int a = Integer.parseInt(quantity.getText()) + 1;
		quantity.setText(String.valueOf(a));	
	}
	
	
	// "Confirm" button = after done with choosing Product + quantity
	public void confirmButton() {	
		
		// load HBox (into Cashing UI) 
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../cashier/hboxOrderDel.fxml"));
		try {
			HBox box = loader.load();
			
			// load HBox Controller
			HBoxOrderDelController controller = loader.getController();
	        
			// set the selectedProduct + selected quantity + price already query 
			controller.setTag(selectedItem, Integer.parseInt(quantity.getText()),price);
			
			// setParentVBox (where HBox will load into)
	        controller.setParentVbox(parentVbox);
	        
	        // setParentController (where this will get all function or any info from)
	        controller.setParentController(parentController);
	        
	        
	        // add the new Hbox = box (already insert info) into parentVBox in Cashing 
			parentVbox.getChildren().add(box);
			
			// print out "Total: ... euro" = in Cashing tab
			parentController.changeTotal(price*Integer.parseInt(quantity.getText()));
			
			// in order to reset the quantity to "1" => make the correct quantity info in new HBox 
			quantity.setText(String.valueOf(1));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


