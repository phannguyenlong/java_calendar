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
    @FXML 
    VBox box;
	@FXML
	Label total;
	
	private static DecimalFormat df = new DecimalFormat("0.00");

    public void setUser(Employee user) {
        this.user = user;
        System.out.println(this.user);        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Cashing controller");
        initScreen();
    }

    //Generate new screen for adding new event
	private void initScreen() {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("../../cashier/popupNewItem.fxml"));
        stage = new Stage();
        stage.setTitle("New Event");
	    try {
	        stage.setScene(new Scene(loader.load()));
//	        NewItemPopupController controller = loader.getController();
	        ((NewItemPopupController) loader.getController()).setParentController(this);
	        ((NewItemPopupController) loader.getController()).setParentVbox(box);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	//New item button has been clicked
	public void newItemButtonClick() {
		System.out.println("Code error!!!!!");
//		System.out.print(user.getSsn());
		stage.show();
		
	}
	
	//After comfirm an item
	public void changeTotal(double sum) {
		total.setText("Total: " + df.format(sum) + " euro");
	}
	
	//Delete an item
	public void minusTotal(double newSum) {
		String[] result = total.getText().split(" ");
		System.out.println("minus= "+result[1]);
		double sum = Double.parseDouble(result[1]) - newSum;
		System.out.println("newsum= "+sum);
		total.setText("Total: " + df.format(sum) + " euro");
	};
	
	}