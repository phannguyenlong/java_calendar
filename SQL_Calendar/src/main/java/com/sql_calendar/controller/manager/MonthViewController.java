package com.sql_calendar.controller.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.sql_calendar.resources.MonthView;
import com.sql_calendar.util.GetRequestModel;
import com.sql_calendar.util.Tool;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MonthViewController implements Initializable {
    @FXML
    GridPane calendarContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Date date = Tool.convertStringtoDate("12/23/2020");

        int firstWeekDay = Tool.getDayofWeek(Tool.getFirstDateOfMonth(date));

        for (int i = firstWeekDay; i <= firstWeekDay + Tool.getNumberOfDayInMonth(date) - 1; i++) {
            calendarContainer.add(new Label(String.valueOf(i - firstWeekDay + 1)), (i - 1) % 7, (i - 1) / 7);
        }

        // System.out.println(Tool.getWeekofMonth(date));

        GetRequestModel request = new GetRequestModel();
        Thread makeRequest = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<MonthView> datas = request.makeRequest("/manager/calendar/month", MonthView.class,
                        "date=12/23/2020");
                System.out.println(datas.size());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for (MonthView data : datas) {
                            // System.out.println(data);
                            Date d = new Date(Long.parseLong(data.getDate()));

                            calendarContainer.add(new Label("Day Income: " + data.getDayIncome()),
                                    Tool.getDayofWeek(d) - 1, Tool.getWeekofMonth(d) - 1);
                        }
                    }
                });
            }
        });
        makeRequest.start();

    }
}
