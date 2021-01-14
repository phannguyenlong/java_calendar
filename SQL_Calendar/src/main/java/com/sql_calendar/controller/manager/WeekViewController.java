package com.sql_calendar.controller.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sql_calendar.resources.EventInstance;
import com.sql_calendar.util.GetRequestModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class WeekViewController implements Initializable {
    @FXML
    VBox monday, tuesday, webnesday, thursday, friday, saturday, sunday;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Week View");

        GetRequestModel request = new GetRequestModel();
        Thread makeRequest = new Thread(new Runnable(){
            @Override
            public void run() {
                ArrayList<EventInstance> datas = request.makeRequest("/manager/calendar/week", EventInstance.class,
                        "date=" + "1/5/2021");
                for (EventInstance data : datas) {
                    System.out.println(data);
                    // TODO: derived data into 7 arrays, then sort 7 array by startTime and ID (if same startTime, compare ID). Use Comparable
                }
            }
        });
        makeRequest.run();
    }
}
