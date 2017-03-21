package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Роман on 05.03.2017.
 */
public class TrainDetailsCtrl extends Ctrl {
    @FXML
    public Label trainFromNum;
    @FXML
    public Label trainToNum;
    @FXML
    public Label trainFromName;
    @FXML
    public Label trainToName;
    @FXML
    public Label trainFromDate;
    @FXML
    public Label trainToDate;
    @FXML
    public Button closeBtn;
    @FXML
    public Label transfer;
    public void backToFindAllRoutes(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(ControllerManager.changeSceneTo("FindAllRoutesCtrl","FindAllRoutesView"));
    }
}
