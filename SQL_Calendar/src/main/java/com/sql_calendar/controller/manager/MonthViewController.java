package com.sql_calendar.controller.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sql_calendar.resources.MonthView;
import com.sql_calendar.util.GetRequestModel;
import com.sql_calendar.util.Tool;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Controller for Month View
 * @author Long Phan
 */
public class MonthViewController implements Initializable {
    private CalendarManagementController parentController;
    @FXML
    VBox container;
    @FXML
    GridPane calendarContainer;
    @FXML
    Label monthIncome, monthLabel, yearLabel;
    @FXML
    JFXButton nextButton, prevButton;
    @FXML
    ImageView loadingIcon;

    public void setParentController(CalendarManagementController parentController) {
        this.parentController = parentController;

        renderMonthView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    private void renderMonthView() {
        loadingIcon.setVisible(true);
        calendarContainer.setDisable(true);
        Date date = Tool.getFirstDateOfMonth(Tool.convertStringtoDate(CalendarManagementController.date));
        // date = Tool.getFirstDateOfMonth(date);

        // Set value for Lable in Header and Footer
        monthLabel.setText(Tool.getMonthName(Tool.getDayMonthYear(date)[1]));
        yearLabel.setText("" + Tool.getDayMonthYear(date)[2]);
        monthIncome.setText("0 $");

        // Get first weekday of the Year
        int firstWeekDay = Tool.getDayofWeek(Tool.getFirstDateOfMonth(date));

        // Fill unused weekday before first day with grey background
        for (int i = 0; i < firstWeekDay - 1; i++) {
            Rectangle rect = new Rectangle(127, 65);
            rect.setFill(Color.web("#E9E9E9"));
            calendarContainer.add(rect, i, 0);
        }

        // Fill in day number for each day in month
        for (int i = firstWeekDay; i <= firstWeekDay + Tool.getNumberOfDayInMonth(date) - 1; i++) {
            Label day = new Label(String.valueOf(i - firstWeekDay + 1));
            day.getStyleClass().addAll("dayText");
            GridPane.setHalignment(day, HPos.RIGHT);
            GridPane.setValignment(day, VPos.TOP);
            calendarContainer.add(day, (i - 1) % 7, (i - 1) / 7);
            FontIcon icon = new FontIcon("mdi-open-in-app");
            icon.setFill(Paint.valueOf("#009688"));
            icon.setIconSize(22);

            JFXButton goTo = new JFXButton("");
            goTo.setPrefSize(127, 40);
            goTo.hoverProperty().addListener((ChangeListener<Boolean>) (observable, oldValue, newValue) -> {
                if (newValue) {
                    goTo.setGraphic(icon);
                } else {
                    goTo.setGraphic(null);
                }
            });
            goTo.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    String newDate = Tool.convertDateToString(Tool.plusOrMinusDay(date, Integer.parseInt(day.getText()) - 1, 1));
                    CalendarManagementController.date = newDate;
                    parentController.handleViewOptionforChild();
                }
            });;

            GridPane.setHalignment(goTo, HPos.CENTER);
            GridPane.setValignment(goTo, VPos.CENTER);
            calendarContainer.add(goTo, (i - 1) % 7, (i - 1) / 7);
        }

        // Fill unused weekdays after last dat with grey background
        for (int i = firstWeekDay + Tool.getNumberOfDayInMonth(date); i <= 42; i++) {
            Rectangle rect = new Rectangle(127, 65);
            rect.setFill(Color.web("#E9E9E9"));
            calendarContainer.add(rect, (i - 1) % 7, (i - 1) / 7);
        }

        // Make request to and fill in day with order
        GetRequestModel request = new GetRequestModel();
        Thread makeRequest = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<MonthView> datas = request.makeRequest("/manager/calendar/month", MonthView.class,
                        "date=" + CalendarManagementController.date);
                loadingIcon.setVisible(false);
                calendarContainer.setDisable(false);
                if (!datas.isEmpty())
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            for (MonthView data : datas) {
                                // System.out.println(data);
                                Date d = new Date(Long.parseLong(data.getDate()));
                                Label income = new Label(
                                        "Day Income: " + Math.round(Float.parseFloat(data.getDayIncome())) + " $");
                                income.getStyleClass().addAll("incomeText");
                                GridPane.setMargin(income, new Insets(0,0,3,0));
                                GridPane.setHalignment(income, HPos.CENTER);
                                GridPane.setValignment(income, VPos.BOTTOM);
                                calendarContainer.add(income, Tool.getDayofWeek(d) - 1, Tool.getWeekofMonth(d) - 1);
                            }
                            monthIncome.setText(
                                    Math.round(Float.parseFloat(datas.get(datas.size() - 1).getMonthIncome())) + " $");
                        }
                    });
            }
        });
        makeRequest.start();
    }

    /**
     * Handle when changing the month
     */
    public void handleChangeMonth(ActionEvent event) {
        Date date = Tool.convertStringtoDate(CalendarManagementController.date);
        CalendarManagementController.date = event.getSource() == nextButton
                ? Tool.convertDateToString(Tool.getFirstDayofNextMonth(date))
                : Tool.convertDateToString(Tool.getFirstDayofPrevMonth(date));

        // Clean the GridPane
        calendarContainer.setGridLinesVisible(false);
        calendarContainer.getColumnConstraints().clear();
        calendarContainer.getRowConstraints().clear();
        calendarContainer.getChildren().clear();
        calendarContainer.setGridLinesVisible(true);

        // Set Size for each cell in GridPane
        for (int i = 0; i < 5; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(16.6);
            calendarContainer.getRowConstraints().add(row);
        }

        renderMonthView();
    }
}
