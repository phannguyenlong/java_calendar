package com.sql_calendar.controller.cashier;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sql_calendar.controller.manager.CalendarManagementController;
import com.sql_calendar.resources.Employee;
import com.sql_calendar.resources.EventInstance;
import com.sql_calendar.resources.Item;
import com.sql_calendar.util.GetRequestModel;
import com.sql_calendar.util.Tool;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewItemPopupController implements Initializable {
	String selectedItem;
	double price,sum;
	@FXML
	Label quantity;
	@FXML
	FlowPane flowPane;


	private CashingController parentController;
	private VBox parentVbox;

    public void setParentController(CashingController parentController) {
        this.parentController = parentController;
    }
    
    public void setParentVbox(VBox box) {
    	this.parentVbox = box;
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Cashing controller");
		quantity.setText("1");		
        renderPopupNewItem();
	}
	
    private void renderPopupNewItem() {
        Thread makeRequest = new Thread(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
                    public void run() {
						GetRequestModel request = new GetRequestModel();
						String parameter="itemName=all"; 
				        ArrayList<Item> list = request.makeRequest("/cashier/item/all", Item.class,parameter);
				        for (Item data : list) {
				        	JFXButton productName = new JFXButton(data.getItemName());
				        	productName.setStyle("-fx-background-color:  #7a7b6d;-fx-background-radius:20;-fx-text-fill: white;-fx-font-size: 12");
				        	productName.setPadding(new Insets(10));
				        	productName.setPrefSize(300, 20);
				            productName.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
				                @Override
				                public void handle(ActionEvent event) {
				            		selectedItem=productName.getText();
				            		for(Item data : list) {
				            			if(productName.getText()==data.getItemName()) {
				            				price = Double. parseDouble(data.getPrice());
				            			}
				            		}
				            		
				                }
				            });

				        	flowPane.getChildren().add(productName);
				        }
                    }
				});
			}
        });
        makeRequest.start();
	}
    
	public void minusButton() {
		int a = Integer.parseInt(quantity.getText()) - 1;
		a = a < 1 ? 1 : a;
		quantity.setText(String.valueOf(a));
	}
	
	public void plusButton() {
		int a = Integer.parseInt(quantity.getText()) + 1;
		quantity.setText(String.valueOf(a));	
	}
	
	public void confirmButton() {	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../cashier/hboxOrderDel.fxml"));
		try {
			HBox box = loader.load();
			HBoxOrderDelController controller = loader.getController();
	        controller.setTag(selectedItem, Integer.parseInt(quantity.getText()),price);
	        controller.setParentVbox(parentVbox);
	        controller.setParentController(parentController);
	        
			parentVbox.getChildren().add(box);
			sum+=price*Integer.parseInt(quantity.getText());
			parentController.changeTotal(sum);
			quantity.setText(String.valueOf(1));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}



