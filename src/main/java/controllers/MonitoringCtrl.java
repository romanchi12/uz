package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import sample.Browser;
import sample.Main;

import java.io.IOException;

public class MonitoringCtrl extends Ctrl{
    String fromId;
    String toId;
    String from;
    String to;
    String dateDep;

    @FXML
    Label train;
    @FXML
    Label fromLabel;
    @FXML
    Label toLabel;
    @FXML
    Label dateLabel;
    @FXML
    Label cupe;
    @FXML
    Button startMonitoringBtn;
    @FXML
    Label lux;
    @FXML
    Label platzkart;
    @FXML
    Label informationText;
    @FXML
    WebView web;
    boolean working = false;
    Browser currentBrowser;
    public void startMonitoring(ActionEvent actionEvent) {
        Browser browser = new Browser(this);
        Thread browserTask = new Thread(browser);
        if(!working){
            startMonitoringBtn.setText("Зупинити");
            currentBrowser = browser;
            browserTask.start();
            working = true;
        }else{
            startMonitoringBtn.setText("Розпочати");
            currentBrowser.cancel();
            working = false;
        }
    }
    @FXML
    public void changeTrain(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        if(ControllerManager.getControllers().get("SelectingTrainCtrl")==null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/SelectingTrainView.fxml"));
            Parent trainLayout = loader.load();
            ControllerManager.addController("SelectingTrainCtrl",(SelectingTrainCtrl)loader.getController());
            Scene trainScene = new Scene(trainLayout);
            ControllerManager.addScene("SelectingTrainCtrl", trainScene);
        }
        stage.setScene(ControllerManager.getScenes().get("SelectingTrainCtrl"));
    }






    public String getFrom() {return from;}
    public void setFrom(String from) {this.from = from;}
    public String getTo() {return to;}
    public void setTo(String to) {this.to = to;}
    public String getDateDep() {return dateDep;}
    public void setDateDep(String dateDep) {this.dateDep = dateDep;}
    public String getFromId() {return fromId;}
    public void setFromId(String fromId) {this.fromId = fromId;}
    public String getToId() {return toId;}
    public void setToId(String toId) {this.toId = toId;}
    public Label getTrain() { return train; }
    public Label getFromLabel() {return fromLabel;}
    public Label getToLabel() {return toLabel;}
    public Label getDateLabel() {return dateLabel;}
    public Label getLux() {
        return lux;
    }
    public Label getCupe() {
        return cupe;
    }
    public Label getPlatzkart() {
        return platzkart;
    }
    public Label getInformationText() {
        return informationText;
    }

    String cookie;
    public void setCookie(String cookie){

    }
}
