package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.IOException;

class FunctionParametersDialog extends Dialog<TabulatedFunctionParameters> {

   FunctionParametersDialog() {
        try {
            FXMLLoader loader = Utils.createFxmlLoader(getClass(), "FunctionParameters");
            Parent root = loader.load();

            DialogPane dialogPane = getDialogPane();

            dialogPane.setContent(root);
            dialogPane.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

            FunctionParameters controller = loader.getController();

            Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
            okButton.setOnAction(event -> controller.okAction());
            okButton.addEventFilter(
                    ActionEvent.ACTION,
                    event -> {
                        if (!controller.okAction()) {
                            event.consume();
                        }
                    }
            );

            Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
            cancelButton.setOnAction(event -> controller.cancelAction());

            setResultConverter(buttonType -> controller.getParameters());

            setOnShown(event -> controller.setDefaultValues());

            setOnCloseRequest(event -> hide());

        } catch (IOException e) {
            e.printStackTrace();
        }
   }
}
