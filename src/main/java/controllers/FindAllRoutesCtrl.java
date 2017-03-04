package controllers;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.FindAllRoutesTask;
import sample.Train;
import sample.TrainCouple;
import sample.UZApi;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Роман on 03.03.2017.
 */
public class FindAllRoutesCtrl extends Ctrl implements Initializable{

    ObservableList<TrainCouple> trains;
    public String getDateDepartment() {
        return dateDepartment;
    }

    public String getFromId() {
        return fromId;
    }

    public String getAcrossId() {
        return acrossId;
    }

    public String getToId() {
        return toId;
    }

    public TextField getFrom() {
        return from;
    }

    public TextField getAcross() {
        return across;
    }

    public TextField getTo() {
        return to;
    }
    public Button getSearchWithTransfersBtn() {
        return searchWithTransfersBtn;
    }

    String dateDepartment;
    String fromId;
    String acrossId;
    String toId;


    @FXML
    public TextField from;
    @FXML
    public CheckBox isWithTransfers;
    @FXML
    public TextField across;
    @FXML
    public TextField to;
    @FXML
    public DatePicker date;
    @FXML
    public Button searchWithTransfersBtn;
    @FXML
    public TableView<TrainCouple> trainsTable;
    @FXML
    public TableColumn<TrainCouple,String> numtonum;
    @FXML
    public TableColumn<TrainCouple,String> allTime;
    @FXML
    public void toggleTransfers(ActionEvent actionEvent) {

    }



    @FXML
    public void searchWithTransfers(ActionEvent actionEvent) {
        this.searchWithTransfersBtn.setDisable(true);
        LocalDate localDate = date.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        this.dateDepartment = new String(f.format(date));
        this.fromId = UZApi.getCityIdByName(from.getText());
        this.acrossId = UZApi.getCityIdByName(across.getText());
        this.toId = UZApi.getCityIdByName(to.getText());
        System.out.println(this.dateDepartment + " " + this.fromId + " " + this.toId + " " + this.acrossId);
        new Thread(new FindAllRoutesTask()).start();
    }

    public void backToMonitoring(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(ControllerManager.changeSceneTo("MonitoringCtrl","MonitoringView"));
    }

    public ObservableList<TrainCouple> getTrains() {
        return trains;
    }

    public void initialize(URL location, ResourceBundle resources) {
        trains = FXCollections.observableArrayList();
        numtonum.setCellValueFactory(new PropertyValueFactory<TrainCouple,String>("numtonum"));
        allTime.setCellValueFactory(new PropertyValueFactory<TrainCouple,String>("allTime"));
        trainsTable.setItems(trains);
    }
}
