package com.sql_calendar.controller.cashier;

import java.net.URL;
import java.util.ResourceBundle;

import com.sql_calendar.resources.Employee;

import javafx.fxml.Initializable;

public class CashingController implements Initializable {
    private Employee user;

    public void setUser(Employee user) {
        this.user = user;
        System.out.println(this.user);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Cashing controller");
    }
    
}
