package com.sql_calendar.controller.manager;

import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sql_calendar.resources.EventInstance;
import com.sql_calendar.util.DeleteRequestModel;
import com.sql_calendar.util.PutRequestModel;
import com.sql_calendar.util.Tool;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class DayViewEventBoxController implements Initializable {
    private ArrayList<EventInstance> eventData;
    @FXML
    Label eventNameLabel, periodLabel, shiftIncomeLabel, endedLabel;
    @FXML
    FlowPane employeeContainer;
    @FXML
    VBox container;

    public void setEventData(ArrayList<EventInstance> eventData) {
        this.eventData = new ArrayList<>();
        for (EventInstance data : eventData)
            this.eventData.add(data.deepCopy());

        renderUI();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeContainer.setVgap(10);
        employeeContainer.setHgap(10);
    }

    private void renderUI() {
        eventNameLabel.setText(eventData.get(0).getEventName());
        periodLabel.setText(String.format("(%s - %s)", eventData.get(0).getStartTime(), eventData.get(0).getEndTime()));

        if (Tool.compare2Time(Time.valueOf(LocalTime.now()),
                Tool.convertStringToTime(eventData.get(0).getEndTime())) < 0)
            endedLabel.setVisible(false);

        for (EventInstance data : eventData) {
            if (data.getEssn() != null) {
                // Config
                HBox employeeBox = new HBox();
                employeeBox.setStyle("-fx-background-color: white;-fx-background-radius: 10");
                employeeBox.setPadding(new Insets(5));
                employeeBox.setSpacing(5);
                employeeBox.setAlignment(Pos.CENTER);
                employeeBox.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.25), 10, 0.25, 2, 2));

                Label empName = new Label(data.getName());
                Label empType = new Label(data.getType());
                empName.setStyle("-fx-font-weight: bold");
                empType.setStyle("-fx-text-fill: #C0C3CC;-fx-font-style: italic");
                VBox emp = new VBox();
                emp.getChildren().addAll(empName, empType);
                employeeBox.getChildren().add(emp);
                // absent button
                FontIcon absentIcon = new FontIcon("mdi-bookmark-remove");
                absentIcon.setFill(Paint.valueOf("#FF6243"));
                absentIcon.setIconSize(22);
                JFXButton absentButton = new JFXButton("", absentIcon);
                // present button
                FontIcon presentIcon = new FontIcon("mdi-checkbox-marked-circle");
                presentIcon.setFill(Paint.valueOf("#3BB54A"));
                presentIcon.setIconSize(22);
                JFXButton presentButton = new JFXButton("", presentIcon);
                // add eventHandler
                absentButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        updateStatus(data, "absent", presentButton, absentButton);
                    };
                });
                presentButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        updateStatus(data, "present", presentButton, absentButton);
                    };
                });

                if (data.getStatus() != null) {
                    if (data.getStatus().equals("absent"))
                        absentButton.setStyle("-fx-background-color: #C0C3CC");
                    else 
                        presentButton.setStyle("-fx-background-color: #C0C3CC");
                }

                employeeBox.getChildren().addAll(absentButton, presentButton);
                employeeContainer.getChildren().add(employeeBox);
            }
        }
    }

    private void updateStatus(EventInstance data, String status, JFXButton present, JFXButton absent) {
        if (status.equals("present")) {
            present.setStyle("-fx-background-color: #C0C3CC");
            absent.setStyle("-fx-background-color: transparent");
        } else {
            absent.setStyle("-fx-background-color: #C0C3CC");
            present.setStyle("-fx-background-color: transparent");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                PutRequestModel request = new PutRequestModel();
                String parameter = String.format("essn=%s&eventID=%s&date=%s&status=%s", data.getEssn(),
                        data.getEventID(), Tool.convertDateToString(data.getDate()), status);
                System.out.println(parameter);
                request.makeRequest("/manager/calendar/day/shift", parameter);
            }
        }).start();
    }
    
    public void onDeleteEvent() {
        container.getChildren().clear();
        ((HBox) container.getParent()).getChildren().remove(container);
        
        new Thread(new Runnable(){
            @Override
            public void run() {
                DeleteRequestModel request = new DeleteRequestModel();
                String parameter = "eventID=" + eventData.get(0).getEventID();
                request.makeRequest("/manager/calendar/event/action", parameter);
            }
        }).run();
    }
}
