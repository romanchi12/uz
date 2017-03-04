package sample;

import controllers.MonitoringCtrl;
import controllers.ControllerManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        stage.setScene(ControllerManager.changeSceneTo("MonitoringCtrl","MonitoringView"));
        stage.setTitle("Моніторинг білетів");
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent t) {
                for(Browser browserThread: Browser.getAllBrowserThreads()) {
                    browserThread.cancel();
                }
                Platform.exit();
            }
        });
    }

    public static void main(String[] args) {
        Data.initialize();
        launch(args);
    }

}
