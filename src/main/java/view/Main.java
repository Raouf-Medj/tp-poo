package view;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.*;
import model.users.User;
import model.users.Users;

import java.io.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

public class Main extends Application {

    private static final String DATA_FILE = "save.dat";
    @Override
    public void start(Stage stage) throws Exception {

        /**************filling previous data*****************/

        Category categorySimple = new Category("cat 1", "red");
        Category categoryComplex = new Category("cat 2", "green");
        Category categoryPeriodic = new Category("cat 3", "blue");

        SimpleTask simpleTask1=new SimpleTask("task1",categorySimple, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(1),0);
        SimpleTask simpleTask2=new SimpleTask("task2",categorySimple, Priority.HIGH, LocalDateTime.of(2023,5,30,15,0), Duration.ofHours(1),0);
        SimpleTask simpleTask3=new SimpleTask("task3",categorySimple, Priority.LOW, LocalDateTime.of(2023,5,30,15,0), Duration.ofHours(1),0);
        SimpleTask simpleTask4=new SimpleTask("task4",categorySimple, Priority.LOW, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(1),0);
        SimpleTask simpleTask5=new SimpleTask("task5",categorySimple, Priority.HIGH, LocalDateTime.of(2023,5,30,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTask6=new SimpleTask("task6",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTask7=new SimpleTask("task7",categorySimple, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTask8=new SimpleTask("task8",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,5,30,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTask9=new SimpleTask("task9",categorySimple, Priority.LOW, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTask10=new SimpleTask("task10",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),0);

        ComplexTask complexTask1= new ComplexTask("task11",categoryComplex, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask2= new ComplexTask("task12",categoryComplex, Priority.LOW, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask3= new ComplexTask("task13",categoryComplex, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask4= new ComplexTask("task14",categoryComplex, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask5= new ComplexTask("task15",categoryComplex, Priority.LOW, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask6= new ComplexTask("task16",categoryComplex, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask7= new ComplexTask("task17",categoryComplex, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask8= new ComplexTask("task18",categoryComplex, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask9= new ComplexTask("task19",categoryComplex, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask10= new ComplexTask("task20",categoryComplex, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));

        SimpleTask simpleTask11=new SimpleTask("task11",categorySimple, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(1),1);
        SimpleTask simpleTask22=new SimpleTask("task22",categorySimple, Priority.HIGH, LocalDateTime.of(2023,5,30,15,0), Duration.ofHours(1),2);
        SimpleTask simpleTask33=new SimpleTask("task33",categorySimple, Priority.LOW, LocalDateTime.of(2023,5,30,15,0), Duration.ofHours(1),3);
        SimpleTask simpleTask44=new SimpleTask("task44",categorySimple, Priority.LOW, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(1),1);
        SimpleTask simpleTask55=new SimpleTask("task55",categorySimple, Priority.HIGH, LocalDateTime.of(2023,5,30,15,0), Duration.ofHours(2),2);
        SimpleTask simpleTask66=new SimpleTask("task66",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),3);
        SimpleTask simpleTask77=new SimpleTask("task77",categorySimple, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),4);
        SimpleTask simpleTask88=new SimpleTask("task88",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,5,30,15,0), Duration.ofHours(2),4);
        SimpleTask simpleTask99=new SimpleTask("task99",categorySimple, Priority.LOW, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),4);
        SimpleTask simpleTask100=new SimpleTask("task100",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),2);

        ArrayList<Task> taskArrayList1 = new ArrayList<Task>(0);
        ArrayList<Task> taskArrayList2 = new ArrayList<Task>(0);
        ArrayList<Task> taskArrayList3 = new ArrayList<Task>(0);



        User u = new User("Ibrahim","");
        u.setConnected(true);
        Calendar calendar = new Calendar();
        Planning planning1 = new Planning(LocalDate.of(2023, 5, 23), LocalDate.of(2023, 5, 31), calendar);
        Planning planning2 = new Planning(LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 10), calendar);
        Planning planning3 = new Planning(LocalDate.of(2023, 5, 12), LocalDate.of(2023, 5, 21), calendar);
        calendar.addPlanning(planning1);




        calendar.getDay(LocalDate.of(2023, 5, 23)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 24)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 25)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 26)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 27)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 28)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 29)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 30)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 31)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));



        u.setCalendarModel(calendar);

        /****************************************************/

        Users users = loadData();
        users.getUsers().put("Ibrahim", u); // temp
        if (users ==  null) {
            users = new Users();
            users.getUsers().put("", new User("", "")); // temp
        }



        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setModel(users);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);

        // Add a listener to the close request event
        Users finalUsers = users;
        stage.setOnCloseRequest(event -> {
            event.consume(); // Consume the event to prevent the window from closing immediately

            // Display a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Close Application");
            alert.setContentText("Are you sure you want to exit?");

            // Handle the user's response
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                saveData(finalUsers);  // saving the session before closing
                stage.close();
            }
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void saveData(Users session) {

        session.setActiveUser(null);

        for (Map.Entry<String, User> e : session.getUsers().entrySet()) {
            if (e.getValue().getCalendarModel() !=  null) {
                e.getValue().getCalendarModel().setProjectsSave(new ArrayList<>(e.getValue().getCalendarModel().getProjects()));
                e.getValue().getCalendarModel().setUnscheduledSave(new ArrayList<>(e.getValue().getCalendarModel().getUnscheduled()));
            }
        }


        try {
            File save = new File(DATA_FILE);
            FileOutputStream fileOut = new FileOutputStream(save);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(session);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Users loadData() {
        Users save = null;
        // Check if saved data exists
        File file = new File(DATA_FILE);
        if (file.exists()) {
            // Perform deserialization and retrieve data from the file
            try {
                FileInputStream fileIn = new FileInputStream(DATA_FILE);
                ObjectInputStream in = new ObjectInputStream(fileIn);

                // Deserialize the data
                save = (Users) in.readObject();
                for (Map.Entry<String, User> e : save.getUsers().entrySet()) {
                    if (e.getValue().getCalendarModel() !=  null) {
                        e.getValue().getCalendarModel().getProjects().addAll(e.getValue().getCalendarModel().getProjectsSave());
                        e.getValue().getCalendarModel().getUnscheduled().addAll(e.getValue().getCalendarModel().getUnscheduledSave());
                    }
                }
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return save;
    }
}
