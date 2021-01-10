package com.sql_calendar.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sql_calendar.resources.Employee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class LandingPageController implements Initializable, EventHandler<ActionEvent> {
    private Employee user;
    ArrayList<JFXButton> menuButtons;
    @FXML
    Label userName;
    @FXML
    Label userType;
    @FXML
    VBox menuContainer;
    @FXML
    AnchorPane contentContainer;

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;

        userName.setText(user.getName());
        userType.setText(user.getType());

        init();
    }

    private void init() {
        menuButtons = new ArrayList<JFXButton>();
        String[] menuStrings;

        menuStrings = user.getType().equalsIgnoreCase("manager")
                ? new String[] { "Calendar Management", "Employee Management", "Finance Report", "Sign out" }
                : new String[] { "Cashing", "Order History" };

        for (String str : menuStrings) {
            JFXButton button = new JFXButton(str);
            button.prefWidthProperty().bind(menuContainer.prefWidthProperty());
            button.getStyleClass().add("menuButton");
            button.addEventHandler(ActionEvent.ACTION, this);
            menuContainer.getChildren().add(button);
            menuButtons.add(button);
        }
        menuButtons.get(0).setUnderline(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../calendarManagement.fxml"));
        try {
            VBox container = loader.load();
            contentContainer.getChildren().add(container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void handle(ActionEvent event) {
        if (user.getType().equalsIgnoreCase("manager"))
            handleManager(event);
        else if (user.getType().equalsIgnoreCase("cashier"))
            handleCashier(event);
    }

    private void handleManager(ActionEvent event) {
        FXMLLoader loader = null;
        contentContainer.getChildren().clear();
        for (JFXButton btn : menuButtons)
            btn.setUnderline(false);
        if (event.getSource() == menuButtons.get(0)) {
            menuButtons.get(0).setUnderline(true);
            loader = new FXMLLoader(getClass().getResource("../calendarManagement.fxml"));
        } else if (event.getSource() == menuButtons.get(1)) {
            menuButtons.get(1).setUnderline(true);
            loader = new FXMLLoader(getClass().getResource("../employeeManagement.fxml"));
        } else if (event.getSource() == menuButtons.get(2)) {
            menuButtons.get(2).setUnderline(true);
            loader = new FXMLLoader(getClass().getResource("../financeReport.fxml"));
        } else if (event.getSource() == menuButtons.get(3)) {
            menuButtons.get(3).setUnderline(true);
        }

        try {
            VBox container = loader.load();
            contentContainer.getChildren().add(container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCashier(ActionEvent event) {
        for (JFXButton btn : menuButtons)
            btn.setUnderline(false);
        if (event.getSource() == menuButtons.get(0)) {
            menuButtons.get(0).setUnderline(true);
        } else if (event.getSource() == menuButtons.get(1)) {
            menuButtons.get(1).setUnderline(true);
        }
    }
    
}
