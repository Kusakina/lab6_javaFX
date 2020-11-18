package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = Utils.createFxmlLoader(getClass(), "Sample");

        Parent root = loader.load();

        stage.setTitle("TabulatedFunction");

        stage.setScene(new Scene(root, 500, 445));
        stage.setMinWidth(500);
        stage.setMinHeight(445);
        stage.setResizable(true);

        Sample controller = loader.getController();
        stage.setOnCloseRequest(controller::onCloseAction);

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
