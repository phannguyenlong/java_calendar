package com.sql_calendar.controller.manager;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.sql_calendar.resources.MonthView;
import com.sql_calendar.util.GetRequestModel;

import javafx.fxml.Initializable;

public class MonthViewController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GetRequestModel request = new GetRequestModel();
        Thread makeRequest = new Thread(new Runnable(){
            @Override
            public void run() {
                ArrayList<MonthView> datas = request.makeRequest("/manager/calendar/month", MonthView.class,
                        "date=12/23/2020");
                System.out.println(datas.size());
                for (MonthView data : datas) {
                    System.out.println(data);
                }
            }
        });
        makeRequest.start();
    }
}
