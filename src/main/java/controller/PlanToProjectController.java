package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import model.Calendar;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanToProjectController {
    private Stage currentStage;
    private Calendar calendarModel;
    private ObservableList<Task> selectedTasks;

    @FXML
    private ChoiceBox<Planning> planningChoiceBox;

    @FXML
    private ChoiceBox<Project> projectChoiceBox;

    @FXML
    void plan(ActionEvent event) {
        Planning selectedPlanning = planningChoiceBox.getValue();
        Project selectedProject = projectChoiceBox.getValue();

        if (selectedProject !=  null) {
            for (Task task : selectedTasks) task.setProject(selectedProject);
            selectedProject.getTasks().addAll(selectedTasks);
        }
//        calendarModel.getUnscheduled().removeAll(selectedTasks);
//        List<Task> remaining = new ArrayList<>();

        if (selectedPlanning !=  null) {
            List<Task> unscheduled = calendarModel.fillPlanning(selectedPlanning, new ArrayList<>(selectedTasks), calendarModel.getMinDuration());
            if (!unscheduled.isEmpty()) {
//                remaining.addAll(unscheduled);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("No free timeslots found");
                alert.setContentText("Some tasks were not planned, please extend the planning or add more free timeslots");
                alert.showAndWait();
            }
        }
        else {
            // for each task:
            // iterate through the days from today till deadline to try and find a freezone, if none found inform the user, so that he adds some
            List<Task> unscheduled = selectedTasks;
            for (Task task : selectedTasks) {
                if (unscheduled.isEmpty()) continue;
                Planning temp = new Planning(LocalDate.now(), task.getDeadLine().toLocalDate(), calendarModel);
                calendarModel.addPlanning(temp);

                List<Task> left = calendarModel.fillPlanning(temp, new ArrayList<>(unscheduled), calendarModel.getMinDuration());
                unscheduled.retainAll(left);

                calendarModel.removePlanning(temp);
            }
            if (!unscheduled.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("No free timeslots found");
                alert.setContentText("Some tasks were not planned, please extend the planning or add more free timeslots");
                alert.showAndWait();
            }
        }
        currentStage.close();
//        calendarModel.getUnscheduled().addAll(unscheduled);
    }

    @FXML
    void cancel(ActionEvent event) {
        currentStage.close();
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void setCalendarModel(Calendar calendarModel) {
        this.calendarModel = calendarModel;
        projectChoiceBox.getItems().addAll(calendarModel.getProjects());
        planningChoiceBox.getItems().addAll(calendarModel.getPlannings());
    }

    public void setSelectedTasks(ObservableList<Task> selectedTasks) {
        this.selectedTasks = selectedTasks;
    }
}