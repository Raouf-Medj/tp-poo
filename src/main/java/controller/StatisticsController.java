package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Calendar;
import model.users.*;

import java.io.IOException;
import java.time.LocalDate;

public class StatisticsController {
    private Stage currentStage;
    private Users usersModel;
    private Calendar calendarModel;
    // other models

    @FXML
    private Label avgProd;

    @FXML
    private HBox badges;

    @FXML
    private Label bestDay;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Label nbStreaks;

    @FXML
    private PieChart pieChart;

    @FXML
    void viewDashboard(ActionEvent event) throws IOException{
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
    void gotoProjects(ActionEvent event) throws IOException {
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
    void gotoStatistics(ActionEvent event) throws IOException{
        // stay in same page
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

    public void init() {
        // initialize all labels and HBox and pie chart
        // add listeners
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void setUsersModel(Users usersModel) {
        this.usersModel = usersModel;
    }

    public void setCalendarModel(Calendar calendarModel) {
        this.calendarModel = calendarModel;
    }
}
