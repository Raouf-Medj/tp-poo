package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.users.User;
import model.users.Users;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Users users = new Users();
        users.getUsers().put("Ibrahim", new User("Ibrahim", "321"));

        Login login = new Login(users);
        login.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
