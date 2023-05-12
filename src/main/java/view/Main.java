package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import model.users.User;
import model.users.Users;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Calendar calendar = new Calendar();
        Category category = new Category("yes","infrared");
        Day day = new Day(LocalDate.of(2023,5,12));
        Task task1 = new SimpleTask("prepareTools",category,Priority.HIGH, LocalDateTime.of(2023,12,12,17,30), Duration.ofHours(3),1);
        Task task2 = new ComplexTask("prepareTools",category,Priority.HIGH, LocalDateTime.of(2023,12,12,17,30), Duration.ofHours(5));
        FreeZone freeZone= new FreeZone(LocalTime.of(8,30),LocalTime.of(11,30));
        FreeZone freeZone2= new FreeZone(LocalTime.of(13,30),LocalTime.of(22,0));
        day.insertZone(freeZone);
        day.insertZone(freeZone2);
        calendar.addDay(day);

/*        Users users = new Users();
        users.getUsers().put("", new User("", ""));*/

/*        Login login = new Login(users);
        login.show();*/
        DayView dayView = new DayView( calendar,LocalDate.of(2023,5,12));
        dayView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
