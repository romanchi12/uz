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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        /*System.out.println(new Date());
        Date now = new Date();
        long l = now.getTime();
        System.out.println(l);*/
        /*Date d = new Date((long)1494488760*1000);
        System.out.println(d.getTime());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        System.out.println(dateFormatter.format(new Timestamp((long)1494488760*1000)));
        System.out.println(dateFormatter.format(new Date(1492499680000l)));*/
    }

}
