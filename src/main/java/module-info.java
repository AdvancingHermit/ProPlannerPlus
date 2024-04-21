module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.cucumber.core;

    opens controllers to javafx.fxml; // Gives access to fxml files
    exports controllers; // Exports the class inheriting from javafx.application.Application
}