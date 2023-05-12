package controller;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Calendar;
import model.Day;

import java.time.LocalDate;
import java.util.TreeSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.FreeZone;
import model.OccupiedZone;


public class DayViewController {
    private Day model;

    @FXML
    private VBox dayBox;

    @FXML
    private Label currentDate;

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate.setText(currentDate.toString());
    }

    @FXML
    private MenuBar menuBar;

    @FXML
    void gotoProjects(ActionEvent event) {

    }

    @FXML
    void gotoStatistics(ActionEvent event) {

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
    void viewDashboard(ActionEvent event) {

    }

    @FXML
    void viewToday(ActionEvent event) {

    }


    public void setModel(Calendar calendar, LocalDate currentDate) {
        this.model=calendar.getDay(currentDate);
        if (model == null){
            model= new Day(currentDate);
        }
        setCurrentDate(currentDate);
        fillDayBox(model);

    }

    public void fillDayBox(Day model){
        TreeSet<FreeZone> zones = model.getZones();
        double originalPosition =0;
        for(FreeZone zn : zones){
            Rectangle zoneRepresentation = new Rectangle(200,((double)zn.getDuration().toHours())*31);

            zoneRepresentation.setTranslateY(((double)zn.getStartTime().getHour())*31-originalPosition);
            originalPosition+= ((double)zn.getDuration().toHours())*31;
            if (zn instanceof OccupiedZone){
                zoneRepresentation.setFill(Color.rgb(224,166,108));
            }
            else{
                zoneRepresentation.setFill(Color.rgb(101,170,194));
            }
            dayBox.getChildren().add(zoneRepresentation);

        }
    }
}
