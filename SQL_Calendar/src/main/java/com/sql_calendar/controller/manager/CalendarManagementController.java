package com.sql_calendar.controller.manager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.sql_calendar.util.Animation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class CalendarManagementController implements Initializable {
    protected static String date;
    // FXML varibale
    @FXML
    JFXComboBox<Label> viewOption;
    @FXML
    AnchorPane viewContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add view options
        viewOption.getItems().add(new Label("Month"));
        viewOption.getItems().add(new Label("Week"));
        viewOption.getItems().add(new Label("Day"));
        viewOption.getSelectionModel().selectFirst();

        // get current Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
        date = dtf.format(LocalDateTime.now());
        System.out.println(date);
        
        handleViewOption();
    }

    public void handleViewOption() {
        FXMLLoader loader = null;
        viewContainer.getChildren().clear();
        Animation.makeFadeout(viewContainer);
        String option = (String) viewOption.getValue().getText();
        System.out.println(date);

        if (option.equals("Month"))
            loader = new FXMLLoader(getClass().getResource("../../manager/monthView.fxml"));
        else if (option.equals("Week"))
            loader = new FXMLLoader(getClass().getResource("../../manager/weekView.fxml"));
        else if (option.equals("Day"))
            loader = new FXMLLoader(getClass().getResource("../../manager/dayView.fxml"));
        try {
            FlowPane container = loader.load();
            Animation.makeFadeback(viewContainer);
            viewContainer.getChildren().add(container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
