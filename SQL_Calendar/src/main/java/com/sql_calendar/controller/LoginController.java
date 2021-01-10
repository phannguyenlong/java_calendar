package com.sql_calendar.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sql_calendar.resources.Employee;
import com.sql_calendar.util.GetRequestModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class LoginController implements Initializable {
    @FXML
    Button submitButton;
    @FXML
    JFXTextField username;
    @FXML
    JFXPasswordField password;
    @FXML
    ImageView loadingIcon;

    public JFXTextField getUsername() {
        return username;
    }

    public void setUsername(JFXTextField username) {
        this.username = username;
    }

    public JFXPasswordField getPassword() {
        return password;
    }

    public void setPassword(JFXPasswordField password) {
        this.password = password;
    }

    public void handleSubmit() {
        GetRequestModel request = new GetRequestModel();
        String parameter = "username=" + username.getText() + "&password=" + password.getText();

        Thread makeRequest = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    loadingIcon.setVisible(true); // show loading icon
                    ArrayList<Employee> list = request.makeRequest("/auth", Employee.class, parameter);
                    if (list.isEmpty()) {
                        System.out.println("Wrong");
                        username.setStyle("-jfx-unfocus-color:RED; -jfx-focus-color:RED; -fx-text-inner-color: red;");
                        ;
                        password.setStyle("-jfx-unfocus-color:RED; -jfx-focus-color:RED; -fx-text-inner-color: red;");
                    } else {
                        System.out.println(list.get(0).toString());
                        System.out.println("Welcome");
                    }
                } catch (IOException e) {
                    System.out.println("Error connect to server");
                    e.printStackTrace();
                } finally {
                    loadingIcon.setVisible(false);
                }
            }
        });
        makeRequest.start();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("RUn this first");
        loadingIcon.setVisible(false);
    }
}
