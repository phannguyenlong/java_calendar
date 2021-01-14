package com.sql_calendar;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The first Frame of Application
 * @author Long Phan
 */
public class MainFrame extends Application implements EventHandler<ActionEvent> {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(loader.load());
    
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        // primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }  

    @Override
    public void handle(ActionEvent event) {
        System.out.println("click the butotn");
    }

    // public static void main(String[] args) {
    //     launch(args);
    // }
}
