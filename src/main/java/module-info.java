module view {
    requires javafx.controls;
    requires javafx.fxml;


    opens model to javafx.fxml;
    exports model;
    opens controller to javafx.fxml;
    exports controller;
    opens view to javafx.fxml;
    exports view;
    exports model.users;
    opens model.users to javafx.fxml;
}