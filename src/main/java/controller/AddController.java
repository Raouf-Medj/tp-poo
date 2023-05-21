package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.time.*;
import javafx.scene.layout.VBox;
import model.Calendar;
import model.exceptions.BeyondDeadlineException;
import model.exceptions.NotFitInDayExeception;
import model.exceptions.NotFitInZoneException;

public class AddController implements Initializable {
    private Stage currentStage;
    private Calendar calendarModel;

    // ----------- AddTask -----------
    @FXML
    private TextField taskCategory;

    @FXML
    private DatePicker taskDeadline;

    @FXML
    private CheckBox taskDecomposable;

    @FXML
    private TextField taskName;

    @FXML
    private Spinner<Integer> hourSpinner;

    @FXML
    private Spinner<Integer> minuteSpinner;

    @FXML
    private Spinner<Integer> taskPeriodicity;

    @FXML
    private ChoiceBox<Priority> taskPriority;

    // ----------- AddProject -----------
    @FXML
    private TextArea projectDesc;

    @FXML
    private TextField projectName;

    // ----------- AddPlanning -----------
    @FXML
    private DatePicker endDate;

    @FXML
    private DatePicker startDate;

    @FXML
    private VBox Vbox;

    private Planning newPlanning;

    private HashMap<LocalDate, Spinner<Integer>> startHoursInputs;
    private HashMap<LocalDate, Spinner<Integer>> endHoursInputs;

    // controller methods
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!(taskPeriodicity ==  null)) { // if we're in "new task"
            SpinnerValueFactory<Integer> valueFactoryPeriodicity = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30);
            valueFactoryPeriodicity.setValue(0);
            taskPeriodicity.setValueFactory(valueFactoryPeriodicity);
            SpinnerValueFactory<Integer> valueFactoryHours = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
            valueFactoryHours.setValue(1);
            hourSpinner.setValueFactory(valueFactoryHours);
            SpinnerValueFactory<Integer> valueFactoryMinutes = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
            valueFactoryMinutes.setValue(0);
            minuteSpinner.setValueFactory(valueFactoryMinutes);

            taskPeriodicity.valueProperty().addListener(new ChangeListener<Integer>() {
                @Override
                public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                    taskDecomposable.setDisable(taskPeriodicity.getValue() !=  0);
                }
            });

            taskPriority.getItems().addAll(Priority.values());
        }
    }

    @FXML
    void addTask(ActionEvent event) {
//        calendarModel.getUnscheduled().add(new SimpleTask("Task1"));
//        calendarModel.getUnscheduled().add(new SimpleTask("Task2"));
//        calendarModel.getUnscheduled().add(new SimpleTask("Task3"));
//        calendarModel.getUnscheduled().add(new SimpleTask("Task4"));

        Task taskToAdd;
        if (!taskDecomposable.isSelected()) {
            taskToAdd = new SimpleTask(taskName.getText(), new Category(taskCategory.getText(), Color.AQUA.toString()), taskPriority.getValue(), taskDeadline.getValue().atStartOfDay(), Duration.ofMinutes(hourSpinner.getValue()*60 + minuteSpinner.getValue()), taskPeriodicity.getValue());
        }
        else {
            taskToAdd = new ComplexTask(taskName.getText(), new Category(taskCategory.getText(), Color.BEIGE.toString()), taskPriority.getValue(), taskDeadline.getValue().atStartOfDay(), Duration.ofMinutes(hourSpinner.getValue()*60 + minuteSpinner.getValue()));

        }

        calendarModel.getUnscheduled().add(taskToAdd);
        System.out.println(calendarModel.getUnscheduled());
        currentStage.close();
    }

    @FXML
    void addProject(ActionEvent event) {

        Project project = new Project(projectName.getText(), projectDesc.getText());
        calendarModel.getProjects().add(project);
        currentStage.close();
    }

    @FXML
    void addPlanning(ActionEvent event) throws IOException {
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        if (start.isBefore(LocalDate.now()) || start.isAfter(end)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid date");
            alert.setContentText("Please enter valid dates");
            alert.showAndWait();
        }
        else {
            newPlanning = new Planning(start, end, calendarModel);
            Planning p = calendarModel.addPlanning(newPlanning);
            if (p !=  null) newPlanning = p;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/addfreezones.fxml"));
            Parent root = loader.load();

            AddController controller = loader.getController();
            controller.setCalendarModel(calendarModel);
            // other models

            controller.initVbox(newPlanning);
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Add free timeslots");
            currentStage.setResizable(false);
            controller.setCurrentStage(currentStage);
            currentStage.show();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        newPlanning = null;
        currentStage.close();
    }
    @FXML
    void decomposableAction(ActionEvent event) {
        taskPeriodicity.setDisable(taskDecomposable.isSelected());
    }

    @FXML
    void addFreezones(ActionEvent event) {

        boolean stop = false;
        for (Map.Entry<LocalDate, Day> e : newPlanning.getDays().entrySet()) {
            if (startHoursInputs.get(e.getKey()).getValue() >=  endHoursInputs.get(e.getKey()).getValue()) {
                stop = true;
            }
            if (stop) continue;
        }
        if (!stop) {
            for (Map.Entry<LocalDate, Day> e : newPlanning.getDays().entrySet()) {
                if (Day.isInsertable(e.getValue().getZones(), new FreeZone(LocalTime.of(startHoursInputs.get(e.getKey()).getValue(), 0), LocalTime.of(endHoursInputs.get(e.getKey()).getValue(), 0)))) {
                    try{
                        e.getValue().insertZone(new FreeZone(LocalTime.of(startHoursInputs.get(e.getKey()).getValue(), 0), LocalTime.of(endHoursInputs.get(e.getKey()).getValue(), 0)));
                    }catch(BeyondDeadlineException ex){

                    }catch(NotFitInDayExeception ex){

                    }catch(NotFitInZoneException ex){

                    }

                }
                else {
                    //ask the user if he wants to change the freezones of the day (extention?), but dont delete existing planned tasks!! (if time)
                    System.out.println("Feature to add later: check AddController.java:addFreezones()");
                }
            }
            currentStage.close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid timeslot");
            alert.setContentText("Please enter valid timeslots");
            alert.showAndWait();
        }
    }

    public void setCalendarModel(Calendar calendarModel) {
        this.calendarModel = calendarModel;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    private Spinner<Integer> createSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(valueFactory);
        return spinner;
    }

    void initVbox(Planning planning) {
        startHoursInputs = new HashMap<>();
        endHoursInputs = new HashMap<>();
        newPlanning = planning;

        HBox hbox = new HBox();
        hbox.setSpacing(120);
        hbox.setPadding(new Insets(0, 0, 0, 160));
        hbox.getChildren().add(new Label("Start Hour"));
        hbox.getChildren().add(new Label("End Hour"));
        Vbox.getChildren().add(hbox);
        for (Map.Entry<LocalDate, Day> e : planning.getDays().entrySet()) {
            hbox = new HBox();
            hbox.setSpacing(20);
            hbox.setAlignment(Pos.CENTER);
            LocalDate current = e.getValue().getDate();
            hbox.getChildren().add(new Label(current.toString()));
            startHoursInputs.put(current, createSpinner());
            hbox.getChildren().add(startHoursInputs.get(current));
            endHoursInputs.put(current, createSpinner());
            hbox.getChildren().add(endHoursInputs.get(current));
            Vbox.getChildren().add(hbox);
            if (current.equals(planning.getStartDay())) {
                Button button = new Button("V");

                button.setOnAction(event -> {
                    for (Map.Entry<LocalDate, Spinner<Integer>> entry : startHoursInputs.entrySet()) {
                        entry.getValue().getValueFactory().setValue(startHoursInputs.get(current).getValue());
                    }
                    for (Map.Entry<LocalDate, Spinner<Integer>> entry : endHoursInputs.entrySet()) {
                        entry.getValue().getValueFactory().setValue(endHoursInputs.get(current).getValue());
                    }
                });

                hbox.getChildren().add(button);
            }
            else hbox.setPadding(new Insets(0, 45, 0, 0));
        }
        Vbox.setSpacing(20);
        Vbox.setPadding(new Insets(10, 0, 10, 0));
        Vbox.setAlignment(Pos.CENTER);
    }
}
