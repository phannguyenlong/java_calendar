package com.sql_calendar.controller.manager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sql_calendar.resources.Employee;
import com.sql_calendar.util.GetRequestModel;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for Employee management Tab
 * @author Vinh Nguyen
 */
public class EmployeeManagementController implements Initializable {
	private Stage moreInfoScreen, addEmployeeScreen;
	private EmployeeInfoController moreInfoController;
	private ArrayList<Employee> datas = new ArrayList<>();
	@FXML
	VBox concu;
	@FXML
	JFXTextField searchconcu;
	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Employee management");
        initScreen();
    }

    // Handle Search function
    public void search() {
    	concu.getChildren().clear();
    	datas.clear();
    	Thread makeRequest = new Thread(new Runnable() {
    		@Override
            public void run() {
    	        GetRequestModel request = new GetRequestModel();
    	        
    	        datas = request.makeRequest("/manager/employee", Employee.class, "name=" + searchconcu.getText());
    	        Platform.runLater(new Runnable() {
					@Override
					public void run() {
						for(Employee data : datas) {
		    	        	HBox temp = new HBox();
		                    temp.setPadding(new Insets(11));
		                    temp.setStyle("-fx-background-color: #E9E9EA;-fx-background-radius: 11;");
		                    
		                    Label empName = new Label(data.getName() + " - " + data.getType().toUpperCase());
		                    empName.setPrefWidth(670);
		                    empName.setAlignment(Pos.CENTER_LEFT);
		                    empName.setStyle("-fx-font-size: 17;-fx-font-weight: bold");
		                    
		                    JFXButton btn = new JFXButton("More Info");
		                    btn.setPadding(new Insets(5));
		                    btn.setAlignment(Pos.CENTER_RIGHT);
		                    btn.setStyle("-fx-font-weight: bold;-fx-background-color: #009688;-fx-text-fill: white;-fx-font-size: 14;-fx-background-radius: 11;");
		                    btn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
		                    	@Override
		                        public void handle(ActionEvent event) {
		                            renderMoreInfo(data);
		                        };
		                    });
		                    
		                    temp.getChildren().addAll(empName, btn);
		                    concu.getChildren().add(temp);
		    	        }
					}	        	
    	        });
    		}
    	});
    	makeRequest.start();
    }
    
    private void renderMoreInfo(Employee e) {
    	moreInfoController.setUser(e);
    	moreInfoScreen.show();
    }
    
    public void handleNewEmployee() {
    	addEmployeeScreen.show();
    }
    
    // Generate new screen for adding new event
    private void initScreen() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../manager/moreInfoEmployee.fxml"));
        try {
        	moreInfoScreen = new Stage();
        	moreInfoScreen.setTitle("Employee Information");
        	moreInfoScreen.setScene(new Scene(loader.load(), 400, 280));
        	moreInfoController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        loader = new FXMLLoader(getClass().getResource("../../manager/newEmployeeBox.fxml"));
        try {
        	addEmployeeScreen = new Stage();
        	addEmployeeScreen.setTitle("Add New Employee");
        	addEmployeeScreen.setScene(new Scene(loader.load(), 450, 415.));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 
