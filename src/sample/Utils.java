package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

class Utils {
    public static void onAction(Menu menu)
    {
        final MenuItem menuItem = new MenuItem();

        menu.getItems().add(menuItem);
        menu.addEventHandler(Menu.ON_SHOWN, event -> menu.hide());
        menu.addEventHandler(Menu.ON_SHOWING, event -> menu.fire());
    }

    static FXMLLoader createFxmlLoader(Class<?> callerClass, String sceneName) {
        return new FXMLLoader(callerClass.getResource(String.format("%s.fxml", sceneName)));
    }
}
