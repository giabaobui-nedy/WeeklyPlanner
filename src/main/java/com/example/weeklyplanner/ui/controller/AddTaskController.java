package com.example.weeklyplanner.ui.controller;

import com.example.weeklyplanner.application.AddTaskService;
import com.example.weeklyplanner.domain.enumeration.TaskStatus;
import com.example.weeklyplanner.domain.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

import com.example.weeklyplanner.domain.enumeration.TaskPriority;

public class AddTaskController {

    // FXML fields
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descArea;
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private ComboBox<TaskPriority> priorityBox;
    @FXML
    private ComboBox<TaskStatus> statusBox;
    @FXML
    private TextField estimateField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label errorLabel;

    private final AddTaskService addTaskService = new AddTaskService();

    @FXML
    private void initialize() {
        // Fill combos
        priorityBox.getItems().setAll(TaskPriority.values());
        statusBox.getItems().setAll(TaskStatus.values());
        // Defaults
        priorityBox.getSelectionModel().select(TaskPriority.MEDIUM);
        statusBox.getSelectionModel().select(TaskStatus.TODO);
        dueDatePicker.setValue(LocalDate.now());
        // BootstrapFX classes
        titleField.getStyleClass().add("form-control");
        descArea.getStyleClass().add("form-control");
        dueDatePicker.getStyleClass().add("form-control");
        priorityBox.getStyleClass().add("form-control");
        statusBox.getStyleClass().add("form-control");
        estimateField.getStyleClass().add("form-control");
        saveBtn.getStyleClass().addAll("btn", "btn-primary");
        cancelBtn.getStyleClass().addAll("btn", "btn-secondary");
        // Wire actions
        saveBtn.setOnAction(e -> onSave());
        cancelBtn.setOnAction(e -> close());
    }

    private void onSave() {
        // Obtain and validate inputs
        errorLabel.setText("");
        final String taskName = titleField.getText() == null ? "" : titleField.getText().trim();
        if (taskName.isEmpty()) {
            errorLabel.setText("Title is required.");
            titleField.requestFocus();
            return;
        }
        final String taskDescription = descArea.getText() == null ? "" : descArea.getText().trim();
        final LocalDate taskDueDate = dueDatePicker.getValue();
        final TaskPriority taskPriority = priorityBox.getValue();
        final TaskStatus taskStatus = statusBox.getValue();
        Integer taskEstimateMinutes = null;
        final String estText = estimateField.getText();
        if (estText != null && !estText.isBlank()) {
            try {
                int parsed = Integer.parseInt(estText.trim());
                if (parsed < 0) {
                    errorLabel.setText("Estimate must be ≥ 0.");
                    return;
                }
                taskEstimateMinutes = parsed;
            } catch (NumberFormatException ex) {
                errorLabel.setText("Estimate must be a number.");
                return;
            }
        }
        // Build entity (adapt to your TaskEntity fields/ctors)
        Task newTask = new Task(null, taskName, taskDescription, taskStatus, taskPriority, null, taskDueDate,
                taskEstimateMinutes);
        // Use service to add task
        try {
            addTaskService.addTask(newTask);
            resetForm();
        } catch (Exception ex) {
            // Show a friendly message; log real stacktrace elsewhere
            showErrorDialog("Could not save the task.\n" + ex.getMessage());
        }
    }

    private void resetForm() {
        titleField.setText("");
        descArea.setText("");
        dueDatePicker.setValue(null);
        priorityBox.getSelectionModel().select(TaskPriority.MEDIUM);
        statusBox.getSelectionModel().select(TaskStatus.TODO);
        estimateField.setText("");
        errorLabel.setText("");
    }

    private void close() {
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

    private void showErrorDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Save failed");
        alert.setContentText(msg);
        alert.initOwner(saveBtn.getScene().getWindow());
        alert.showAndWait();
    }
}