package com.sql_calendar.controller.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sql_calendar.resources.EventInstance;

import javafx.fxml.Initializable;

public class DayViewEventBoxController implements Initializable {
    private ArrayList<EventInstance> eventData;

    public void setEventData(ArrayList<EventInstance> eventData) {
        this.eventData = new ArrayList<>();
        for (EventInstance data : eventData)
            this.eventData.add(data.deepCopy());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Day Veiw box");
    }
}
