package com.sql_calendar.controller.manager;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sql_calendar.util.PostRequestModel;
import com.sql_calendar.util.Tool;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;

/**
 * A new Scene popup to Add new event
 * 
 * @author Long Phan
 */
public class NewEventBoxController implements Initializable {
    @FXML
    JFXComboBox<Label> typeOption;
    @FXML
    JFXTextField eventName, startTimeHour, startTimeMinute, endTimeHour, endTimeMinute;
    @FXML
    DatePicker startDate, endDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Config component
        typeOption.getItems().add(new Label("daily"));
        typeOption.getItems().add(new Label("weekly"));
        typeOption.getItems().add(new Label("no repeat"));
        typeOption.getSelectionModel().selectFirst();

        startTimeHour.setTextFormatter(new TextFormatter<>(this::hourFilter));
        endTimeHour.setTextFormatter(new TextFormatter<>(this::hourFilter));
        startTimeMinute.setTextFormatter(new TextFormatter<>(this::minuteFilter));
        endTimeMinute.setTextFormatter(new TextFormatter<>(this::minuteFilter));

        startDate.setShowWeekNumbers(true);
        endDate.setShowWeekNumbers(true);
    }

    private TextFormatter.Change hourFilter(TextFormatter.Change change) {
        if (!change.getControlNewText().matches("([0-9]|1[0-9]|2[0-3])")) {
            change.setText("");
        }
        return change;
    }

    private TextFormatter.Change minuteFilter(TextFormatter.Change change) {
        if (!change.getControlNewText().matches("([0-9]|[0-5][0-9])")) {
            change.setText("");
        }
        return change;
    }

    // Handle New Event popup
    public void createNewEvent() {
        boolean isNull = false;
        Parent[] arr = { eventName, startTimeHour, startTimeMinute, endTimeHour, endTimeMinute, startDate, endDate };
        // Validating
        for (Parent component : arr) {
            component.setStyle("-fx-background-color:  #E9E9EA");
            if (component != startDate && component != endDate) {
                if (((JFXTextField) component).getText().isEmpty()) {
                    component.setStyle("-fx-background-color: #FF584D");
                    isNull = true;
                }
            } else {
                if (((DatePicker) component).getValue() == null) {
                    if (component == endDate && typeOption.getValue().getText().equals("no repeat"))
                        continue;
                    component.setStyle("-fx-background-color: #FF584D");
                    isNull = true;
                }
            }
        }

        if (!isNull) {
            if (!typeOption.getValue().getText().equals("no repeat"))
                if (startDate.getValue().isAfter(endDate.getValue())) {
                    startDate.setStyle("-fx-background-color: #FF584D");
                    endDate.setStyle("-fx-background-color: #FF584D");
                    isNull = true;
                }

            if (Tool.compare2Time(Tool.convertStringToTime(startTimeHour.getText() + ":" + startTimeMinute.getText()),
                    Tool.convertStringToTime(endTimeHour.getText() + ":" + endTimeMinute.getText())) >= 0) {
                startTimeHour.setStyle("-fx-background-color: #FF584D");
                endTimeHour.setStyle("-fx-background-color: #FF584D");
                startTimeMinute.setStyle("-fx-background-color: #FF584D");
                endTimeMinute.setStyle("-fx-background-color: #FF584D");
                isNull = true;
            }
        }

        // If validate success ==> make request to server
        if (!isNull) {
            String startDateString = String.format("%d/%d/%d", startDate.getValue().getMonthValue(),
                    startDate.getValue().getDayOfMonth(), startDate.getValue().getYear());
            String endDateString = typeOption.getValue().getText().equals("no repeat") ? startDateString
                    : String.format("%d/%d/%d", endDate.getValue().getMonthValue(), endDate.getValue().getDayOfMonth(),
                            endDate.getValue().getYear());

            String parameter = String.format(
                    "eventName=%s&startDate=%s&endDate=%s&startTime=%s:%s&endTime=%s:%s&eventType=%s",
                    eventName.getText(), startDateString, endDateString, startTimeHour.getText(),
                    startTimeMinute.getText(), endTimeHour.getText(), endTimeMinute.getText(),
                    typeOption.getValue().getText());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    PostRequestModel request = new PostRequestModel();
                    request.makeRequest("/manager/calendar/event/action", parameter);
                }
            }).run();
            ;
        }
    }

    public void handleChangeEventType() {
        if (typeOption.getValue().getText().equals("no repeat"))
            endDate.setDisable(true);
        else
            endDate.setDisable(false);
    }
}
