package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import model.Calendar;
import model.*;

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

        if (selectedPlanning !=  null) {
            // for each task: add (plan) task to planning
        }
        else {
            // for each task:
            // iterate through the days from today till deadline to try and find a freezone, if none found and there is an empty day
            // prompt the user to add freezones, else if no empty days then throw exception and task stays unscheduled
        }
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