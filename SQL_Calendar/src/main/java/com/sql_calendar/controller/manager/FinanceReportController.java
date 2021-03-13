package com.sql_calendar.controller.manager;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.sql_calendar.resources.FinanceIncome;
import com.sql_calendar.util.GetRequestModel;
import com.sql_calendar.util.Tool;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 * Controller for Finance report
 * @author Vinh Nguyen
 */
public class FinanceReportController implements Initializable {
    private Date date;
    @FXML
    JFXComboBox<Label> typeOption;
    @FXML
    AreaChart<String, Number> chart;
    @FXML
    JFXButton nextButton, prevButton;
    @FXML
    Label dayLabel, sumLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date = new Date();

        typeOption.getItems().add(new Label("Day"));
        typeOption.getItems().add(new Label("Week"));
        typeOption.getItems().add(new Label("Month"));
        typeOption.getSelectionModel().selectFirst();

        renderChart("Day"); 
    }

    // Change View 
    public void handleChangeOption() {
        renderChart(typeOption.getValue().getText());
    }

    // Change day
    public void handleChangeDay(ActionEvent event) {
        String option = typeOption.getValue().getText();

        if (option.equals("Day"))
            date = event.getSource() == nextButton ? Tool.plusOrMinusDay(date, 1, 1) : Tool.plusOrMinusDay(date, 1, 0);
        else if (option.equals("Week"))
            date = event.getSource() == nextButton ? Tool.plusOrMinusDay(Tool.getFirstDayofWeek(date), 7, 1)
                    : Tool.plusOrMinusDay(Tool.getFirstDayofWeek(date), 7, 0);
        else if (option.equals("Month"))
            date = event.getSource() == nextButton ? Tool.getFirstDayofNextMonth(date)
                    : Tool.getFirstDayofPrevMonth(date);
        renderChart(option);
    }

    private void renderChart(String option) {
        chart.getData().clear();
        sumLabel.setText("Sum: 0$");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = null;

                if (option.equals("Day")) {
                    path = "/manager/finance/day";
                    dayLabel.setText(Tool.convertDateToString(date));
                } else if (option.equals("Week")) {
                    path = "/manager/finance/week";
                    dayLabel.setText(String.format("(%s - %s)", Tool.convertDateToString(Tool.getFirstDayofWeek(date)),
                            Tool.convertDateToString(Tool.plusOrMinusDay(Tool.getFirstDayofWeek(date), 6, 1))));
                } else if (option.equals("Month")) {
                    path = "/manager/finance/month";
                    String[] tmp = Tool.convertDateToString(date).split("\\/");
                    dayLabel.setText(String.format("%s - %s", tmp[0], tmp[2]));
                }

                GetRequestModel request = new GetRequestModel();
                ArrayList<FinanceIncome> datas = request.makeRequest(path, FinanceIncome.class,
                        "date=" + Tool.convertDateToString(date));

                if (!datas.isEmpty()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int start = 0, end = 0;
                            if (option.equals("Day")) {
                                start = 0;
                                end = 23;
                            } else if (option.equals("Week")) {
                                start = 1;
                                end = 8;
                            } else if (option.equals("Month")) {
                                start = 1;
                                end = 6;
                            }

                            XYChart.Series<String, Number> serires = new XYChart.Series<String, Number>();
                            int counter = 0;
                            double sum = 0;
                            for (int i = start; i < end; i++) {
                                if (datas.get(counter).getNumber() == i) {
                                    serires.getData()
                                            .add(new XYChart.Data<String, Number>(
                                                    String.valueOf(datas.get(counter).getNumber()),
                                                    datas.get(counter).getTotal()));
                                    sum += datas.get(counter).getTotal();
                                    counter += counter == (datas.size() - 1) ? 0 : 1;
                                } else {
                                    serires.getData().add(new XYChart.Data<String, Number>(String.valueOf(i), 0));
                                }
                            }
                            chart.getData().add(serires);
                            sumLabel.setText(String.format("Sum: %.2f$", sum));
                        }
                    });
                }
            }
        }).run();
    }
}
