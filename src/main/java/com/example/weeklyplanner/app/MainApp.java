package com.example.weeklyplanner.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage s) {
        s.setScene(new Scene(new Label("It works!"), 320, 160));
        s.setTitle("Hello JavaFX");
        s.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}