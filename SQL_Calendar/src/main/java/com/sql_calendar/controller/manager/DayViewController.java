package com.sql_calendar.controller.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sql_calendar.resources.EventInstance;
import com.sql_calendar.util.GetRequestModel;
import com.sql_calendar.util.Tool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class DayViewController implements Initializable {
    @FXML
    Label weekDayLabel, dayLabel;
    @FXML
    ImageView loadingIcon;
    @FXML
    JFXButton prevButton, nextButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Day view");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Date date = Tool.convertStringtoDate(CalendarManagementController.date);

                GetRequestModel request = new GetRequestModel();
                ArrayList<EventInstance> datas = request.makeRequest("/manager/calendar/day", EventInstance.class,
                        "date=" + CalendarManagementController.date);
                // filter data
                ArrayList<Integer> dailyEvent = new ArrayList<>();
                ArrayList<Integer> weeklyEvent = new ArrayList<>();
                ArrayList<EventInstance> toRemove = new ArrayList<>();
                ArrayList<EventInstance> toAdd = new ArrayList<>();
                for (EventInstance data : datas) {
                    if (data.getDate() != null) {
                        if (data.getEventType().equals("daily") && !dailyEvent.contains(data.getEventID())) {
                            EventInstance e = data.makeSampleEvent();
                            toAdd.add(e);
                            dailyEvent.add(data.getEventID());
                        } else if (data.getEventType().equals("weekly") && !weeklyEvent.contains(data.getEventID())) {
                            EventInstance e = data.makeSampleEvent();
                            toAdd.add(e);
                            weeklyEvent.add(data.getEventID());
                        }
                        if (data.getDate().compareTo(date) != 0) {
                            toRemove.add(data);
                        }
                    }
                }
                datas.removeAll(toRemove);
                datas.addAll(toAdd);

                // Sort data
                Collections.sort(datas, new Comparator<EventInstance>() {
                    @Override
                    public int compare(EventInstance o1, EventInstance o2) {
                        if (Tool.convertStringToTime(o1.getStartTime())
                                .compareTo(Tool.convertStringToTime(o2.getStartTime())) == 0)
                            return o1.getEventID() > o2.getEventID() ? 1 : -1;
                        else {
                            return Tool.convertStringToTime(o1.getStartTime())
                                    .compareTo(Tool.convertStringToTime(o2.getStartTime()));
                        }
                    }
                });

                for (EventInstance data : datas) {
                    System.out.println(data);
                }
            }
        }).run();
    }

    public void handleChangeDay(ActionEvent e) {
        System.out.println("click");
    }
}
