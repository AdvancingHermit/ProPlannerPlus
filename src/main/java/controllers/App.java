package controllers;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonToken;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DataModel;

import java.io.IOException;
import java.nio.channels.Pipe;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static DataModel model;
    private static FXMLLoader fxmlLoader;
    private static ProPlannerPlus proPlannerPlus;

    @Override
    public void start(Stage stage) throws IOException {
        model = new DataModel();
        proPlannerPlus = new ProPlannerPlus();
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));

    }

    private static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml + ".fxml"));
        Parent temp = fxmlLoader.load();
        StandardController controller = fxmlLoader.getController();
        controller.initController(model,model, proPlannerPlus);
        return temp;
    }
    public static void main(String[] args) {
        launch();
    }

}