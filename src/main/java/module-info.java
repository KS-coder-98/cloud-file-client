module serverFileManagment {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;

    exports cloud.file.management.server.app to javafx.graphics;
    opens cloud.file.management.server.controller to javafx.fxml;
}