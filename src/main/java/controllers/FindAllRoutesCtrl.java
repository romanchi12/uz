package controllers;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sample.*;

import java.io.IOException;
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
    ObservableList<String> citiesFrom;
    ObservableList<String> citiesAcross;
    ObservableList<String> citiesTo;
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

    public ComboBox<String> getFrom() {
        return from;
    }

    public ComboBox<String> getAcross() {
        return across;
    }

    public ComboBox<String> getTo() {
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
    public ComboBox<String> from;
    @FXML
    public CheckBox isWithTransfers;
    @FXML
    public ComboBox<String> across;
    @FXML
    public ComboBox<String> to;
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
    public void searchWithTransfers(ActionEvent actionEvent) {
        this.searchWithTransfersBtn.setDisable(true);
        LocalDate localDate = date.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        this.dateDepartment = new String(f.format(date));
        this.fromId = UZApi.getCityIdByName(from.getSelectionModel().getSelectedItem());
        this.acrossId = UZApi.getCityIdByName(across.getSelectionModel().getSelectedItem());
        this.toId = UZApi.getCityIdByName(to.getEditor().getText());
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
        citiesFrom = FXCollections.observableArrayList();
        citiesAcross = FXCollections.observableArrayList();
        citiesTo = FXCollections.observableArrayList();
        numtonum.setCellValueFactory(new PropertyValueFactory<TrainCouple,String>("numtonum"));
        allTime.setCellValueFactory(new PropertyValueFactory<TrainCouple,String>("allTime"));
        trainsTable.setItems(trains);
        trainsTable.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(ControllerManager.changeSceneTo("TrainDetailsCtrl","TrainDetailsView"));
                Platform.runLater(new Runnable(){
                    public void run() {
                        TrainDetailsCtrl trainDetailsCtrl = (TrainDetailsCtrl)ControllerManager.getControllers().get("TrainDetailsCtrl");
                        TrainCouple selected = trainsTable.getSelectionModel().getSelectedItem();
                        trainDetailsCtrl.trainToNum.setText(selected.getTo().getNum());
                        trainDetailsCtrl.trainToName.setText(selected.getTo().getFromStation()+" - " + selected.getTo().getToStation());
                        trainDetailsCtrl.trainFromNum.setText(selected.getFrom().getNum());
                        trainDetailsCtrl.trainFromName.setText(selected.getFrom().getFromStation()+" - " + selected.getFrom().getToStation());
                        trainDetailsCtrl.transfer.setText(selected.getTransfer());
                        trainDetailsCtrl.trainFromDate.setText(selected.getFrom().getTimeDepartment());
                        trainDetailsCtrl.trainToDate.setText(selected.getTo().getTimeDepartment());
                    }
                });
            }
        });
        from.setItems(citiesFrom);
        from.setVisibleRowCount(10);
        from.autosize();
        from.getEditor().setOnKeyTyped(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                citiesFrom.clear();
                //String city = ((ComboBox<String>) event.getSource()).getSelectionModel().getSelectedItem();
                JSONArray citiesList = UZApi.getListOfCitiesByName(from.getEditor().getText());
                for(int i=0;i<citiesList.size();i++){
                    JSONObject city = (JSONObject) citiesList.get(i);
                    String cityName = String.valueOf(city.get("label"));
                    citiesFrom.add(cityName);
                }
                from.show();
            }
        });
        across.disableProperty().bind(isWithTransfers.selectedProperty().not());
        across.setItems(citiesAcross);
        across.setVisibleRowCount(10);
        across.autosize();
        across.getEditor().setOnKeyTyped(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                citiesAcross.clear();
                //String city = ((ComboBox<String>) event.getSource()).getSelectionModel().getSelectedItem();
                JSONArray citiesList = UZApi.getListOfCitiesByName(across.getEditor().getText());
                for(int i=0;i<citiesList.size();i++){
                    JSONObject city = (JSONObject) citiesList.get(i);
                    String cityName = String.valueOf(city.get("label"));
                    citiesAcross.add(cityName);
                }
                across.show();
            }
        });
        to.setItems(citiesTo);
        to.setVisibleRowCount(10);
        to.autosize();
        to.getEditor().setOnKeyTyped(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                citiesTo.clear();
                //String city = ((ComboBox<String>) event.getSource()).getSelectionModel().getSelectedItem();
                JSONArray citiesList = UZApi.getListOfCitiesByName(to.getEditor().getText());
                for(int i=0;i<citiesList.size();i++){
                    JSONObject city = (JSONObject) citiesList.get(i);
                    String cityName = String.valueOf(city.get("label"));
                    citiesTo.add(cityName);
                }
                to.show();
            }
        });
    }



}
