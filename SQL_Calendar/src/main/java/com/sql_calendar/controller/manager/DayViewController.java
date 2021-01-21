package com.sql_calendar.controller.manager;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sql_calendar.resources.EventInstance;
import com.sql_calendar.util.GetRequestModel;
import com.sql_calendar.util.Tool;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DayViewController implements Initializable {
    @FXML
    private ScrollPane jsp;
    @FXML
    Label weekdayLabel, dayLabel;
    @FXML
    ImageView loadingIcon;
    @FXML
    JFXButton prevButton, nextButton;
    @FXML
    VBox contentContainer;
    @FXML
    HBox lineContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set up scrollPane speed
        jsp.getContent().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY() * 0.01;
            double deltaX = scrollEvent.getDeltaX() * 0.005;
            jsp.setVvalue(jsp.getVvalue() - deltaY);
            jsp.setHvalue(jsp.getHvalue() - deltaX);
        });
        renderUI();
    }

    private void renderUI() {
        loadingIcon.setVisible(true);
        jsp.setDisable(true);
        lineContainer.setPrefHeight(430);

        String[] weekdays = { "Sunday" ,"Monday", "Tuesday", "Webnesday", "Thursday", "Friday", "Saturday" };
        dayLabel.setText(CalendarManagementController.date);
        weekdayLabel.setText(weekdays[Tool.getDayofWeek(Tool.convertStringtoDate(CalendarManagementController.date)) - 1 ]);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Date date = Tool.convertStringtoDate(CalendarManagementController.date);

                GetRequestModel request = new GetRequestModel();
                ArrayList<EventInstance> datas = request.makeRequest("/manager/calendar/day", EventInstance.class,
                        "date=" + CalendarManagementController.date);

                if (!datas.isEmpty()) {
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
                            } else if (data.getEventType().equals("weekly")
                                    && !weeklyEvent.contains(data.getEventID())) {
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

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<HBox> hboxList = new ArrayList<>();
                            ArrayList<EventInstance> eventList = new ArrayList<>();
                            ArrayList<Time> endTimeList = new ArrayList<>();

                            eventList.add(datas.get(0));
                            for (int i = 1; i < datas.size(); i++) {
                                if (datas.get(i - 1).getEventID() != datas.get(i).getEventID()) {
                                    renderNewEventBox(i, hboxList, endTimeList, datas, eventList);
                                }
                                eventList.add(datas.get(i));
                            }
                            renderNewEventBox(datas.size() - 1, hboxList, endTimeList, datas, eventList);
                            contentContainer.getChildren().addAll(hboxList);
                            if (hboxList.size() > 1)
                                lineContainer.setPrefHeight(250 * hboxList.size() + 10 * hboxList.size());
                            else
                                lineContainer.setPrefHeight(430);
                        }
                    });
                }
                loadingIcon.setVisible(false);
                jsp.setDisable(false);
            }
        }).run();
    }

    private void renderNewEventBox(int i, ArrayList<HBox> hboxList, ArrayList<Time> endTimeList,
            ArrayList<EventInstance> datas, ArrayList<EventInstance> eventList) {
        // find which Hbox to pass data in
        int anchor;
        double margin;
        i = i == 0 ? 1 : i; // standarlize i

        for (anchor = 0; anchor < endTimeList.size(); anchor++) {
            if (endTimeList.get(anchor).compareTo(Tool.convertStringToTime(datas.get(i - 1).getStartTime())) < 0) {
                break;
            }
        }
        if (anchor == endTimeList.size()) { // if run to end of array ==> make new Hbox
            margin = 25 + 85 * (Tool.converTimeStringToDouble(datas.get(i - 1).getStartTime()) - 6);
            hboxList.add(new HBox());
            endTimeList.add(Tool.convertStringToTime(datas.get(i - 1).getEndTime()));
        } else { // update endTime of current Hbox
            margin = 85 * Tool.compare2Time(Tool.convertStringToTime(datas.get(i - 1).getStartTime()),
                    endTimeList.get(anchor));
            endTimeList.set(anchor, Tool.convertStringToTime(datas.get(i - 1).getEndTime()));
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../manager/dayViewEventBox.fxml"));
        try {
            VBox eventBox = loader.load();
            DayViewEventBoxController controller = loader.getController();
            controller.setEventData(eventList);
            eventBox.setPrefWidth(85 * (Tool.converTimeStringToDouble(eventList.get(0).getEndTime())
                    - Tool.converTimeStringToDouble(eventList.get(0).getStartTime())));
            HBox.setMargin(eventBox, new Insets(0, 0, 0, margin));
            hboxList.get(anchor).getChildren().add(eventBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
        eventList.clear();
    }

    public void handleChangeDay(ActionEvent e) {
        Date date = Tool.convertStringtoDate(CalendarManagementController.date);
        date = e.getSource() == nextButton ? Tool.plusOrMinusDay(date, 1, 1)
                : Tool.plusOrMinusDay(date, 1, 0);
        CalendarManagementController.date = Tool.convertDateToString(date);
        System.out.println(CalendarManagementController.date);
        // cleaning
        contentContainer.getChildren().clear();
        // rerender
        renderUI();
    }
}
