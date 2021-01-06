package com.sql_calendar;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainFrame extends Application implements EventHandler<ActionEvent> {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Calendar");

        Button button = new Button();
        button.setText("Click 1");
        button.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("fuck u");
            }
            
        });

        Button button2 = new Button();
        button2.setText("Click 2");
        button2.setOnAction(e -> {
            System.out.println("hel");
        });

        VBox layout = new VBox(20);
        layout.getChildren().add(button);
        layout.getChildren().add(button2);

        Scene scene = new Scene(layout, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.show();
    }  

    @Override
    public void handle(ActionEvent event) {
        System.out.println("click the butotn");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
