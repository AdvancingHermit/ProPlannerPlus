module hellofx {
    requires javafx.controls;
    requires javafx.fxml;

    opens dtu.example.ui to javafx.fxml; // Gives access to fxml files
    exports dtu.example.ui; // Exports the class inheriting from javafx.application.Application
}