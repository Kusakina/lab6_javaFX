package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import functions.ArrayTabulatedFunction;
import functions.FunctionPoint;
import functions.InappropriateFunctionPointException;
import functions.TabulatedFunction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Sample {
    private double x;
    private double y;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button AddButton;

    @FXML
    private Menu File_menu;

    @FXML
    private MenuItem new_file_menu;

    @FXML
    private MenuItem open_file_menu;

    @FXML
    private MenuItem save_file_menu;

    @FXML
    private MenuItem sava_file_us_menu;

    @FXML
    private MenuItem close_menu;

    @FXML
    private Menu Tabulate_menu;

    @FXML
    private MenuItem download_menu;

    @FXML
    private MenuItem tabulate_menu;

    @FXML
    private TextField X_field;

    @FXML
    private TextField Y_field;

    @FXML
    private AnchorPane scroller;

    @FXML
    private TableColumn<?, ?> X_values;

    @FXML
    private TableColumn<?, ?> Y_values;
    private static ArrayTabulatedFunction arrayTabulatedFunction = null;

    @FXML
    void initialize() {
        new_file_menu.setOnAction(event -> {
        FunctionParameters.FunctionParameterShow();
       /*     FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FunctionParameters.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOpacity(1);
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.showAndWait();*/
        });
        AddButton.setOnAction(event -> {
            try {
                x = Double.parseDouble(X_field.getText());
                y = Double.parseDouble(Y_field.getText());
                FunctionPoint xy = new FunctionPoint(x, y);
                try {
                    arrayTabulatedFunction.addPoint(xy);
                } catch (InappropriateFunctionPointException e) {
                    e.printStackTrace();
                }
                //Stage stage = (Stage) AddButton.getScene().getWindow();
            } catch (NumberFormatException e) {
            }
        });
    }}
