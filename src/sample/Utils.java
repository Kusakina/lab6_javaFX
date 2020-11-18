package sample;

import javafx.fxml.FXMLLoader;

class Utils {

    static FXMLLoader createFxmlLoader(Class<?> callerClass, String sceneName) {
        return new FXMLLoader(callerClass.getResource(String.format("%s.fxml", sceneName)));
    }
}
