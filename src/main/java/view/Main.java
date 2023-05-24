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
        Category categoryStudy = new Category("Study","white");
        Category categoryExtra = new Category("Extra","white");
        Category categoryActivity = new Category("Activity","white");

        SimpleTask simpleTask1=new SimpleTask("Visit the doctor",categoryExtra, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(1),0);
        SimpleTask simpleTask2=new SimpleTask("Visit the museum",categoryActivity, Priority.HIGH, LocalDateTime.of(2023,5,30,15,0), Duration.ofHours(1),0);
        SimpleTask simpleTask3=new SimpleTask("Do some shopping",categoryExtra, Priority.HIGH, LocalDateTime.of(2023,5,30,15,0), Duration.ofHours(1),0);
        SimpleTask simpleTask4=new SimpleTask("Buy a charger",categoryExtra, Priority.LOW, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(1),0);
        SimpleTask simpleTask5=new SimpleTask("prepare demo Tp",categoryStudy, Priority.HIGH, LocalDateTime.of(2023,5,30,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTask6=new SimpleTask("task6",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTask7=new SimpleTask("task7",categorySimple, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTask8=new SimpleTask("task8",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,5,30,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTask9=new SimpleTask("task9",categorySimple, Priority.LOW, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTask10=new SimpleTask("task10",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTaska=new SimpleTask("taska",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTaskb=new SimpleTask("taskb",categorySimple, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTaskc=new SimpleTask("taskc",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTaskd=new SimpleTask("taskd",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),0);
        SimpleTask simpleTaske=new SimpleTask("taske",categorySimple, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(2),0);

        ComplexTask complexTask1= new ComplexTask("Revise Poo",categoryStudy, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask2= new ComplexTask("Revise Calculus",categoryStudy, Priority.LOW, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask3= new ComplexTask("task15",categoryComplex, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask4= new ComplexTask("task14",categoryComplex, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask5= new ComplexTask("task15",categoryComplex, Priority.LOW, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask6= new ComplexTask("task16",categoryComplex, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask7= new ComplexTask("task17",categoryComplex, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask8= new ComplexTask("task18",categoryComplex, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask9= new ComplexTask("task19",categoryComplex, Priority.HIGH, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));
        ComplexTask complexTask10= new ComplexTask("task20",categoryComplex, Priority.MEDIUM, LocalDateTime.of(2023,5,31,15,0), Duration.ofHours(5));

        SimpleTask simpleTask11=new SimpleTask("Practice sport",categoryActivity, Priority.HIGH, LocalDateTime.of(2023,6,30,15,0), Duration.ofHours(2),2);
        SimpleTask simpleTask22=new SimpleTask("Practice touch typing",categoryActivity, Priority.HIGH, LocalDateTime.of(2023,6,30,15,0), Duration.ofHours(1),1);
        SimpleTask simpleTask33=new SimpleTask("task33",categorySimple, Priority.LOW, LocalDateTime.of(2023,6,30,15,0), Duration.ofHours(2),3);
        SimpleTask simpleTask44=new SimpleTask("task44",categorySimple, Priority.LOW, LocalDateTime.of(2023,6,4,15,0), Duration.ofHours(1),1);
        SimpleTask simpleTask55=new SimpleTask("task55",categorySimple, Priority.HIGH, LocalDateTime.of(2023,6,4,15,0), Duration.ofHours(1),2);
        SimpleTask simpleTask66=new SimpleTask("task66",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,6,4,15,0), Duration.ofHours(2),3);
        SimpleTask simpleTask77=new SimpleTask("task77",categorySimple, Priority.HIGH, LocalDateTime.of(2023,6,4,15,0), Duration.ofHours(2),4);
        SimpleTask simpleTask88=new SimpleTask("task88",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,6,4,15,0), Duration.ofHours(2),4);
        SimpleTask simpleTask99=new SimpleTask("task99",categorySimple, Priority.LOW, LocalDateTime.of(2023,6,4,15,0), Duration.ofHours(2),4);
        SimpleTask simpleTask100=new SimpleTask("task100",categorySimple, Priority.MEDIUM, LocalDateTime.of(2023,6,4,15,0), Duration.ofHours(2),2);


        ArrayList<Task> taskArrayList1 = new ArrayList<Task>(0);
        ArrayList<Task> taskArrayList2 = new ArrayList<Task>(0);
        ArrayList<Task> taskArrayList3 = new ArrayList<Task>(0);

        taskArrayList1.add(simpleTask3);
        taskArrayList1.add(simpleTask2);
        taskArrayList1.add(simpleTask1);
        taskArrayList1.add(simpleTask4);
        taskArrayList1.add(simpleTask5);
        taskArrayList1.add(simpleTask11);
        taskArrayList1.add(simpleTask22);
        taskArrayList1.add(complexTask1);
        taskArrayList1.add(complexTask2);

        taskArrayList2.add(simpleTask6);
        taskArrayList2.add(simpleTask7);
        taskArrayList2.add(simpleTask8);
        taskArrayList2.add(simpleTask9);
        taskArrayList2.add(simpleTask10);
        taskArrayList2.add(complexTask3);
        taskArrayList2.add(complexTask4);
        taskArrayList2.add(complexTask5);
        taskArrayList2.add(complexTask6);
        taskArrayList2.add(simpleTask33);
        taskArrayList2.add(simpleTask44);
        taskArrayList2.add(simpleTask55);


        User u = new User("Ibrahim","");
        u.setConnected(true);
        Calendar calendar = new Calendar();
        Planning planning1 = new Planning(LocalDate.of(2023, 5, 23), LocalDate.of(2023, 5, 31), calendar);
        Planning planning2 = new Planning(LocalDate.of(2023, 5, 1), LocalDate.of(2023, 5, 17), calendar);

        calendar.addPlanning(planning1);
        calendar.addPlanning(planning2);


        calendar.getDay(LocalDate.of(2023, 5, 1)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 2)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 3)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 4)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 5)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 6)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 7)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 8)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 9)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 10)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 11)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 12)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 13)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 14)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 15)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 16)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 17)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));



        calendar.getDay(LocalDate.of(2023, 5, 23)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 24)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 25)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 26)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 27)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 28)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 29)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 30)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));
        calendar.getDay(LocalDate.of(2023, 5, 31)).insertZone(new FreeZone(LocalTime.of(18, 0), LocalTime.of(22, 0)));

        calendar.fillPlanning(planning2,taskArrayList2,Duration.ofMinutes(30));
        calendar.getUnscheduled().addAll(taskArrayList1);

        simpleTask6.setState(State.COMPLETED);
        simpleTask7.setState(State.COMPLETED);
        simpleTask8.setState(State.COMPLETED);
        simpleTask9.setState(State.COMPLETED);
        simpleTask10.setState(State.COMPLETED);
        complexTask3.setState(State.COMPLETED);
        complexTask4.setState(State.COMPLETED);
        complexTask5.setState(State.COMPLETED);
        complexTask6.setState(State.COMPLETED);
        simpleTask33.setState(State.COMPLETED);
        simpleTask44.setState(State.COMPLETED);
        simpleTask55.setState(State.COMPLETED);


        calendar.getDay(LocalDate.of(2023,5,1)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,2)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,3)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,4)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,5)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,6)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,7)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,8)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,9)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,10)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,11)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,12)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,13)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,14)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,15)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,16)).setGoalAchieved(true);
        calendar.getDay(LocalDate.of(2023,5,17)).setGoalAchieved(true);

        planning2.getBadges().add(Badge.GOOD);
        planning2.getBadges().add(Badge.GOOD);
        planning2.getBadges().add(Badge.GOOD);
        planning2.getBadges().add(Badge.VERY_GOOD);

        u.setCalendarModel(calendar);

        /****************************************************/

        Users users = loadData();
        if(users!=null){
            users.getUsers().put("Ibrahim", u);// temp
        }
        else{
            users = new Users();
            users.getUsers().put("Ibrahim", u);
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
