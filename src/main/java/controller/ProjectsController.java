package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import model.users.Users;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Properties;
import java.util.ResourceBundle;

public class ProjectsController {
    private Stage currentStage;
    private Users usersModel;
    private Calendar calendarModel;
    private Project currentProject;
    // other models

    @FXML
    private MenuBar menuBar;

    @FXML
    private Label progressPourcentage;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label projectDesc;

    @FXML
    private Label projectName;

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
        controller.init();
        // other models

        Scene scene = new Scene(root);
        controller.setCurrentStage(currentStage);
        currentStage.setScene(scene);
        currentStage.setTitle("Planify - MEDJADJ & ABOUD - 2023 : Statistics");
        currentStage.setResizable(false);
        currentStage.show();
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

    @FXML
    void gotoSelected(ActionEvent event) throws IOException{
        Project selectedProject = projectsList.getSelectionModel().getSelectedItem();

        System.out.println(selectedProject);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/projectView.fxml"));
        Parent root = loader.load();

        ProjectsController controller = loader.getController();
        controller.setUsersModel(usersModel);
        controller.setCurrentProject(selectedProject);
        controller.setCalendarModel(calendarModel);
        // other models

        Scene scene = new Scene(root);
        controller.setCurrentStage(currentStage);
        currentStage.setScene(scene);
        currentStage.setTitle("Planify - MEDJADJ & ABOUD - 2023 : ProjectView");
        currentStage.setResizable(false);
        currentStage.show();
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

        if (projectsList !=  null) {
            projectsList.setItems(calendarModel.getProjects());
            projectsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        }

        if (projectName !=  null) {
            projectName.setText(currentProject.toString());
            projectDesc.setText(currentProject.getDescription());
            progressPourcentage.setText(""+((int) currentProject.getProgress()*100));
            progressBar.setProgress(currentProject.getProgress());
        }
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }
}
