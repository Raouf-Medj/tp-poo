package controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.skin.ProgressBarSkin;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.*;

import java.time.LocalDate;
import java.util.TreeSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class DayViewController {
    private Day model;
    private Task selectedTask=null;

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
        selectedTaskName.setText(selectedTask.getName());
    }

    private int numberOfTasks=0;
    private int numberOfDoneTasks=0;

    @FXML
    private VBox dayBox;

    @FXML
    private Label currentDate;

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate.setText(currentDate.toString());
    }

    @FXML
    private Label selectedTaskName;



    @FXML
    private Button taskViewButton;


    @FXML
    private MenuBar menuBar;

    @FXML
    void gotoProjects(ActionEvent event) {

    }

    @FXML
    void gotoStatistics(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void newPlanning(ActionEvent event) {

    }

    @FXML
    void newProject(ActionEvent event) {

    }

    @FXML
    void newTask(ActionEvent event) {

    }

    @FXML
    void viewDashboard(ActionEvent event) {

    }


    @FXML
    private Label comment;


    @FXML
    void viewToday(ActionEvent event) {

    }

    @FXML
    private ProgressBar progressBar;



    public void setModel(Calendar calendar, LocalDate currentDate) {

        this.model=calendar.getDay(currentDate);
        if (model == null){
            model= new Day(currentDate);
        }
        setCurrentDate(currentDate);
        fillDayBox(model);
        selectedTaskName.setText("Not Selected");
        taskViewButton.setDisable(true);

    }



    @FXML
    void goToTask(ActionEvent event) {
        System.out.println("went to task : "+selectedTaskName);
    }


    public void fillDayBox(Day model){
        TreeSet<FreeZone> zones = model.getZones();
        double originalPosition =0;
        for(FreeZone zn : zones){
           /* Rectangle zoneRepresentation = new Rectangle(200,((double)zn.getDuration().toMinutes())*31.0/60.0);
            zoneRepresentation.setTranslateY((((double)zn.getStartTime().getHour())+((double)zn.getStartTime().getMinute())/60.0)*31.0-originalPosition);
            originalPosition+=((double)zn.getDuration().toMinutes())*31.0/60.0;
            if (zn instanceof OccupiedZone){
                zoneRepresentation.setFill(Color.rgb(224,166,108));
            }
            else{
                zoneRepresentation.setFill(Color.rgb(101,170,194));
            }
            dayBox.getChildren().add(zoneRepresentation);*/
            Label label= new Label("");

            label.setPrefWidth(200);
            label.setAlignment(Pos.CENTER);
            label.setPrefHeight(((double)zn.getDuration().toMinutes())*31.0/60.0);
            label.setTranslateY((((double)zn.getStartTime().getHour())+((double)zn.getStartTime().getMinute())/60.0)*31.0-originalPosition);
            originalPosition+=((double)zn.getDuration().toMinutes())*31.0/60.0;
            BackgroundFill backgroundFillOrange = new BackgroundFill(Color.rgb(224,166,108), new CornerRadii(10), Insets.EMPTY);
            Background backgroundOrange = new Background(backgroundFillOrange);
            BackgroundFill backgroundFillBlue = new BackgroundFill(Color.rgb(101,170,194), new CornerRadii(10), Insets.EMPTY);
            Background backgroundBlue = new Background(backgroundFillBlue);
            if (zn instanceof OccupiedZone){
                numberOfTasks++;
                //if(((OccupiedZone) zn).getTask().getState()==completed){
                //numberOfDoneTasks++
                label.setBackground(backgroundOrange);
                label.setText(((OccupiedZone) zn).getName());
                label.setOnMouseClicked(event -> {
                    setSelectedTask(((OccupiedZone) zn).getTask());
                    setSelectedTask(((OccupiedZone) zn).getTask());
                    taskViewButton.setDisable(false);
                });
            }
            else{
                label.setBackground(backgroundBlue);
                label.setText("Free Time");
            }
            dayBox.getChildren().add(label);

        }
        setProgressState();

    }

    public void setProgressState(){
        comment.setText("A journey with a thousand miles begins with a single step");
        if(numberOfTasks==0){
            comment.setText("Hooray ! no tasks for this day, you can still add some right away");
        }
    }
}
