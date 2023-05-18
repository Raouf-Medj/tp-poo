package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;
import model.users.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

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
        if (!calendarModel.getBadges().isEmpty()) {
            for (Badge badge : calendarModel.getBadges()) {
                ImageView image = new ImageView();
                image.setImage(new Image("/"+badge.toString()+".png"));
                image.setFitWidth(80);
                image.setPreserveRatio(true);
                badges.getChildren().add(image);
            }
        }
        else {
            Label label = new Label("No badges to show");
            label.setStyle("-fx-font-size: 20px;");
            badges.getChildren().add(label);
        }

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        Map<String, Integer> categMap = calendarModel.getCategories();
        for (Map.Entry<String, Integer> e : categMap.entrySet()) {
            chartData.add(new PieChart.Data(e.getKey(), e.getValue()));
        }

        double avg = 0;
        int cptStreaks = 0;
        LocalDate bestProd = null;
        Planning currentPlanning = calendarModel.getCurrentPlanning();
        if (currentPlanning !=  null) {
            bestProd = currentPlanning.getStartDay();
            double maxProd = 0;
            double cptDays = 0;
            for (Map.Entry<LocalDate, Day> e : currentPlanning.getDays().entrySet()) {
                double cpt = 0;
                double cptDone = 0;
                Day day = e.getValue();
                for (FreeZone zone : day.getZones()) {
                    if (zone instanceof OccupiedZone) {
                        Task task = ((OccupiedZone) zone).getTask();
                        if (task.getState().equals(State.COMPLETED)) cptDone++;
                        cpt++;
                    }
                }
                avg += cptDone / cpt;
                cptDays++;

                if (day.isGoalAchieved()) {
                    cptStreaks++;
                }

                if (cptDone / cpt > maxProd) {
                    maxProd = cptDone / cpt;
                    bestProd = day.getDate();
                }
            }
            avg = avg / cptDays;
        }
        avgProd.setText(""+((int) avg));
        nbStreaks.setText(""+cptStreaks);
        if (bestProd !=  null) bestDay.setText(bestProd.toString());
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
