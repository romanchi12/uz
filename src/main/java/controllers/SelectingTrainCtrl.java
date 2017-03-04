package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import sample.Data;
import sample.Main;
import sample.UZApi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Роман on 27.01.2017.
 */
public class SelectingTrainCtrl extends Ctrl{
    @FXML
    TextField from;
    @FXML
    TextField to;
    String fromId;
    String toId;
    @FXML
    DatePicker date;
    public void backToMonitoring(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(ControllerManager.changeSceneTo("MonitoringCtrl", "MonitoringView"));
    }

    public void refresh(ActionEvent actionEvent) {
        MonitoringCtrl monitoringCtrl = (MonitoringCtrl) ControllerManager.getControllers().get("MonitoringCtrl");
        monitoringCtrl.setFrom(new String(from.getText()));
        monitoringCtrl.setTo(new String(to.getText()));
        monitoringCtrl.setFromId(UZApi.getCityIdByName(from.getText()));
        monitoringCtrl.setToId(UZApi.getCityIdByName(to.getText()));
        LocalDate localDate = date.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println(f.format(date));
        monitoringCtrl.setDateDep(new String(f.format(date)));
    }
}
