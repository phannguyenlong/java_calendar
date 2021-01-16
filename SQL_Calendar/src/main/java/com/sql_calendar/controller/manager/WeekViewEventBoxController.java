package com.sql_calendar.controller.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sql_calendar.resources.EventInstance;

import javafx.fxml.Initializable;

public class WeekViewEventBoxController implements Initializable {

    private ArrayList<EventInstance> eventData;

    public void setEventData(ArrayList<EventInstance> eventData) {
        this.eventData = eventData;

        updateUI();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void updateUI() {
        // for (EventInstance e : eventData) {
        //     System.out.println(e);
        // }
    }
    
}
