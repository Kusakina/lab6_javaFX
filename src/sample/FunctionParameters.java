package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import functions.ArrayTabulatedFunction;
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

    static ArrayTabulatedFunction arrayTabulatedFunction = null;
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
    private Button CanselButton;

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
                arrayTabulatedFunction = new ArrayTabulatedFunction(leftBorder, rightBorder, pointsCounter);
                Stage stage = (Stage) OKButton.getScene().getWindow();
                stage.close();
            } catch (NumberFormatException e) {

            } catch (IllegalArgumentException e) {
                Left_Domain_field.setText("0");
                Right_Domain_field.setText("10");
                spinnerCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, Integer.MAX_VALUE, 11));
            }
        });
        CanselButton.setOnAction(event -> {
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

    public static ArrayTabulatedFunction FunctionParameterShow() {
        FXMLLoader fxmlLoader = new FXMLLoader(FunctionParameters.class.getResource("FunctionParameter.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Tabulate");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            event.consume();
        });
        stage.showAndWait();
        return arrayTabulatedFunction;
    }
}

