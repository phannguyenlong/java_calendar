package com.sql_calendar.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sql_calendar.controller.cashier.CashingController;
import com.sql_calendar.controller.cashier.OrderHistoryController;
import com.sql_calendar.resources.Employee;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.sql_calendar.util.Animation;

/**
 * Controller for landing page of application
 * @author Long Phan
 */
public class LandingPageController implements Initializable, EventHandler<ActionEvent> {
    private Employee user;
    ArrayList<JFXButton> menuButtons;
    // FXML variable
    @FXML
    Label userName;
    @FXML
    Label userType;
    @FXML
    VBox menuContainer;
    @FXML
    AnchorPane contentContainer, rootPane;

    public Employee getUser() {
        return user;
    }

    // Will be called by Login controller and the app will receive Object of user
    public void setUser(Employee user) {
        this.user = user;

        userName.setText(user.getName());
        userType.setText(user.getType());

        init();
    }

    // Main function to render landing view
    private void init() {
        menuButtons = new ArrayList<JFXButton>();
        String[] menuStrings;
        
        // Setting up text for Menu component
        menuStrings = user.getType().equalsIgnoreCase("manager")
                ? new String[] { "Calendar Management", "Employee Management", "Finance Report", "Sign out" }
                : new String[] { "Cashing", "Order History", "Sign out" };
        HashMap<String, String> menuIcons = new HashMap<String, String>();
        menuIcons.put("Calendar Management", "mdi-calendar-multiple");
        menuIcons.put("Employee Management", "mdi-account-card-details");
        menuIcons.put("Finance Report", "mdi-newspaper");
        menuIcons.put("Cashing", "mdi-calculator");
        menuIcons.put("Order History", "mdi-history");
        menuIcons.put("Sign out", "mdi-logout");

        // Create Menu
        for (String str : menuStrings) {
            // Create Button
            JFXButton button = new JFXButton(str);
            button.prefWidthProperty().bind(menuContainer.prefWidthProperty());
            button.getStyleClass().addAll("menuButton");
            button.addEventHandler(ActionEvent.ACTION, this);

            // Set Icon
            FontIcon icon = new FontIcon(menuIcons.get(str));
            icon.getStyleClass().add("menuIcon");

            button.setGraphic(icon);
            menuContainer.getChildren().add(button);
            menuButtons.add(button);
        }
        menuButtons.get(0).setUnderline(true);

        // Load the First page (which is also first option of menu)
        FXMLLoader loader = user.getType().equalsIgnoreCase("manager")
                ? new FXMLLoader(getClass().getResource("../manager/calendarManagement.fxml"))
                : new FXMLLoader(getClass().getResource("../cashier/cashing.fxml"));
        try {
            VBox container = loader.load();
            if (user.getType().equals("cashier")) {
                CashingController controller = loader.getController();
                controller.setUser(user);
            }
            contentContainer.getChildren().add(container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    // Compare type of User to use proper handle Event Function
    @Override
    public void handle(ActionEvent event) {
        if (user.getType().equalsIgnoreCase("manager"))
            handleManager(event);
        else if (user.getType().equalsIgnoreCase("cashier"))
            handleCashier(event);
    }

    // Handle Button Click for Manager Menu
    private void handleManager(ActionEvent event) {
        FXMLLoader loader = null;
        contentContainer.getChildren().clear();
        Animation.makeFadeout(contentContainer);
        for (JFXButton btn : menuButtons)
            btn.setUnderline(false);
        if (event.getSource() == menuButtons.get(0)) {
            menuButtons.get(0).setUnderline(true);
            loader = new FXMLLoader(getClass().getResource("../manager/calendarManagement.fxml"));
        } else if (event.getSource() == menuButtons.get(1)) {
            menuButtons.get(1).setUnderline(true);
            loader = new FXMLLoader(getClass().getResource("../manager/employeeManagement.fxml"));
        } else if (event.getSource() == menuButtons.get(2)) {
            menuButtons.get(2).setUnderline(true);
            loader = new FXMLLoader(getClass().getResource("../manager/financeReport.fxml"));
        } else if (event.getSource() == menuButtons.get(3)) {
            handleLogout();
            return; // exit function
        }

        try {
            VBox container = loader.load();
            Animation.makeFadeback(contentContainer);
            contentContainer.getChildren().add(container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle Button click for Cashier Menu
    private void handleCashier(ActionEvent event) {
        FXMLLoader loader = null;
        boolean isCashingTab = true;
        contentContainer.getChildren().clear();
        Animation.makeFadeout(contentContainer);
        for (JFXButton btn : menuButtons)
            btn.setUnderline(false);
        if (event.getSource() == menuButtons.get(0)) {
            loader = new FXMLLoader(getClass().getResource("../cashier/cashing.fxml"));
            menuButtons.get(0).setUnderline(true);
        } else if (event.getSource() == menuButtons.get(1)) {
            loader = new FXMLLoader(getClass().getResource("../cashier/orderHistory.fxml"));
            menuButtons.get(1).setUnderline(true);
            isCashingTab = false;
        } else if (event.getSource() == menuButtons.get(2)) {
            handleLogout();
            return; // exit function
        }

        try {
            VBox container = loader.load();
            if (isCashingTab) {
                CashingController controller = loader.getController();
                controller.setUser(user);
            } else {
                OrderHistoryController controller = loader.getController();
                controller.setUser(user);
            }
            Animation.makeFadeback(contentContainer);
            contentContainer.getChildren().add(container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle Button Click for Log out
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../login.fxml"));
            Scene newScene = new Scene(loader.load());
            Stage currentStage = (Stage) rootPane.getScene().getWindow();
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
