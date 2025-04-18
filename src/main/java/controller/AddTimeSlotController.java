package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import model.Calendar;
import model.Day;
import model.exceptions.BeyondDeadlineException;
import model.exceptions.NotFitInDayExeception;
import model.exceptions.NotFitInZoneException;
import model.FreeZone;

import java.time.Duration;
import java.time.LocalTime;

public class AddTimeSlotController {

    private Day model;

    @FXML
    private Button cancelButton;


    private Calendar calendarModel;


    private Stage currentStage;


    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    private Spinner<Integer> createSpinnerHours() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0, 1);
        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(valueFactory);
        return spinner;
    }

    private Spinner<Integer> createSpinnerMinutes() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0, 1);
        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(valueFactory);
        return spinner;
    }

    private DayViewController dayViewController;

    public void setModel(Day day, Calendar calendarModel,DayViewController controller) {
        dayViewController=controller;
        this.model = day;
        this.calendarModel=calendarModel;
        SpinnerValueFactory<Integer> stm = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        stm.setValue(0);
        SpinnerValueFactory<Integer> etm = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        etm.setValue(0);
        startTimeMinute.setValueFactory(stm);
        endTimeMinute.setValueFactory(etm);
        SpinnerValueFactory<Integer> sth = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        sth.setValue(0);
        SpinnerValueFactory<Integer> eth = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        eth.setValue(0);
        startTimeHour.setValueFactory(sth);
        endTimeHour.setValueFactory(eth);

    }


    @FXML
    private Button addButton;

    @FXML
    private Spinner<Integer> endTimeHour;

    @FXML
    private Spinner<Integer> endTimeMinute;

    @FXML
    private Spinner<Integer> startTimeHour;

    @FXML
    private Spinner<Integer> startTimeMinute;

    @FXML
    void addTimeSlot(ActionEvent event) {
        Duration duration = Duration.between(LocalTime.of(startTimeHour.getValue(),startTimeMinute.getValue()),LocalTime.of(endTimeHour.getValue(),endTimeMinute.getValue()));
        if(!duration.isNegative() && duration.compareTo(calendarModel.getMinDuration())>=0){
            FreeZone zone = new FreeZone(LocalTime.of(startTimeHour.getValue(),startTimeMinute.getValue()),LocalTime.of(endTimeHour.getValue(),endTimeMinute.getValue()));
            try{
                model.insertZone(zone);
                dayViewController.setStatus("Zone Inserted Successfully",false);
            }catch(BeyondDeadlineException e){
                dayViewController.setStatus(e.getMessage(),true);
            }catch(NotFitInDayExeception e){
                dayViewController.setStatus(e.getMessage(),true);
            }catch(NotFitInZoneException e){
                dayViewController.setStatus(e.getMessage(),true);
            }
            currentStage.close();
            dayViewController.fillDayBox(model);
        }

    }

    @FXML
    void cancel(ActionEvent event) {
        currentStage.close();
    }

}
