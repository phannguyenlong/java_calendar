package com.sql_calendar.controller.manager;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sql_calendar.resources.Employee;
import com.sql_calendar.util.DeleteRequestModel;
import com.sql_calendar.util.PutRequestModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EmployeeInfoController implements Initializable {
	@FXML
	JFXTextField name, address, phone, ssn;
	@FXML
	JFXComboBox<Label> type;
	private Employee user;

	public void setUser(Employee user) {
		this.user = user;

		init();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		type.getItems().add(new Label("staff"));
		type.getItems().add(new Label("cashier"));
		type.getItems().add(new Label("manager"));

		name.setDisable(true);
		ssn.setDisable(true);
	}

	private void init() {
		JFXTextField[] fields = { address, phone };
		for (JFXTextField node : fields)
			node.setStyle("-fx-background-color: transparent");
		
		name.setText(user.getName());
		ssn.setText(user.getSsn());
		address.setText(user.getAddress());
		phone.setText(user.getPhone());

		if (user.getType().equals("staff")) {
			type.getSelectionModel().select(0);
		} else if (user.getType().equals("cashier")) {
			type.getSelectionModel().select(1);
		} else {
			type.getSelectionModel().select(2);
		}
	}

	public void handleBackButton() {
		Stage stage = (Stage) name.getScene().getWindow();
		stage.close();
	}

	public void handleSaveButton() {
		boolean isValid = true;
		JFXTextField[] fields = { address, phone };
		for (JFXTextField node : fields)
			node.setStyle("-fx-background-color: transparent");
		// Validate if null ==> set background red
		for (JFXTextField node : fields) {
			if (((JFXTextField) node).getText().equals("")) {
				isValid = false;
				node.setStyle("-fx-background-color: #FF584D");
			}
		}

		// validate phone
		Pattern pattern = Pattern.compile("^\\d{10}$");
		Matcher matcher = pattern.matcher(phone.getText());
		if (!matcher.matches()) {
			phone.setStyle("-fx-background-color: #FF584D");
			isValid = false;
		}
		
		if (isValid) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					PutRequestModel request = new PutRequestModel();
					String parameter = String.format("ssn=%s&address=%s&phone=%s&type=%s", ssn.getText(), address.getText(), phone.getText(), type.getValue().getText());
					
					request.makeRequest("/manager/employee", parameter);
				}
			}).run();
			Stage stage = (Stage) name.getScene().getWindow();
			stage.close();
		}
	}
	
	public void handleDeleteButton() {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				DeleteRequestModel request = new DeleteRequestModel();
				String parameter = String.format("ssn=%s", user.getSsn());
				request.makeRequest("/manager/employee", parameter);
			}
		}).run();
		Stage stage = (Stage) name.getScene().getWindow();
		stage.close();
	}
	

}
