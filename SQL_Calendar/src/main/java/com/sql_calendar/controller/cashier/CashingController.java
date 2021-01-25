package com.sql_calendar.controller.cashier;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sql_calendar.resources.Employee;
import com.sql_calendar.resources.OrderItem;
import com.sql_calendar.util.PostRequestModel;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CashingController implements Initializable {
    private Employee user;
    public Stage stage;
    double sum;
    
	ArrayList<OrderItem> order = new ArrayList<>();

    @FXML 
    VBox box;
	@FXML
	Label total;
	
	FXMLLoader loader;

	// Round total
	private static DecimalFormat df = new DecimalFormat("0.00");

	// setUser = take User table
    public void setUser(Employee user) {
        this.user = user;
        System.out.println(this.user);        
    }
    
    // run initialize (load initScreen)
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Cashing controller");
        initScreen();
    }

    // Generate Popup-New-Item scene = load popup + load ParentController + ParentVBox
	private void initScreen() {
	    loader = new FXMLLoader(getClass().getResource("../../cashier/popupNewItem.fxml"));
        stage = new Stage();
        stage.setTitle("New Event");
	    try {
	        stage.setScene(new Scene(loader.load()));
	        NewItemPopupController controller = loader.getController();
	        controller.setParentController(this);
	        controller.setParentVbox(box);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	// "New item" button Clicked = render Popup
	public void newItemButtonClick() {
		System.out.println("Code error!!!!!");
		stage.show();
		
	}
	
	// Total after "confirm" an item in Popup-New-Item (rounded decimal)
	public void changeTotal(double plussum) {
		sum += plussum;
		total.setText("Total: " + df.format(sum) + " euro");
	}
	
	// Total after Del an HBox item
	public void minusTotal(int quantity, double price) {
		sum -= quantity*price;
		sum = sum < 0 ? 0 : sum;
		total.setText("Total: " + df.format(sum) + " euro");
	};
	
	public void cancelButton() {
		sum = 0;
		total.setText("Total: " + df.format(sum) + " euro");
		((NewItemPopupController) loader.getController()).deleteAllItem();
		box.getChildren().clear();
	}
	
	public void confirmOrderButton() {
		DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");  
		LocalDateTime now = LocalDateTime.now();  
		String parameter = "date=" + date.format(now) + "&time=" + time.format(now) + "&essn=" + user.getSsn();
		System.out.println(date.format(now) + " " + time.format(now));
		order = ((NewItemPopupController) loader.getController()).getOrder();
		
		System.out.println(parameter);
		ArrayList<OrderItem> sendOrders = new ArrayList<>();
		for (OrderItem o : order) {
			sendOrders.add(new OrderItem(o.getItemID(), o.getQuantity()));
		}
		Thread makeRequest = new Thread(new Runnable() {
			@Override
			public void run() {
				PostRequestModel request = new PostRequestModel();
				System.out.println("SIZEW : "  + order.size());
				int res_code = request.makeRequest("/cashier/order/new", sendOrders , parameter);
				if (res_code == 200) {
		            System.out.println("Successfull make request");
		        } else {
		            System.out.println("Failed");
		        }
			}
	    });
		makeRequest.start();
		cancelButton();
	}
}