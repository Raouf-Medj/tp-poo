package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Badge;
import model.Calendar;
import model.Planning;
import model.users.Users;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoryController {
    private Stage currentStage;
    private Users usersModel;
    private Calendar calendarModel;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    void viewDashboard(ActionEvent event) throws IOException {
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

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void setUsersModel(Users usersModel) {
        this.usersModel = usersModel;
    }

    public void setCalendarModel(Calendar calendarModel) {
        this.calendarModel = calendarModel;
    }

    public void init() {

//        temp
//        calendarModel.addPlanning(new Planning(LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 7), calendarModel));
//        calendarModel.addPlanning(new Planning(LocalDate.of(2023, 5, 10), LocalDate.of(2023, 5, 20), calendarModel));
//        calendarModel.addPlanning(new Planning(LocalDate.of(2023, 5, 20), LocalDate.of(2023, 5, 22), calendarModel));

        calendarModel.updateArchivedPlannings();

        VBox container = new VBox();
        container.setSpacing(5);
        scrollPane.setContent(container);

        for (Planning planning : calendarModel.getArchivedPlannings()) {

//            planning.getBadges().add(Badge.GOOD);
//            planning.getBadges().add(Badge.GOOD);
//            planning.getBadges().add(Badge.GOOD);
//            planning.getBadges().add(Badge.GOOD);
//            planning.getBadges().add(Badge.GOOD);
//            planning.getBadges().add(Badge.VERY_GOOD);
//            planning.getBadges().add(Badge.VERY_GOOD);
//            planning.getBadges().add(Badge.VERY_GOOD);
//            planning.getBadges().add(Badge.EXCELLENT);

            VBox outer = new VBox();
            outer.setPrefWidth(1185);
            outer.setPadding(new Insets(10, 10, 10, 10));
            outer.setStyle("-fx-background-color:  #e9e9e9");

            GridPane grid = new GridPane();
            grid.setHgap(30);
            grid.setVgap(20);
            Label label1 = new Label("Planning: ");
            label1.setStyle("-fx-font-size: 20");
            grid.add(label1, 0, 0);
            Label label2 = new Label("Number of completed tasks: ");
            label2.setStyle("-fx-font-size: 20");
            grid.add(label2, 0, 1);
            Label label3 = new Label("Number of completed projects: ");
            label3.setStyle("-fx-font-size: 20");
            grid.add(label3, 0, 2);

            int[] stats = planning.getStats();
            Label label4 = new Label(planning.toString());
            label4.setStyle("-fx-font-size: 20");
            grid.add(label4, 1, 0);
            Label label5 = new Label(""+stats[0]);
            label5.setStyle("-fx-font-size: 20");
            grid.add(label5, 1, 1);
            Label label6 = new Label(""+stats[1]);
            label6.setStyle("-fx-font-size: 20");
            grid.add(label6, 1, 2);

            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_LEFT);
            Label label7 = new Label("Badges: ");
            label7.setStyle("-fx-font-size: 20");
            hbox.getChildren().add(label7);

            HBox badges = new HBox();
            badges.setPrefHeight(103);
            badges.setSpacing(20);
            badges.setAlignment(Pos.CENTER_LEFT);

            hbox.getChildren().add(badges);

            if (!planning.getBadges().isEmpty()) {
                for (Badge badge : planning.getBadges()) {
                    ImageView image = new ImageView();
                    image.setImage(new Image("/"+badge.toString()+".png"));
                    image.setFitWidth(80);
                    image.setPreserveRatio(true);
                    badges.getChildren().add(image);
                }
            }
            else {
                Label label = new Label("                                           No badges to show");
                label.setStyle("-fx-font-size: 20px;");
                badges.getChildren().add(label);
            }

            outer.getChildren().add(grid);
            outer.getChildren().add(hbox);

            container.getChildren().add(outer);
        }
    }
}
