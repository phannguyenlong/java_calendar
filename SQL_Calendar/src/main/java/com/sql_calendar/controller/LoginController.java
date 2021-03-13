package com.sql_calendar.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sql_calendar.resources.Employee;
import com.sql_calendar.util.GetRequestModel;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for Log in Action
 * @author Long Phan
 */
public class LoginController implements Initializable {
    // FXML variable
    @FXML
    Button submitButton;
    @FXML
    JFXTextField username;
    @FXML
    JFXPasswordField password;
    @FXML
    ImageView loadingIcon;
    @FXML
    HBox rootPane;

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

    // Action happen when clicking Button "Sign In"
    public void handleSubmit() {
        Thread makeRequest = new Thread(new Runnable() {
            // Make request to server
            @Override
            public void run() {
                GetRequestModel request = new GetRequestModel();
                String parameter = "username=" + username.getText() + "&password=" + password.getText();
                loadingIcon.setVisible(true); // show loading icon
                ArrayList<Employee> list = request.makeRequest("/auth", Employee.class, parameter);
                if (list.isEmpty()) { // If wrong ==> Set all to "red" color
                    username.setStyle("-jfx-unfocus-color:RED; -jfx-focus-color:RED; -fx-text-inner-color: red;");
                    password.setStyle("-jfx-unfocus-color:RED; -jfx-focus-color:RED; -fx-text-inner-color: red;");
                } else { // If success ==> Fade out then open LandingPage.fxml
                    if (list.get(0).getType().equals("staff")) {
                        username.setStyle("-jfx-unfocus-color:RED; -jfx-focus-color:RED; -fx-text-inner-color: red;");
                        password.setStyle("-jfx-unfocus-color:RED; -jfx-focus-color:RED; -fx-text-inner-color: red;");
                    } else {
                        System.out.println(list.get(0).toString());
                        makeFadeout(list.get(0));
                    }
                }
                loadingIcon.setVisible(false);
            }
        });
        makeRequest.start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadingIcon.setVisible(false); // hide loading Icon
    }

    // Fade out function then open landingPage.fxml
    private void makeFadeout(Employee employee) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> renderNewScene(employee));
    }

    // Function handle to open new Scene (which is landingPage.fxml)
    private void renderNewScene(Employee employee) {
        System.out.println("new scene");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../landingPage.fxml"));
            Parent newView = loader.load();

            LandingPageController controller = loader.getController();
            controller.setUser(employee);

            Scene newScene = new Scene(newView);
            Stage currentStage = (Stage) rootPane.getScene().getWindow();

            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
