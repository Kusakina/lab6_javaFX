package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FunctionParameters {

    static TabulatedFunction tabulatedFunction = null;
    private static double leftBorder;
    private static double rightBorder;
    private static int pointsCounter;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Left_Domain_field;

    @FXML
    private TextField Right_Domain_field;

    @FXML
    private Button OKButton;

    @FXML
    private Button CancelButton;

    @FXML
    private Spinner<Integer> spinnerCount;

    @FXML
    void initialize() {
        {
            spinnerCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, Integer.MAX_VALUE, 11));
        }
        OKButton.setOnAction(event -> {
            try {
                leftBorder = Double.parseDouble(Left_Domain_field.getText());
                rightBorder = Double.parseDouble(Right_Domain_field.getText());
                pointsCounter = spinnerCount.getValue();
                //поменять по заданию 2
                tabulatedFunction = new ArrayTabulatedFunction(leftBorder, rightBorder, pointsCounter);
                Stage stage = (Stage) OKButton.getScene().getWindow();
                stage.close();
            } catch (NumberFormatException e) {

            } catch (IllegalArgumentException e) {
                Left_Domain_field.setText("0");
                Right_Domain_field.setText("10");
                spinnerCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, Integer.MAX_VALUE, 11));
            }
        });
        CancelButton.setOnAction(event -> {
            Stage stage = (Stage) OKButton.getScene().getWindow();
            stage.close();
        });
    }
    public double getLeftDomainBorder(){
        return Double.parseDouble(Left_Domain_field.getText());
    }
    public double getRightDomainBorder(){
        return Double.parseDouble(Right_Domain_field.getText());
    }
    public int getPointsCount(){
        return spinnerCount.getValue();
    }

    public static TabulatedFunction FunctionParameterShow() {
        FXMLLoader fxmlLoader = new FXMLLoader(FunctionParameters.class.getResource("FunctionParameters.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Tabulate");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setOnCloseRequest(Event::consume);
        stage.showAndWait();
        return tabulatedFunction;
    }
}

