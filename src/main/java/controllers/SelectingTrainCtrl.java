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
        if(ControllerManager.getControllers().get("MonitoringCtrl") == null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/MonitoringView.fxml"));
            Parent monitoringLayout = loader.load();
            ControllerManager.addController("MonitoringCtrl",(MonitoringCtrl)loader.getController());
            Scene monitoringScene = new Scene(monitoringLayout);
            ControllerManager.addScene("MonitoringCtrl", monitoringScene);
        }
        stage.setScene(ControllerManager.getScenes().get("MonitoringCtrl"));
    }

    public void refresh(ActionEvent actionEvent) {
        MonitoringCtrl monitoringCtrl = (MonitoringCtrl) ControllerManager.getControllers().get("MonitoringCtrl");
        monitoringCtrl.setFrom(new String(from.getText()));
        monitoringCtrl.setTo(new String(to.getText()));
        monitoringCtrl.setFromId(new String(getCity(from.getText())));
        monitoringCtrl.setToId(new String(getCity(to.getText())));
        LocalDate localDate = date.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println(f.format(date));
        monitoringCtrl.setDateDep(new String(f.format(date)));
    }
    public String getCity(String cityName){
        try {
            MonitoringCtrl monitoringCtrl = (MonitoringCtrl) ControllerManager.getControllers().get("MonitoringCtrl");
            URL url = new URL("http://booking.uz.gov.ua/purchase/station/?term="+URLEncoder.encode(cityName,"UTF-8"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Host", "booking.uz.gov.ua");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.2; rv:47.0) Gecko/20100101 Firefox/47.0");
            connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
            connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
            connection.setRequestProperty("Accept-Encoding","gzip, deflate");
            connection.setRequestProperty("DNT", "1");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Referer","http://booking.uz.gov.ua/ru/");
            connection.setRequestProperty("Content-Length","0");
            connection.setRequestProperty("Cookie", Data.getCookies());
            connection.setRequestProperty("Connection", "keep-alive");
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            InputStream is = connection.getInputStream();
            if(responseCode==200){
                String response = "";
                byte temp[] = new byte [1024];
                int readed = -1;
                while ((readed = is.read(temp))!=-1){
                    response += new String(temp,0,readed,"UTF-8");
                }
                System.out.println(response);
                JSONArray cities = (JSONArray) JSONValue.parseWithException(response);
                for(int i=0;i<cities.size();i++){
                    JSONObject city = (JSONObject) cities.get(i);
                    if(String.valueOf(city.get("label")).toLowerCase().equals(cityName.toLowerCase())){
                        return String.valueOf(city.get("value"));
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
