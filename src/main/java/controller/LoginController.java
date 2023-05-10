package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.users.User;
import model.users.Users;
import view.Login;

import java.io.IOException;
import java.util.Map;

public class LoginController {
    private Users model;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorMessage;

    @FXML
    private PasswordField passwordConfirmSignup;

    @FXML
    private PasswordField passwordSignup;

    @FXML
    private Button signupButton;

    @FXML
    private TextField usernameSignup;


    @FXML
    private TextField username;

    public void setModel(Users users) {
        this.model = users;
    }

    @FXML
    void login(ActionEvent event) {
        String pseudo = username.getText();
        String mdp = password.getText();
        User u = new User(pseudo, mdp);
        if (model.getActiveUser() !=  null && !model.getActiveUser().equals(u)) model.getActiveUser().setConnected(false);
        if (model.getUsers().containsKey(pseudo)) {
            u = model.getUsers().get(pseudo);
            if (u.isCorrectPassword(mdp)) {
                u.setConnected(true);
                model.setActiveUser(u);
                // REDIRECT
//                for (Map.Entry<String, User> e : model.getUsers().entrySet()) {
//                    System.out.println("USER: "+e.getKey()+" ->  CONNECTED: "+e.getValue().isConnected());
//                }
            }
            else {
                errorMessage.setText("ERROR: wrong password!");
                errorMessage.setVisible(true);
            }
        }
        else {
            errorMessage.setText("ERROR: username does not exist!");
            errorMessage.setVisible(true);
        }


    }

    @FXML void clearErrorMessage(KeyEvent event) {
        errorMessage.setVisible(false);
        errorMessage.setText("");
    }

    @FXML
    void switchToSignup(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/signup.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setModel(model);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Signup");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void switchToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/login.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setModel(model);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();
    }

    @FXML void signup(ActionEvent event) {
        String pseudo = usernameSignup.getText();
        String mdp = passwordSignup.getText();
        String mdpConfirm = passwordConfirmSignup.getText();
        if (!model.getUsers().containsKey(pseudo)) {
            if (!mdp.equals(mdpConfirm)) {
                errorMessage.setText("ERROR: please confirm your password!");
                errorMessage.setVisible(true);
            }
            else {
                User u = new User(pseudo, mdp);
                model.getUsers().put(pseudo, u);
                u.setConnected(true);
                model.setActiveUser(u);
                // REDIRECT
            }
        }
        else {
            errorMessage.setText("ERROR: username already exists!");
            errorMessage.setVisible(true);
        }
    }

}

