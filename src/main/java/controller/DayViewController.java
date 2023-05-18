package controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.TreeSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.users.Users;


public class DayViewController {
    private Day model;
    private Task selectedTask=null;
    private FreeZone selectedZone=null;
    private Stage currentStage;
    private Calendar calendarModel;
    private Users usersModel;

    public void setSelectedTask(Task selectedTask) {
        this.selectedTask = selectedTask;
        if(selectedTask!=null){
            selectedTaskName.setText(selectedTask.getName());
        }
        else{
            selectedTaskName.setText("Not Selected");
        }
    }

    public void setSelectedZone(FreeZone zone){
        this.selectedZone = zone;
        selectedTimeSlot.setText("From "+zone.getStartTime().toString()+" to "+zone.getEndTime().toString());
    }

    private int numberOfTasks=0;
    private int numberOfDoneTasks=0;

    @FXML
    private Button unscheduleButton;

    @FXML
    private Button addNewTimeSlotButton;


    @FXML
    private Button removeTimeSlotButton;

    @FXML
    private Button scheduleTaskButton;


    @FXML
    private Label selectedTimeSlot;

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
    void gotoProjects(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/projects.fxml"));
        Parent root = loader.load();

        ProjectsController controller = loader.getController();
        controller.setUsersModel(usersModel);
        controller.setCalendarModel(calendarModel);
        // other models

        Scene scene = new Scene(root);
        controller.setCurrentStage(currentStage);
        currentStage.setScene(scene);
        currentStage.setTitle("Planify - MEDJADJ & ABOUD - 2023 : Projects");
        currentStage.setResizable(false);
        currentStage.show();
    }

    @FXML
    void gotoStatistics(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/statistics.fxml"));
        Parent root = loader.load();

        StatisticsController controller = loader.getController();
        controller.setUsersModel(usersModel);
        controller.setCalendarModel(calendarModel);
        // other models

        Scene scene = new Scene(root);
        controller.setCurrentStage(currentStage);
        currentStage.setScene(scene);
        currentStage.setTitle("Planify - MEDJADJ & ABOUD - 2023 : Statistics");
        currentStage.setResizable(false);
        currentStage.show();
    }

    @FXML
    void viewDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/dashboard.fxml"));
        Parent root = loader.load();

        DashboardController controller = loader.getController();
        controller.setUsersModel(usersModel);
        controller.setCalendarModel(calendarModel);
        // other models

        Scene scene = new Scene(root);
        controller.setCurrentStage(currentStage);
        currentStage.setScene(scene);
        currentStage.setTitle("Planify - MEDJADJ & ABOUD - 2023 : Dashboard");
        currentStage.setResizable(false);
        currentStage.show();
    }


    @FXML
    private Label comment;


    @FXML
    void viewToday(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/dayView.fxml"));
        Parent root = loader.load();

        DayViewController controller = loader.getController();
        controller.setModel(calendarModel, LocalDate.now());
        controller.setUsersModel(usersModel);
        // other models

        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.setTitle("Planify - MEDJADJ & ABOUD - 2023 : TodayView");
        currentStage.setResizable(false);
        controller.setCurrentStage(currentStage);
        currentStage.show();
    }

    @FXML
    private ProgressBar progressBar=new ProgressBar();



    public void setModel(Calendar calendar, LocalDate currentDate) {
        calendarModel = calendar;
        this.model=calendar.getDay(currentDate);
        if (model == null){
            model= new Day(currentDate);
            calendar.addDay(model);
        }

        setCurrentDate(currentDate);
        removeTimeSlotButton.setDisable(true);
        fillDayBox(model);
        selectedTaskName.setText("Not Selected");
        selectedTimeSlot.setText("Not Selected");
        taskViewButton.setDisable(true);
        unscheduleButton.setDisable(true);

    }






    public void fillDayBox(Day model){
        dayBox.getChildren().clear();
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
                    setSelectedZone(zn);
                    taskViewButton.setDisable(false);
                    unscheduleButton.setDisable(false);
                    removeTimeSlotButton.setDisable(false);
                });
            }
            else{
                label.setOnMouseClicked(event -> {
                    setSelectedZone(zn);
                    taskViewButton.setDisable(true);
                    unscheduleButton.setDisable(true);
                    removeTimeSlotButton.setDisable(false);
                    setSelectedTask(null);
                });
                label.setBackground(backgroundBlue);
                label.setText("Free Time");
            }
            dayBox.getChildren().add(label);

        }
        setProgressState();
    }

    public void setProgressState(){
        comment.setStyle("-fx-text-fill: blue;");
        comment.setText("A journey with a thousand miles begins with a single step");
        progressBar.setProgress(0);
        if(numberOfTasks==0){
            comment.setText("Hooray ! no tasks for this day, you can still add some right away");
            comment.setStyle("-fx-text-fill: Green;");
            progressBar.setProgress(0);
        }
        else{
            progressBar.setProgress(((double)numberOfDoneTasks)/((double)numberOfTasks));
            if(numberOfTasks==numberOfDoneTasks){
                comment.setText("Well done ! all tasks finished");
                comment.setStyle("-fx-text-fill: Green;");
            }else if(numberOfDoneTasks>=1){
                comment.setText("You have "+(numberOfTasks-numberOfDoneTasks)+" out of "+numberOfTasks+" tasks done");
                comment.setStyle("-fx-text-fill: Black;");
            }
        }
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void setUsersModel(Users usersModel) {
        this.usersModel = usersModel;
    }


    @FXML
    void addNewTimeSlot(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/addTimeSlot.fxml"));
        Parent root = fxmlLoader.load();

        AddTimeSlotController addTimeSlotController = fxmlLoader.getController();
        addTimeSlotController.setModel(calendarModel.getDay((model.getDate())),calendarModel,this);

        Scene scene = new Scene(root);
        Stage newStage= new Stage();

        newStage.setScene(scene);
        newStage.setTitle("Add new Time slot");
        newStage.setResizable(false);
        addTimeSlotController.setCurrentStage(newStage);
        newStage.show();
    }

    @FXML
    void goToTask(ActionEvent event) {
        System.out.println("went to task : "+selectedTaskName);
    }


    @FXML
    void removeTimeSlot(ActionEvent event) {
        if(selectedZone instanceof OccupiedZone){
            selectedTask.setUnscheduled(true);
            calendarModel.getUnscheduled().add(selectedTask);
            selectedTask=null;
            model.removeZone(selectedZone);
            removeTimeSlotButton.setDisable(true);
            setSelectedTask(null);
            selectedTimeSlot.setText("Not Selected");
            selectedZone=null;

            fillDayBox(model);
        }else{
            model.removeZone(selectedZone);
            removeTimeSlotButton.setDisable(true);
            selectedZone=null;
            selectedTimeSlot.setText("Not Selected");
            fillDayBox(model);
        }
    }


    @FXML
    void scheduleTask(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/view/scheduleTask.fxml"));
        Parent root = fxmlLoader.load();

        ScheduleTaskController scheduleTaskController = fxmlLoader.getController();
        scheduleTaskController.setModel(calendarModel,calendarModel.getDay(model.getDate()),selectedZone,this);

        Scene scene = new Scene(root);
        Stage newStage= new Stage();

        newStage.setScene(scene);
        newStage.setTitle("Schedule a task");
        newStage.setResizable(false);
        scheduleTaskController.setCurrentStage(newStage);
        newStage.show();
    }

    @FXML
    void unscheduleTask(ActionEvent event) {
        model.unAppendTask(selectedTask);
        calendarModel.getUnscheduled().add(selectedTask);
        fillDayBox(model);
    }



}
