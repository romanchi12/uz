package sample;


import controllers.MonitoringCtrl;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Browser extends Task<Void> {
    private static ArrayList<Browser> allBrowserThreads = new ArrayList<Browser>();

    public static ArrayList<Browser> getAllBrowserThreads() {
        return Browser.allBrowserThreads;
    }

    private MonitoringCtrl monitoringCtrl = null;

    public Browser(MonitoringCtrl monitoringCtrl) {
        this.monitoringCtrl = monitoringCtrl;
        this.monitoringCtrl.getInformationText().textProperty().bind(this.messageProperty());
        Browser.allBrowserThreads.add(this);
    }

    @Override
    protected Void call() throws Exception {
        this.updateMessage("Моніторинг розпочинається");
        while (!isCancelled()) {
            //change to UZApi public static JSONObject getTrainsByCity()
            JSONObject jsonObject = UZApi.purchasesearch(this.monitoringCtrl.getFromId(), this.monitoringCtrl.getToId(), this.monitoringCtrl.getFrom(), this.monitoringCtrl.getTo(), this.monitoringCtrl.getDateDep());
            JSONArray value = (JSONArray) jsonObject.get("value");
            JSONObject parameters = (JSONObject) value.get(0);
            final String trainNumber = String.valueOf(parameters.get("num"));
            final Label train = this.monitoringCtrl.getTrain();
            Platform.runLater(new Runnable() {
                public void run() {
                    train.setText(trainNumber);
                }
            });
            final Label from = this.monitoringCtrl.getFromLabel();
            final Label to = this.monitoringCtrl.getToLabel();
            final Label date = this.monitoringCtrl.getDateLabel();
            final String fromtodate[] = {this.monitoringCtrl.getFrom().toUpperCase(), this.monitoringCtrl.getTo().toUpperCase(), this.monitoringCtrl.getDateDep().toUpperCase()};
            Platform.runLater(new Runnable() {
                public void run() {
                    from.setText(fromtodate[0]);
                    to.setText(fromtodate[1]);
                    date.setText(fromtodate[2]);
                }
            });
            JSONArray types = (JSONArray) parameters.get("types");
            for (int i = 0; i < types.size(); i++) {
                JSONObject type = (JSONObject) types.get(i);
                if (type.get("title").equals("Купе")) {
                    final Label cupe = this.monitoringCtrl.getCupe();
                    final String cupePlaces = String.valueOf(type.get("places"));
                    Platform.runLater(new Runnable() {
                        public void run() {
                            cupe.setText(cupePlaces);
                        }
                    });
                }
                if (type.get("title").equals("Люкс")) {
                    final Label lux = this.monitoringCtrl.getLux();
                    final String luxPlaces = String.valueOf(type.get("places"));
                    Platform.runLater(new Runnable() {
                        public void run() {
                            lux.setText(luxPlaces);
                        }
                    });
                }
                if (type.get("title").equals("Плацкарт")) {
                    final Label platzkart = this.monitoringCtrl.getPlatzkart();
                    final String platzkartPlaces = String.valueOf(type.get("places"));
                    Platform.runLater(new Runnable() {
                        public void run() {
                            platzkart.setText(platzkartPlaces);
                        }
                    });
                }
            }
            int time = 60;
            while (!isCancelled() && time > 0) {
                this.updateMessage("Час до наступного оновлення: " + --time);
                System.out.println("Час до наступного оновлення: " + time);
                Thread.currentThread().sleep(1000);
            }
        }
        this.updateMessage("Моніторинг зупинено");
        return null;
    }
}
