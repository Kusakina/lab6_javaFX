package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import functions.TabulatedFunction;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Error_message {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

    }
    public static void errorShow() {
        FXMLLoader fxmlLoader = new FXMLLoader(FunctionParameters.class.getResource("Error_message.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Error");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setOnCloseRequest(Event::consume);
        stage.showAndWait();
    }
}
