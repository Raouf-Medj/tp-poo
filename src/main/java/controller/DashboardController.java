package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import model.users.*;

import java.io.IOException;
import java.nio.file.FileSystemAlreadyExistsException;
import java.time.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    Stage currentStage;
    private Users usersModel;
    // other models

    private LocalDate toView;

    @FXML
    private Label month;

    @FXML
    private ListView<Task> unscheduledTasksList;

    @FXML
    private Label username;

    @FXML
    private Label year;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toView = LocalDate.now();
        month.setText(toView.getMonth().toString());
        year.setText(toView.getYear()+"");
    }

    public void setUsersModel(Users usersModel) {
        this.usersModel = usersModel;
        username.setText(usersModel.getActiveUser().getPseudo());
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    @FXML
    void viewDashboard(ActionEvent event) {
        // do nothing, you are already in dashboard
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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/statistics.fxml"));
        Parent root = loader.load();

        StatisticsController controller = loader.getController();
        controller.setUsersModel(usersModel);
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
    void logout(ActionEvent event) throws IOException {

        // stuff to do before changing the scene (saving models to a file + resetting activeUser to null)

        usersModel.setActiveUser(null);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/login.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setModel(usersModel);

        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.setScene(scene);
        currentStage.setTitle("Login");
        currentStage.setResizable(false);
        currentStage.show();
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
    void nextMonth(ActionEvent event) {
        toView = toView.plusMonths(1);
        month.setText(toView.getMonth().toString());
        year.setText(toView.getYear()+"");
    }

    @FXML
    void prevMonth(ActionEvent event) {
        toView = toView.minusMonths(1);
        month.setText(toView.getMonth().toString());
        year.setText(toView.getYear()+"");
    }

}

