package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("Sample.fxml"));
        Parent root = null;
        try {
            root = fxmlloader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("TabulatedFunction");

        stage.setScene(new Scene(root, 500, 445));
        stage.setMinWidth(500);
        stage.setMinHeight(445);
        stage.setResizable(true);

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
