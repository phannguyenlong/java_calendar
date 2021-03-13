package com.sql_calendar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The first Frame of Application
 * @author Long Phan
 */
public class MainFrame extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(loader.load());
    
        primaryStage.setTitle("Convenient Calendar Management");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("appIcon.png")));
        primaryStage.setResizable(false);
        primaryStage.show();
    } 
}
