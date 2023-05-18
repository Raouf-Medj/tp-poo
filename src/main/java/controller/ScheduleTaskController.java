package controller;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import model.Exceptions.BeyondDeadlineException;
import model.Exceptions.NotFitInDayExeception;
import model.Exceptions.NotFitInZoneException;

import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ScheduleTaskController implements Initializable {
    private DayViewController dayViewController;
    private Task selectedTask;

    public void setSelectedTask(Task task){
        selectedTask=task;
    }

    private Day day;
    private FreeZone selectedZone;
    private Calendar calendar;

    Stage currentStage;
    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @FXML
    private Button cancelButton;

    public void setModel(model.Calendar calendar,Day day ,FreeZone selectedZone,DayViewController controller){
        this.dayViewController=controller;
        this.calendar=calendar;
        this.selectedZone=selectedZone;
        this.day=day;
        taskList.setItems(calendar.getUnscheduled());
        calendar.getUnscheduled().addListener((ListChangeListener<Task>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Task task : change.getAddedSubList()) {
                        if (!taskList.getItems().contains(task)) {
                            taskList.getItems().add(task);
                        }
                    }
                }
                if (change.wasRemoved()) {
                    for (Task task : change.getRemoved()) {
                        if (taskList.getItems().contains(task)) {
                            taskList.getItems().remove(task);
                        }
                    }
                }
            }
        });
        setDurationCheck.setOnAction(event->{
            if(setDurationCheck.isSelected()){
                setDurationMinutes.setDisable(false);
                setDurationHours.setDisable(false);
            }
            else {
                setDurationMinutes.setDisable(true);
                setDurationHours.setDisable(true);
            }
        });

        setInsertionTimeCheck.setOnAction(event->{
            if(setInsertionTimeCheck.isSelected()){
                setInsertionTimeMinutes.setDisable(false);
                setInsertionTimeHours.setDisable(false);
            }
            else {
                setInsertionTimeMinutes.setDisable(true);
                setInsertionTimeHours.setDisable(true);
            }
        });

        insertInZoneCheck.setOnAction(event -> {
            if (insertInZoneCheck.isSelected()) {
                setDurationCheck.setDisable(true);
                setDurationHours.setDisable(true);
                setDurationMinutes.setDisable(true);
                setInsertionTimeCheck.setDisable(true);
                setInsertionTimeHours.setDisable(true);
                setInsertionTimeMinutes.setDisable(true);
            } else {
                addTaskButton.setDisable(true);
            }
        });

        taskList.setOnMouseClicked(event -> {
            setSelectedTask(taskList.getSelectionModel().getSelectedItem());
            if(selectedZone==null){
                insertInZoneCheck.setDisable(true);
            }
            else {
                insertInZoneCheck.setDisable(false);
            }
            if(selectedTask == null){

                setDurationCheck.setDisable(true);
                setDurationHours.setDisable(true);
                setDurationMinutes.setDisable(true);
                setInsertionTimeCheck.setDisable(true);
                setInsertionTimeHours.setDisable(true);
                setInsertionTimeMinutes.setDisable(true);
                addTaskButton.setDisable(true);
            }
            else if (selectedTask instanceof ComplexTask){
                setDurationCheck.setDisable(false);
                setDurationHours.setDisable(false);
                setDurationMinutes.setDisable(false);
                setInsertionTimeCheck.setDisable(false);
                setInsertionTimeHours.setDisable(false);
                setInsertionTimeMinutes.setDisable(false);
                addTaskButton.setDisable(false);
            }
            else if (selectedTask instanceof SimpleTask){
                setDurationCheck.setDisable(true);
                setDurationHours.setDisable(true);
                setDurationMinutes.setDisable(true);
                setInsertionTimeCheck.setDisable(false);
                setInsertionTimeHours.setDisable(false);
                setInsertionTimeMinutes.setDisable(false);
                addTaskButton.setDisable(false);
            }
        });



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        insertInZoneCheck.setDisable(true);
        setDurationCheck.setDisable(true);
        setDurationHours.setDisable(true);
        setDurationMinutes.setDisable(true);
        setInsertionTimeCheck.setDisable(true);
        setInsertionTimeHours.setDisable(true);
        setInsertionTimeMinutes.setDisable(true);
        addTaskButton.setDisable(true);

        taskList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        SpinnerValueFactory<Integer> sitm = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        sitm.setValue(0);
        setInsertionTimeMinutes.setValueFactory(sitm);

        SpinnerValueFactory<Integer> sith = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        sith.setValue(0);
        setInsertionTimeHours.setValueFactory(sith);

        SpinnerValueFactory<Integer> sdh = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        sdh.setValue(0);
        setDurationHours.setValueFactory(sitm);

        SpinnerValueFactory<Integer> sdm = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        sdm.setValue(0);
        setDurationMinutes.setValueFactory(sitm);

    }

    @FXML
    void cancel(ActionEvent event) {
        currentStage.close();
    }




    @FXML
    private Button addTaskButton;

    @FXML
    private CheckBox insertInZoneCheck;

    @FXML
    private CheckBox setDurationCheck;

    @FXML
    private Spinner<Integer> setDurationHours;

    @FXML
    private Spinner<Integer> setDurationMinutes;

    @FXML
    private CheckBox setInsertionTimeCheck;

    @FXML
    private Spinner<Integer> setInsertionTimeHours;

    @FXML
    private Spinner<Integer> setInsertionTimeMinutes;

    @FXML
    private ListView<Task> taskList;

    @FXML
    void addTask(ActionEvent event) {
        try{
            if(insertInZoneCheck.isSelected() && selectedZone != null){
                day.appendTask(selectedTask,calendar.getMinDuration(),selectedZone);
            }
            else{
                if(selectedTask instanceof SimpleTask){
                    if(setInsertionTimeCheck.isSelected()){
                        day.appendTask(selectedTask,calendar.getMinDuration(), LocalTime.of(setInsertionTimeHours.getValue(),setInsertionTimeMinutes.getValue()));
                    }
                    else{
                        day.appendTask(selectedTask,calendar.getMinDuration());
                    }
                }
                else{
                    if(setInsertionTimeCheck.isSelected()){
                        if(setDurationCheck.isSelected()){
                            day.appendTask(selectedTask,calendar.getMinDuration(), LocalTime.of(setInsertionTimeHours.getValue(),setInsertionTimeMinutes.getValue()), Duration.ofMinutes(setDurationHours.getValue()*60+setDurationMinutes.getValue()));
                        }
                        else{
                            day.appendTask(selectedTask,calendar.getMinDuration(), LocalTime.of(setInsertionTimeHours.getValue(),setInsertionTimeMinutes.getValue()));
                        }
                    }
                    else{
                        if(setDurationCheck.isSelected()){
                            day.appendTask(selectedTask,calendar.getMinDuration(),  Duration.ofMinutes(setDurationHours.getValue()*60+setDurationMinutes.getValue()));
                        }
                        else{
                            day.appendTask(selectedTask,calendar.getMinDuration());
                        }

                    }
                }
            }
            dayViewController.setStatus("Oll Korrect",false);
        }catch(BeyondDeadlineException e){
            dayViewController.setStatus(e.getMessage(),true);
        }catch(NotFitInDayExeception e){
            dayViewController.setStatus(e.getMessage(),true);
        }catch(NotFitInZoneException e){
            dayViewController.setStatus(e.getMessage(),true);
        }

        if(!selectedTask.getUnscheduled()){
            calendar.getUnscheduled().remove(selectedTask);
            dayViewController.incrementNumberOfTasks();
            dayViewController.setProgressState();
        }
        currentStage.close();
        dayViewController.fillDayBox(day);
    }

    @FXML
    void insertInZone(ActionEvent event) {

    }

    @FXML
    void setDuration(ActionEvent event) {

    }

    @FXML
    void setInsertionTime(ActionEvent event) {

    }

}