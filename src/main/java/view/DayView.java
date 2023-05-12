package view;

import controller.DayViewController;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Calendar;
import model.Day;
import model.users.User;
import model.users.Users;

import java.time.LocalDate;

public class DayView extends Stage {

    Day model;

    public DayView(Calendar model, LocalDate currentDate) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("dayView.fxml"));
            Parent root = loader.load();

            DayViewController controller = loader.getController();
            controller.setModel(model,currentDate);


            Scene scene = new Scene(root);
            this.setScene(scene);
            this.setTitle("Login");
            this.setResizable(false);
            this.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
