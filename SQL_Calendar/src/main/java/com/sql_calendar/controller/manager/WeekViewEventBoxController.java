package com.sql_calendar.controller.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sql_calendar.resources.EventInstance;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class WeekViewEventBoxController implements Initializable {
    private ArrayList<EventInstance> eventData;
    @FXML
    Label eventNameLabel, eventPeriodLabel;
    @FXML
    VBox employeeList;

    public void setEventData(ArrayList<EventInstance> eventData) {
        this.eventData = eventData;

        updateUI();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void updateUI() {
        if (!eventData.isEmpty()) {
            eventNameLabel.setText(eventData.get(0).getEventName());
            eventPeriodLabel
                    .setText(String.format("%s - %s", eventData.get(0).getStartTime(), eventData.get(0).getEndTime()));
            
            for (EventInstance event : eventData) {
                // employeeList.getChildren().add(new Label(event.getName()));
                if (event.getName() != null) {
                    HBox employeBox = new HBox();
                    employeBox.setStyle("-fx-background-color: white; -fx-background-border: 10");
                    employeBox.setAlignment(Pos.CENTER_LEFT);
                    employeBox.setPrefSize(105, 30);

                    JFXButton deleteButton = new JFXButton("");
                    Label empName = new Label(event.getName());
                    empName.setPrefWidth(90);

                    // Set Icon
                    FontIcon icon = new FontIcon("mdi-account-remove");
                    icon.setFill(Paint.valueOf("#E76E54"));

                    deleteButton.setGraphic(icon);
                    employeBox.getChildren().addAll(empName, deleteButton);
                    employeeList.getChildren().add(employeBox);
                }
            }
        }
    }
    
}
