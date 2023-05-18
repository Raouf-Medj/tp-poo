package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
import model.users.*;

import java.io.IOException;
import java.nio.file.FileSystemAlreadyExistsException;
import java.time.*;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.*;

public class DashboardController implements Initializable {
    Stage currentStage;
    private Users usersModel;
    private Calendar calendarModel;
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

    @FXML
    private GridPane monthGrid;

    @FXML
    private Spinner<Integer> goalSpinner;

    @FXML
    private ImageView badge;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (month !=  null) {
            toView = LocalDate.now();
            month.setText(toView.getMonth().toString());
            year.setText(toView.getYear()+"");
            monthGrid.getChildren().clear();
            drawCalendar();

            unscheduledTasksList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }
    }

    public void setUsersModel(Users usersModel) {
        this.usersModel = usersModel;
        username.setText(usersModel.getActiveUser().getPseudo());
    }

    public void setCalendarModel(Calendar calendarModel) {
        this.calendarModel = calendarModel;

        if(unscheduledTasksList !=  null) {
            unscheduledTasksList.setItems(calendarModel.getUnscheduled());

            // Add a listener to the tasks list to update the ListView
            calendarModel.getUnscheduled().addListener((ListChangeListener<Task>) change -> {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (Task task : change.getAddedSubList()) {
                            if (!unscheduledTasksList.getItems().contains(task)) {
                                unscheduledTasksList.getItems().add(task);
                            }
                        }
                    }
                    if (change.wasRemoved()) {
                        for (Task task : change.getRemoved()) {
                            if (unscheduledTasksList.getItems().contains(task)) {
                                unscheduledTasksList.getItems().remove(task);
                            }
                        }
                    }
                }
            });
        }
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
    void logout(ActionEvent event) throws IOException {

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
    void newPlanning(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/addplanning.fxml"));
        Parent root = loader.load();

        AddController controller = loader.getController();
        controller.setCalendarModel(calendarModel);
        // other models

        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Add a new planning");
        newStage.setResizable(false);
        controller.setCurrentStage(newStage);
        newStage.show();
    }

    @FXML
    void newProject(ActionEvent event) throws IOException{
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

    @FXML
    void newTask(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/addtask.fxml"));
        Parent root = loader.load();

        AddController controller = loader.getController();
        controller.setCalendarModel(calendarModel);
        // other models

        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Add a new task");
        newStage.setResizable(false);
        controller.setCurrentStage(newStage);
        newStage.show();
    }

    @FXML
    void nextMonth(ActionEvent event) {
        toView = toView.plusMonths(1);
        month.setText(toView.getMonth().toString());
        year.setText(toView.getYear()+"");
        monthGrid.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void prevMonth(ActionEvent event) {
        toView = toView.minusMonths(1);
        month.setText(toView.getMonth().toString());
        year.setText(toView.getYear()+"");
        monthGrid.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void planSelected(ActionEvent event) throws IOException {
        ObservableList<Task> selectedTasks = unscheduledTasksList.getSelectionModel().getSelectedItems();

        if (!selectedTasks.isEmpty()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/addToProjectConfirm.fxml"));
            Parent root = loader.load();

            PlanToProjectController controller = loader.getController();
            controller.setCalendarModel(calendarModel);
            controller.setSelectedTasks(selectedTasks);
            // other models

            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Plan selected tasks");
            newStage.setResizable(false);
            controller.setCurrentStage(newStage);
            newStage.show();
        }
    }

    @FXML
    void removeSelected(ActionEvent event) {
        ObservableList<Task> selectedTasks = unscheduledTasksList.getSelectionModel().getSelectedItems();

        calendarModel.getUnscheduled().removeAll(selectedTasks);
    }

    @FXML
    void changeMinTaskDuration(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/changeSettings.fxml"));
        Parent root = loader.load();

        DashboardController controller = loader.getController();
        controller.setCalendarModel(calendarModel);
        controller.initSpinner();
        // other models

        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.setTitle("Settings");
        newStage.setResizable(false);
        controller.setCurrentStage(newStage);
        newStage.show();
    }

    @FXML
    private Spinner<Integer> minutesSpinner;

    @FXML
    void updateDuration(ActionEvent event) {
        calendarModel.setMinDuration(Duration.ofMinutes(minutesSpinner.getValue()));
        calendarModel.setNbCompletedToCongratulate(goalSpinner.getValue());
        currentStage.close();
    }

    void initSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0, 5);
        valueFactory.setValue((int)calendarModel.getMinDuration().toMinutes());
        minutesSpinner.setValueFactory(valueFactory);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        valueFactory2.setValue(calendarModel.getNbCompletedToCongratulate());
        goalSpinner.setValueFactory(valueFactory2);
    }

    void drawCalendar() {

        int monthMaxDate = toView.getMonth().maxLength();
        //Check for leap year
        if(toView.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }

        //int dateOffset = ZonedDateTime.of(toView.getYear(), toView.getMonthValue(), 1,0,0,0,0,toView.getZone()).getDayOfWeek().getValue();
        int dateOffset = LocalDateTime.of(toView.getYear(), toView.getMonthValue(), 1, 0, 0).getDayOfWeek().getValue();

        for (int i = 1; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(1);
                rectangle.setWidth(119);
                rectangle.setHeight(88);
                rectangle.setArcWidth(20);
                rectangle.setArcHeight(20);
                monthGrid.add(rectangle, j, i);

                int calculatedDate = (j+1)+(7*(i-1));
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Label date = new Label(String.valueOf(currentDate));
                        double textTranslationY = - (44) * 0.75;
                        date.setTranslateY(textTranslationY);
                        date.setTranslateX(119*0.46);
                        monthGrid.add(date, j, i);

                        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                // temp
                                System.out.println(toView.getYear()+" - "+toView.getMonth()+" - "+currentDate);
                                Day day = calendarModel.getDay(LocalDate.of(toView.getYear(), toView.getMonthValue(), currentDate));
                                if (day !=  null) {
                                    for (FreeZone z : day.getZones()) {
                                        z.showZone();
                                    }
                                }
                                try {
                                    FXMLLoader loader = new FXMLLoader();
                                    loader.setLocation(getClass().getResource("/view/dayView.fxml"));
                                    Parent root = loader.load();

                                    DayViewController controller = loader.getController();
                                    controller.setModel(calendarModel, LocalDate.of(toView.getYear(), toView.getMonthValue(), currentDate));
                                    controller.setUsersModel(usersModel);
                                    // other models

                                    Scene scene = new Scene(root);
                                    currentStage.setScene(scene);
                                    currentStage.setTitle("Planify - MEDJADJ & ABOUD - 2023 : DayView");
                                    currentStage.setResizable(false);
                                    controller.setCurrentStage(currentStage);
                                    currentStage.show();
                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

//                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
//                        if(calendarActivities != null){
//                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
//                        }
                    }
                    if(LocalDate.now().getYear() == toView.getYear() && LocalDate.now().getMonth() == toView.getMonth() && LocalDate.now().getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.DODGERBLUE);
                        rectangle.setStrokeWidth(1.5);
                    }
                }

            }
        }
    }

}

