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

public class Browser extends Task<Void> {
    private MonitoringCtrl monitoringCtrl = null;
    public Browser(MonitoringCtrl monitoringCtrl) {
        this.monitoringCtrl = monitoringCtrl;
        this.monitoringCtrl.getInformationText().textProperty().bind(this.messageProperty());

    }

    //sd
    @Override
    protected Void call() throws Exception {
        this.updateMessage("Моніторинг розпочинається");
        while (!isCancelled()) {
            try {
                URL url = new URL("http://booking.uz.gov.ua/ru/purchase/search/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Host", "booking.uz.gov.ua");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2; rv:47.0) Gecko/20100101 Firefox/47.0");
                connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
                connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
                connection.setRequestProperty("DNT", "1");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("GV-Ajax", "1");
                connection.setRequestProperty("GV-Referer", "http://booking.uz.gov.ua/ru/");
                connection.setRequestProperty("GV-Screen", "1366x768");
                connection.setRequestProperty("GV-Token", Data.getToken());
                connection.setRequestProperty("Referer", "http://booking.uz.gov.ua/ru/");
                connection.setRequestProperty("Content-Length", "214");
                connection.setRequestProperty("Cookie", Data.getCookies());
                connection.setRequestProperty("Connection", "keep-alive");
                String query = "station_id_from=" + this.monitoringCtrl.getFromId()+"&station_id_till="+this.monitoringCtrl.getToId()+"&station_from=" + URLEncoder.encode(this.monitoringCtrl.getFrom(),"UTF-8")+"&station_till="+URLEncoder.encode(this.monitoringCtrl.getTo(),"UTF-8")+"&date_dep="+this.monitoringCtrl.getDateDep()+"&time_dep=00%3A00&time_dep_till=&another_ec=0&search=";
                connection.getOutputStream().write(query.getBytes("UTF8"));
                int responseCode = connection.getResponseCode();
                System.out.println("Response Code: " + responseCode);
                InputStream is = connection.getInputStream();
                if (responseCode == 200) {
                    String response = "";
                    byte temp[] = new byte[1024];
                    int readed = -1;
                    while ((readed = is.read(temp)) != -1) {
                        response += new String(temp, 0, readed, "UTF-8");
                    }
                    System.out.println(response);
                    JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(response);
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
                    final String fromtodate [] ={this.monitoringCtrl.getFrom().toUpperCase(),this.monitoringCtrl.getTo().toUpperCase(),this.monitoringCtrl.getDateDep().toUpperCase()};
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
                }

                int time = 60;
                while (!isCancelled()&&time>0) {
                    this.updateMessage("Час до наступного оновлення: " + --time);
                    System.out.println("Час до наступного оновлення: " + time);
                    Thread.currentThread().sleep(1000);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println("Parse exception");
            }
        }
        this.updateMessage("Моніторинг зупинено");
    return null;
    }
}
