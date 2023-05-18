package view;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.users.User;
import model.users.Users;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class Main extends Application {

    private static final String DATA_FILE = "save.dat";
    @Override
    public void start(Stage stage) throws Exception {

        Users users = loadData();
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
            e.getValue().getCalendarModel().setProjectsSave(new ArrayList<>(e.getValue().getCalendarModel().getProjects()));
            e.getValue().getCalendarModel().setUnscheduledSave(new ArrayList<>(e.getValue().getCalendarModel().getUnscheduled()));
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
                    e.getValue().getCalendarModel().getProjects().addAll(e.getValue().getCalendarModel().getProjectsSave());
                    e.getValue().getCalendarModel().getUnscheduled().addAll(e.getValue().getCalendarModel().getUnscheduledSave());
                }
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return save;
    }
}
