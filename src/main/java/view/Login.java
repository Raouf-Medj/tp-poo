package view;

import controller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.users.User;
import model.users.Users;

import java.util.HashMap;

public class Login extends Stage {
    private Users model;
    public Login(Users model) {
        this.model = model;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            LoginController controller = loader.getController();
            controller.setModel(model);

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
