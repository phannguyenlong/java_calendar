package com.sql_calendar.controller.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.sql_calendar.resources.Employee;
import com.sql_calendar.resources.EventInstance;
import com.sql_calendar.util.DeleteRequestModel;
import com.sql_calendar.util.GetRequestModel;
import com.sql_calendar.util.PostRequestModel;
import com.sql_calendar.util.Tool;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class WeekViewEventBoxController implements Initializable {
    private ArrayList<EventInstance> eventData;
    private JFXDialog dialog;
    private JFXDialogLayout content;
    private StackPane stackPane;
    private VBox emplListContainer;
    private int weekDay;
    @FXML
    Label eventNameLabel, eventPeriodLabel;
    @FXML
    VBox employeeList;

    public void setEventData(ArrayList<EventInstance> eventData) {
        this.eventData = new ArrayList<>();
        // Must make a deep copy
        for (EventInstance data : eventData)
            this.eventData.add(data.deepCopy());
 
        updateUI();
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
        initPopup();
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeList.setPadding(new Insets(5));
    }

    private void updateUI() {
        if (!eventData.isEmpty()) {
            eventNameLabel.setText(eventData.get(0).getEventName());
            eventPeriodLabel
                    .setText(String.format("%s - %s", eventData.get(0).getStartTime(), eventData.get(0).getEndTime()));

            for (EventInstance event : eventData) {
                // employeeList.getChildren().add(new Label(event.getName()));
                if (event.getName() != null) {
                    HBox employeBox = new HBox();
                    employeBox.setStyle("-fx-background-color: white;-fx-background-radius: 5;");
                    employeBox.setAlignment(Pos.CENTER_LEFT);
                    employeBox.setPrefSize(110, 30);

                    JFXButton deleteButton = new JFXButton("");
                    deleteButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent e) {
                            renderDeleteEmployee(event, employeBox);
                        }
                    });
                    Label empName = new Label(event.getName());
                    empName.setPrefWidth(90);

                    // Set Icon
                    FontIcon icon = new FontIcon("mdi-account-remove");
                    icon.setFill(Paint.valueOf("#E76E54"));

                    deleteButton.setGraphic(icon);
                    employeBox.getChildren().addAll(empName, deleteButton);
                    employeeList.getChildren().add(employeBox);
                }
            }
        }
    }

    private void initPopup() {
        dialog = new JFXDialog();
        dialog.setDialogContainer(stackPane);
        content = new JFXDialogLayout();

        // create dialog content
        // Search bar
        JFXTextField searchBar = new JFXTextField();
        searchBar.setPromptText("Enter Employee Name");
        searchBar.setFocusColor(Paint.valueOf("#009688"));
        searchBar.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Thread makeRequest = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        renderSearchEmployee(searchBar.getText());
                    }
                });

                makeRequest.run();
            }
        });

        // body
        VBox bodyConainer = new VBox();

        // Scorll pane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setPrefSize(400, 300);
        emplListContainer = new VBox(5); // spacing 5
        emplListContainer.setPadding(new Insets(5));
        scrollPane.setContent(emplListContainer);

        bodyConainer.getChildren().add(scrollPane);

        // Add to dialog
        content.setHeading(searchBar);
        content.setBody(bodyConainer);
        dialog.setContent(content);
    }

    public void handleAddEmployee() {
        dialog.show();
    }

    private void renderSearchEmployee(String search) {
        GetRequestModel request = new GetRequestModel();

        ArrayList<Employee> datas = request.makeRequest("/manager/employee", Employee.class, "name=" + search);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                emplListContainer.getChildren().clear();
                for (Employee data : datas) {
                    HBox temp = new HBox();
                    temp.setPadding(new Insets(5));
                    temp.setStyle("-fx-background-color: white;-fx-background-radius: 10;");
                    if (eventData.size() > 1) // prevent empty event
                        if (eventData.stream().filter(e -> e.getEssn() != null).anyMatch(e -> e.getEssn().equals(data.getSsn())))
                            continue;
                    Label empName = new Label(data.getName());
                    empName.setPrefWidth(300);
                    empName.setAlignment(Pos.CENTER_LEFT);

                    JFXButton btn = new JFXButton("Add");
                    btn.setStyle("-fx-background-color: #009688;-fx-text-fill: white;");
                    btn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            renderAddEmployee(data, temp);
                        };
                    });
                    temp.getChildren().addAll(empName, btn);

                    emplListContainer.getChildren().add(temp);
                }
            }
        });
    }

    private void renderAddEmployee(Employee emp, HBox box) {
        String ssn = emp.getSsn();
        int eventID = eventData.get(0).getEventID();
        String date = eventData.get(0).getDate() == null
                ? Tool.convertDateToString(
                        Tool.plusOrMinusDay(Tool.convertStringtoDate(CalendarManagementController.date), weekDay, 1))
                : Tool.convertDateToString(eventData.get(0).getDate());
        String paramter = String.format("ssn=%s&eventID=%s&date=%s", ssn, eventID, date);
        System.out.println(paramter);

        new Thread (new Runnable(){
            public void run() {
                PostRequestModel request = new PostRequestModel();
                request.makeRequest("/manager/calendar/event", paramter);
            };
        }).run();;

        EventInstance p = new EventInstance();
        p.setDate(Tool.convertStringtoDate(date));
        p.setName(emp.getName());
        p.setEssn(ssn);
        p.setEventID(eventID);

        eventData.add(p);
        employeeList.getChildren().clear();
        emplListContainer.getChildren().remove(box);
        updateUI();
    }
    
    private void renderDeleteEmployee(EventInstance event, HBox box) {
        String ssn = event.getEssn();
        int eventID = event.getEventID();
        String date = Tool.convertDateToString(event.getDate());
        String paramter = String.format("ssn=%s&eventID=%s&date=%s", ssn, eventID, date);
        System.out.println(paramter);

        new Thread(new Runnable(){
            public void run() {
                DeleteRequestModel request = new DeleteRequestModel();
                request.makeRequest("/manager/calendar/event", paramter);
            };
        }).run();

        employeeList.getChildren().remove(box);
    }
}
