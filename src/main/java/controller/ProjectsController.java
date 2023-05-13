package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import model.*;
import model.users.Users;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectsController {
    private Stage currentStage;
    private Users usersModel;
    private Calendar calendarModel;
    // other models

    @FXML
    private MenuBar menuBar;

    @FXML
    private ListView<Project> projectsList;

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
        // stay in same page
    }

    @FXML
    void gotoStatistics(ActionEvent event) throws IOException{
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
    void viewToday(ActionEvent event) {

    }

    @FXML
    void gotoSelected(ActionEvent event) {
        Project selectedProject = projectsList.getSelectionModel().getSelectedItem();

        System.out.println(selectedProject);
        // redirect to projectView
    }

    @FXML
    void createNew(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/addproject.fxml"));
        Parent root = loader.load();

        AddController controller = loader.getController();
        controller.setCalendarModel(calendarModel);
        // other models

        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Add a new project");
        newStage.setResizable(false);
        controller.setCurrentStage(newStage);
        newStage.show();
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void setUsersModel(Users usersModel) {
        this.usersModel = usersModel;
    }

    public void setCalendarModel(Calendar calendarModel) {
        this.calendarModel = calendarModel;

        projectsList.setItems(calendarModel.getProjects());
        projectsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}
