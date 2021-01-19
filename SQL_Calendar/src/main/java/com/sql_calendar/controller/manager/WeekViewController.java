package com.sql_calendar.controller.manager;

import java.io.IOException;
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

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class WeekViewController implements Initializable {
    @FXML
    private ScrollPane jsp;
    @FXML
    private Label weekLabel, periodLabel;
    @FXML
    public StackPane stackPane;
    @FXML
    private JFXButton nextButton, prevButton;
    @FXML
    ImageView loadingIcon;
    @FXML
    private VBox sunday, monday, tuesday, webnesday, thursday, friday, saturday;
    private ArrayList<ArrayList<EventInstance>> dayLists; // start from sunday(1) to saturday(7)

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        jsp.setHbarPolicy(ScrollBarPolicy.NEVER);
        jsp.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        // set up scrollPane speed
        jsp.getContent().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY() * 0.01;
            jsp.setVvalue(jsp.getVvalue() - deltaY);
        });

        renderWeekView();
    }

    private void renderWeekView() {
        loadingIcon.setVisible(true);
        jsp.setDisable(true);
        GetRequestModel request = new GetRequestModel();
        Thread makeRequest = new Thread(new Runnable() {
            @Override
            public void run() {
                // Set up date
                Date date = Tool.convertStringtoDate(CalendarManagementController.date);
                date = Tool.getFirstDayofWeek(date);
                CalendarManagementController.date = Tool.convertDateToString(date);

                // Add text to Label
                weekLabel.setText(String.format("Week %d", Tool.getWeekofMonth(date)));
                Date firstDayOfWeek = Tool.getFirstDayofWeek(date);
                periodLabel.setText(String.format("(%s - %s)", Tool.convertDateToString(firstDayOfWeek), Tool.convertDateToString(Tool.plusOrMinusDay(firstDayOfWeek, 6, 1))));

                ArrayList<EventInstance> datas = request.makeRequest("/manager/calendar/week", EventInstance.class,
                        "date=" + CalendarManagementController.date);

                // create 7 arrays of day
                dayLists = new ArrayList<ArrayList<EventInstance>>();
                for (int i = 0; i < 7; i++)
                    dayLists.add(new ArrayList<EventInstance>());

                if (!datas.isEmpty()) {
                    // devide recieving data to arrays of day
                    for (EventInstance data : datas) {
                        // Derived into 7 array
                        Date d = data.getDate() == null ? data.getStartDate() : data.getDate();
                        if (data.getEventType().equals("daily") && data.getDate() == null) {
                            dayLists.forEach(day -> day.add(data));
                        } else {
                            dayLists.get(Tool.getDayofWeek(d) - 1).add(data);
                        }
                    }

                    // Sort arrays by startTime and ID
                    for (ArrayList<EventInstance> day : dayLists) {
                        Collections.sort(day, new Comparator<EventInstance>() {
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
                    }

                    loadingIcon.setVisible(false);
                    jsp.setDisable(false);
                    // Add data to UI
                    renderEventBox();
                } else {
                    loadingIcon.setVisible(false);
                    jsp.setDisable(false);
                }
            }
        });
        makeRequest.run();
    }

    private void renderEventBox() {
        // Assign them to VBox
        VBox[] dayContainers = { sunday, monday, tuesday, webnesday, thursday, friday, saturday };
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 7; i++) {
                    if (dayLists.get(i).isEmpty()) continue;
                    ArrayList<EventInstance> event = new ArrayList<>();
                    for (int j = 0; j < dayLists.get(i).size(); j++) {
                        EventInstance eventInstances = dayLists.get(i).get(j);
                        if (j != 0)
                            if (eventInstances.getEventID() != dayLists.get(i).get(j - 1).getEventID()) {
                                FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource("../../manager/weekEvent.fxml"));
                                try {
                                    VBox eventBox = loader.load();
                                    WeekViewEventBoxController controller = loader.getController();
                                    controller.setEventData(event);
                                    controller.setStackPane(stackPane);
                                    controller.setWeekDay(i); // 0 is sunday, 6 is saturday
                                    dayContainers[i].getChildren().add(eventBox);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    event.clear();
                                    event.add(eventInstances);
                                }
                            } else {
                                event.add(eventInstances);
                            }
                        else {
                            event.add(eventInstances);
                        }
                    }
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../../manager/weekEvent.fxml"));
                    try {
                        VBox eventBox = loader.load();
                        WeekViewEventBoxController controller = loader.getController();
                        controller.setEventData(event);
                        controller.setStackPane(stackPane);
                        controller.setWeekDay(i); // 0 is sunday, 6 is saturday
                        dayContainers[i].getChildren().add(eventBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        event.clear();
                    }
                }
            };
        });
    }

    public void handleChangeWeek(ActionEvent e) {
        Date date = Tool.convertStringtoDate(CalendarManagementController.date);
        date = e.getSource() == nextButton ? Tool.plusOrMinusDay(Tool.getFirstDayofWeek(date), 7, 1) : Tool.plusOrMinusDay(Tool.getFirstDayofWeek(date), 7, 0);
        CalendarManagementController.date = Tool.convertDateToString(date);

        // Cleaning
        VBox[] dayContainers = { sunday, monday, tuesday, webnesday, thursday, friday, saturday };
        for (VBox box : dayContainers) 
            box.getChildren().clear();
        for (ArrayList<EventInstance> event : dayLists) 
            event.clear();
        dayLists.clear();

        // Rerender
        renderWeekView();
    }
}
