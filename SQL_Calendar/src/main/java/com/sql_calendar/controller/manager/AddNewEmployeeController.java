package com.sql_calendar.controller.manager;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sql_calendar.util.PostRequestModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

public class AddNewEmployeeController implements Initializable {
	@FXML
	JFXTextField username, firstname, lastname, address, phone, ssn;
	@FXML
	JFXPasswordField password;
	@FXML
	JFXComboBox<Label> type, sex;
	@FXML
	DatePicker birthday;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		type.getItems().add(new Label("staff"));
		type.getItems().add(new Label("cashier"));
		type.getItems().add(new Label("manager"));
		type.getSelectionModel().selectFirst();

		sex.getItems().add(new Label("M"));
		sex.getItems().add(new Label("F"));
		sex.getSelectionModel().selectFirst();
	}

	private boolean validate() {
		// Validate
		boolean isValid = true;
		Parent[] fields = { username, firstname, lastname, address, phone, ssn, password, birthday };
		String regex = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
		Pattern pattern = Pattern.compile(regex);
		// set to normal background
		for (Parent node : fields)
			node.setStyle("-fx-background-color: transparent");
		// Validate if null ==> set background red
		for (Parent node : fields) {
			if (node != birthday && node != password) {
				if (((JFXTextField) node).getText().equals("")) {
					isValid = false;
					node.setStyle("-fx-background-color: #FF584D");
				}
			} else if (node != birthday) {
				if (((JFXPasswordField) node).getText().equals("")) {
					isValid = false;
					node.setStyle("-fx-background-color: #FF584D");
				}
			} else {
				if (((DatePicker) node).getValue() == null) {
					node.setStyle("-fx-background-color: #FF584D");
					isValid = false;
				}
			}
		}
		// validate ssn
		Matcher matcher = pattern.matcher(ssn.getText());
		if (!matcher.matches())
			ssn.setStyle("-fx-background-color: #FF584D");
		
		// validate phone
	    pattern = Pattern.compile("^\\d{10}$");
	    matcher = pattern.matcher(phone.getText());
		if (!matcher.matches())
			phone.setStyle("-fx-background-color: #FF584D");

		return isValid;
	}

	public void handleBackButton() {
		Stage stage = (Stage) username.getScene().getWindow();
		stage.close();
	}

	public void handleResetButton() {
		JFXTextField[] fields = { username, firstname, lastname, address, phone, ssn };
		for (JFXTextField text : fields)
			text.setText("");
		password.setText("");
		sex.getSelectionModel().selectFirst();
		type.getSelectionModel().selectFirst();
		birthday.setValue(null);
	}

	public void handleSaveButton() {
		if (validate() == true) {
			String bdate = String.format("%d/%d/%d", birthday.getValue().getMonthValue(),
					birthday.getValue().getDayOfMonth(), birthday.getValue().getYear());
			String parameter = String.format(
					"ssn=%s&fname=%s&lname=%s&address=%s&bdate=%s&sex=%s&phone=%s&type=%s&username=%s&password=%s",
					ssn.getText(), firstname.getText(), lastname.getText(), address.getText(), bdate,
					sex.getValue().getText(), phone.getText(), type.getValue().getText(), username.getText(),
					password.getText());
			System.out.println(parameter);
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					PostRequestModel request = new PostRequestModel();
					request.makeRequest("/manager/employee", parameter);					
				}	
			}).run();
		}
	}

}
