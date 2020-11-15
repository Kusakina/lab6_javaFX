package sample;

import functions.FunctionPoint;
import functions.FunctionPointIndexOutOfBoundsException;
import functions.InappropriateFunctionPointException;
import functions.TabulatedFunction;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

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
    private TableColumn<FunctionPoint, Double> X_values;
    @FXML
    private TableView<FunctionPoint> pointTableView;

    @FXML
    private TableColumn<FunctionPoint,Double> Y_values;
    private static TabulatedFunction tabulatedFunction = null;

    @FXML
    void initialize() {
        pointTableView.setEditable(true);
        X_values.setCellValueFactory( new PropertyValueFactory<>("abs"));
        Y_values.setCellValueFactory( new PropertyValueFactory<>("ord"));
        Y_values.setEditable(true);

        Y_values.setOnEditCommit(yEditEvent -> {
            try {
                double y = yEditEvent.getNewValue();
                int index = yEditEvent.getTablePosition().getRow();
                tabulatedFunction.setPointY(index, y);
            } catch (NumberFormatException e) {
            }

        });
        Y_values.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        X_values.prefWidthProperty().bind(pointTableView.widthProperty().divide(2));
        Y_values.prefWidthProperty().bind(pointTableView.widthProperty().divide(2));

        new_file_menu.setOnAction(event -> {
            try{pointTableView.getItems().clear();
            } catch (Exception e){}
            tabulatedFunction =FunctionParameters.FunctionParameterShow();

            for (int i = 0; i< tabulatedFunction.getPointCount(); ++i){
                pointTableView.getItems().add(tabulatedFunction.getPoint(i));
            }


        });
        AddButton.setOnAction(event -> {
            try {
                x = Double.parseDouble(X_field.getText());
                y = Double.parseDouble(Y_field.getText());
                FunctionPoint xy = new FunctionPoint(x, y);
                try {
                    int index = 0;
                    tabulatedFunction.addPoint(xy);
                    for (;!tabulatedFunction.getPoint(index).equals(xy);++index);
                    for (; index< tabulatedFunction.getPointCount(); ++index){
                       try {
                           pointTableView.getItems().remove(index);
                       } catch (Exception e){}
                        pointTableView.getItems().add(index, tabulatedFunction.getPoint(index));
                    }

                } catch (InappropriateFunctionPointException e) {
                    e.printStackTrace();
                    Error_message.errorShow();
                }
                //Stage stage = (Stage) AddButton.getScene().getWindow();
            } catch (NumberFormatException e) {
            }
        });
        DeleteButton.setOnAction(event -> {
            try {
                int index = pointTableView.getSelectionModel().getSelectedIndex();
                tabulatedFunction.deletePoint(index);
                pointTableView.getItems().remove(index);
            }catch (FunctionPointIndexOutOfBoundsException|IllegalStateException e ){

            }

        });
    }}
