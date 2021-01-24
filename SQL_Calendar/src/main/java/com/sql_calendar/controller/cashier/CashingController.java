package com.sql_calendar.controller.cashier;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import com.sql_calendar.resources.Employee;

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
    @FXML 
    VBox box;
	@FXML
	Label total;
	
	// Round total
	private static DecimalFormat df = new DecimalFormat("0.00");

	// setUser = take User (essn...) table
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
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("../../cashier/popupNewItem.fxml"));
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
//		System.out.print(user.getSsn());
		stage.show();
		
	}
	
	// Total after "confirm" an item in Popup-New-Item (rounded decimal)
	public void changeTotal(double plussum) {
		sum += plussum;
		total.setText("Total: " + df.format(sum) + " euro");
	}
	
	// Total after Del an HBox item
	public void minusTotal(double newSum) {
		sum -= newSum;
		if(sum<0) sum=0;
		total.setText("Total: " + df.format(sum) + " euro");
	};
	
	public void cancelButton() {
		sum = 0;
		total.setText("Total: " + df.format(sum) + " euro");
		box.getChildren().clear();
	}
	
	public void confirmOrderButton() {
		
	}
}