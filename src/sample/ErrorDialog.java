package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

class ErrorDialog {

    static void processError(Exception e, String message) {
        //e.printStackTrace();
        ErrorDialog.errorShow(message + ": " + e.getMessage());
    }

    private static void errorShow(String message) {
        new Alert(Alert.AlertType.ERROR, message, ButtonType.OK).show();
    }
}
