package sample;

import functions.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.DoubleStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Sample implements Initializable {

    @FXML
    private Button deleteButton;

    @FXML
    private Button addPointButton;

    @FXML
    private MenuItem newFileMenuItem;

    @FXML
    private MenuItem loadFileMenuItem;

    @FXML
    private MenuItem saveFileMenuItem;

    @FXML
    private MenuItem saveFileAsMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private Menu loadAndTabulateMenu;

    @FXML
    private TextField xTextField;

    @FXML
    private TextField yTextField;

    @FXML
    private TableView<FunctionPoint> pointsTableView;

    @FXML
    private TableColumn<FunctionPoint, Double> xValuesTableColumn;

    @FXML
    private TableColumn<FunctionPoint,Double> yValuesTableColumn;

    private Stage getWindow() {
        return (Stage) addPointButton.getScene().getWindow();
    }
    
    private FileTabulatedFunction fileTabulatedFunction;

    private FunctionParametersDialog functionParametersDialog;

    private FileChooser fileChooser;
    private FunctionLoader functionLoader;

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        this.fileTabulatedFunction = new FileTabulatedFunction();

        this.functionParametersDialog = new FunctionParametersDialog();

        this.fileChooser = new FileChooser();
        this.functionLoader = new FunctionLoader();

        initializePointsTableView();

        initializeMenus();

        initializeButtons();
    }
    
    private void updatePointTableView() {
    	
		pointsTableView.getItems().clear();
		for (int i = 0; i < fileTabulatedFunction.getPointCount(); ++i){
			pointsTableView.getItems().add(fileTabulatedFunction.getPoint(i));
		}
	}
	
	private void saveFileAction() {
		if (!fileTabulatedFunction.isFileNameAssigned()) {
            saveFileAsAction();
        } else {
            try {
                fileTabulatedFunction.saveFunction();
            } catch (IOException e) {
                ErrorDialog.processError(e, "Ошибка при сохранении документа");
            }
        }
	}

	private void saveFileAsAction() {
	    try {
            File saveFile = fileChooser.showSaveDialog(getWindow());
            fileTabulatedFunction.saveFunctionAs(saveFile.getAbsolutePath());
        } catch (IOException e) {
            ErrorDialog.processError(e, "Ошибка при сохранении документа в выбранный файл");
        }
	}

    private void initializeButtons() {
        addPointButton.setOnAction(event -> {
            try {
                double x = Double.parseDouble(xTextField.getText());
                double y = Double.parseDouble(yTextField.getText());
                FunctionPoint xy = new FunctionPoint(x, y);
                try {
                    fileTabulatedFunction.addPoint(xy);
    			    updatePointTableView();
                } catch (InappropriateFunctionPointException e) {
                    ErrorDialog.processError(e, "Ошибка при добавлении точки");
                }
            } catch (NumberFormatException e) {
                ErrorDialog.processError(e, "Введены некорректные координаты");
            }
        });
        deleteButton.setOnAction(event -> {
            try {
                int index = pointsTableView.getSelectionModel().getSelectedIndex();
                fileTabulatedFunction.deletePoint(index);
                pointsTableView.getItems().remove(index);
            } catch (FunctionPointIndexOutOfBoundsException | IllegalStateException e){
                ErrorDialog.processError(e, "Ошибка при удалении точки");
            }

        });
    }

    private void initializeMenus() {
        newFileMenuItem.setOnAction(event -> {
                    if (cancelBecauseNotSaved()) return;
                    Optional<TabulatedFunctionParameters> params = functionParametersDialog.showAndWait();

                    params.ifPresent(tabulatedFunctionParameters -> {
                        try {
                            fileTabulatedFunction.newFunction(
                                    tabulatedFunctionParameters.leftBorderX,
                                    tabulatedFunctionParameters.rightBorderX,
                                    tabulatedFunctionParameters.pointCount);
                            updatePointTableView();
                        } catch (IllegalArgumentException e) {
                            ErrorDialog.processError(e, "Ошиба при создании новой фунии");
                        }
                    });
                }
        );

        saveFileMenuItem.setOnAction(event -> saveFileAction());

        saveFileAsMenuItem.setOnAction(event -> saveFileAsAction());

        loadFileMenuItem.setOnAction(event -> {
            if (cancelBecauseNotSaved()) return;

            File selectedFile = fileChooser.showOpenDialog(getWindow());
            if (null != selectedFile) {
                try {
                    fileTabulatedFunction.loadFunction(selectedFile.getAbsolutePath());
                    updatePointTableView();
                } catch (InappropriateFunctionPointException | IOException e) {
                    ErrorDialog.processError(e, "Ошибка при загрузке функии из документа");
                }
            }

        });

        closeMenuItem.setOnAction(event -> getWindow().close());

        loadAndTabulateMenu.setOnAction(event -> {
            if (cancelBecauseNotSaved()) return;

            File selectedFile = fileChooser.showOpenDialog(getWindow());
            if (null != selectedFile) {
                try {
                    Function functionToTabulate = functionLoader.loadFunction(selectedFile);

                    Optional<TabulatedFunctionParameters> params = functionParametersDialog.showAndWait();

                    params.ifPresent(tabulatedFunctionParameters -> {
                        try {
                            fileTabulatedFunction.tabulateFunction(
                                    functionToTabulate,
                                    tabulatedFunctionParameters.leftBorderX,
                                    tabulatedFunctionParameters.rightBorderX,
                                    tabulatedFunctionParameters.pointCount
                            );
                            updatePointTableView();
                        } catch (IllegalArgumentException e) {
                            ErrorDialog.processError(e, "Ошибка при табулировании функции");
                        }
                    });
                } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                    ErrorDialog.processError(e, "Ошибка при загрузке функции");
                }
            }
        });
        Utils.onAction(loadAndTabulateMenu);
    }

    void onCloseAction(WindowEvent windowEvent) {
        if (cancelBecauseNotSaved()) {
            windowEvent.consume();
        }
    }

    private boolean cancelBecauseNotSaved() {
        if (fileTabulatedFunction.isModified()) {
            Optional<ButtonType> result = new Alert(
                Alert.AlertType.CONFIRMATION,
                "У вас не сохранена функция, хотите ли сохранить?",
                ButtonType.CANCEL, ButtonType.NO, ButtonType.YES
            ).showAndWait();

            if (result.isPresent()) {
                ButtonType buttonType = result.get();

                if (ButtonType.CANCEL == buttonType) return true;

                if (ButtonType.YES == buttonType) {
                    saveFileAction();
                }
            } else {
                return true;
            }
        }
        return false;
    }

    private void initializePointsTableView() {
        pointsTableView.setEditable(true);
        xValuesTableColumn.setCellValueFactory( new PropertyValueFactory<>("abs"));
        yValuesTableColumn.setCellValueFactory( new PropertyValueFactory<>("ord"));
        yValuesTableColumn.setEditable(true);

        yValuesTableColumn.setOnEditCommit(yEditEvent -> {
            try {
                double y = yEditEvent.getNewValue();
                int index = yEditEvent.getTablePosition().getRow();
                fileTabulatedFunction.setPointY(index, y);
            } catch (NumberFormatException ignored) {
            }
        });
        yValuesTableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        xValuesTableColumn.prefWidthProperty().bind(pointsTableView.widthProperty().divide(2));
        yValuesTableColumn.prefWidthProperty().bind(pointsTableView.widthProperty().divide(2));
    }
}
