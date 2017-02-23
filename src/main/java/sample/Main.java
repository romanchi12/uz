package sample;

import controllers.Controller;
import controllers.ControllerManager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        if(ControllerManager.getControllers().get("Controller")==null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/sample.fxml"));
            Parent monitoringLayout = loader.load();
            ControllerManager.addController("Controller", (Controller)loader.getController());
            Scene monitoringScene = new Scene(monitoringLayout);
            ControllerManager.addScene("Controller", monitoringScene);
        }
        stage.setScene(ControllerManager.getScenes().get("Controller"));
        stage.setTitle("Моніторинг білетів");
        //stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        Data.initialize();
        launch(args);
    }

}
