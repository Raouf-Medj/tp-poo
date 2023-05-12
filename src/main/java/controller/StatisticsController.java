package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import model.users.*;

import java.io.IOException;

public class StatisticsController {
    private Stage currentStage;
    private Users usersModel;
    // other models

    @FXML
    private MenuBar menuBar;

    @FXML
    void viewDashboard(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/dashboard.fxml"));
        Parent root = loader.load();

        DashboardController controller = loader.getController();
        controller.setUsersModel(usersModel);
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
    void viewToday(ActionEvent event) {

    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void setUsersModel(Users usersModel) {
        this.usersModel = usersModel;
    }
}
