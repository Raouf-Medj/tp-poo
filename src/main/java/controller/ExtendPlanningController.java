package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.*;
import model.users.Users;

import java.io.IOError;
import java.io.IOException;

public class ExtendPlanningController {
    Stage currentStage;
    Stage parentStage;
    private Calendar calendarModel;
    private Users usersModel;

    @FXML
    private DatePicker datePicker;

    public void setCalendarModel(Calendar calendarModel) {
        this.calendarModel = calendarModel;
        datePicker.setValue(calendarModel.getCurrentPlanning().getEndDay());
    }

    public void setUsersModel(Users usersModel) {
        this.usersModel = usersModel;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    @FXML
    void extend(ActionEvent event) throws IOException {
        Planning current = calendarModel.getCurrentPlanning();
        Planning tempPlanning = new Planning(current.getEndDay().plusDays(1), datePicker.getValue(), calendarModel);
        calendarModel.extendPlanning(current, datePicker.getValue());
        currentStage.close();

        Planning p = calendarModel.addPlanning(tempPlanning);
        if (p !=  null) tempPlanning = p;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/addfreezones.fxml"));
        Parent root = loader.load();

        AddController controller = loader.getController();
        controller.setCalendarModel(calendarModel);
        controller.setUsersModel(usersModel);
        // other models

        controller.initVbox(tempPlanning);
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.setTitle("Add free timeslots");
        currentStage.setResizable(false);
        controller.setCurrentStage(currentStage);
        controller.setParentStage(parentStage);
        currentStage.show();

        calendarModel.getPlannings().remove(tempPlanning);
    }
}