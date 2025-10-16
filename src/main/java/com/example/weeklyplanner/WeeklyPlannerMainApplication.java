package com.example.weeklyplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class WeeklyPlannerMainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WeeklyPlannerMainApplication.class.getResource("add-task-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 900);
        stage.setTitle("Weekly Planner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}