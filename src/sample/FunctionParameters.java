package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FunctionParameters implements Initializable {

    @FXML
    private TextField leftDomainTextField;

    @FXML
    private TextField rightDomainTextField;

    @FXML
    private Spinner<Integer> pointsCountSpinner;

    private SpinnerValueFactory.IntegerSpinnerValueFactory pointsCountValueFactory;
    
    private TabulatedFunctionParameters parameters;
    
    TabulatedFunctionParameters getParameters() {
    	return parameters;
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        pointsCountValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, Integer.MAX_VALUE);
	    pointsCountSpinner.setValueFactory(pointsCountValueFactory);

        setDefaultValues();
    }

    void setDefaultValues() {
        leftDomainTextField.setText("1");
        rightDomainTextField.setText("2");
        pointsCountValueFactory.setValue(11);
    }

    boolean okAction() {
        try {
            double leftBorder = Double.parseDouble(leftDomainTextField.getText());
            double rightBorder = Double.parseDouble(rightDomainTextField.getText());
            int pointsCounter = pointsCountSpinner.getValue();

            parameters = new TabulatedFunctionParameters(leftBorder, rightBorder, pointsCounter);
            return true;
        } catch (NumberFormatException e) {
            ErrorDialog.processError(e, "Ошибка при указании параметров функии");
            return false;
        }
    }

    void cancelAction() {
        parameters = null;
    }
}

