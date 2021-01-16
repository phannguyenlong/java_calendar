package com.sql_calendar.controller.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sql_calendar.resources.EventInstance;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class WeekViewEventBoxController implements Initializable {
    private ArrayList<EventInstance> eventData;
    @FXML
    Label eventNameLabel, eventPeriodLabel;

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
            eventPeriodLabel.setText(String.format("%s - %s", eventData.get(0).getStartTime(), eventData.get(0).getEndTime()));
        }
    }
    
}
