package sample;

import controllers.ControllerManager;
import controllers.FindAllRoutesCtrl;
import controllers.MonitoringCtrl;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Роман on 03.03.2017.
 */
public class UZApi {
    private static HashMap<String,String> mainRailwayStations = initializeMainRaiwayStation();
    private static HashMap<String,String> initializeMainRaiwayStation() {
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put("Київ","2200001");
        temp.put("Харків","2204001");
        temp.put("Львів","2218000");
        temp.put("Одеса","2208001");
        temp.put("Дніпропетровськ-Голов.","2210700");
        temp.put("Івано-Франківськ","2218200");
        temp.put("Тернопіль","2218300");
        temp.put("Вінниця","2200200");
        temp.put("Хмельницький","2200300");
        temp.put("Жмеринка-Пас.","2200270");
        temp.put("Кривий Ріг-Гол.","2210900");
        temp.put("Коломия","2218030");
        temp.put("Конотоп","2200040");
        temp.put("Стрий","2218090");
        temp.put("Фастів 1","2200150");
        temp.put("Запоріжжя 1","2210800");
        return temp;
    }
    public static String getCityIdByName(String cityName){
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
    public static JSONArray getListOfCitiesByName(String cityName){
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
                if(cities.size()>10){
                    return new JSONArray();
                }
                return cities;
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
    public static JSONObject purchasesearch(String fromId, String toId,String from, String to, String dateDepartment){
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
            //connection.setRequestProperty("GV-Token", Data.getToken());
            connection.setRequestProperty("Referer", "http://booking.uz.gov.ua/ru/");
            connection.setRequestProperty("Content-Length", "214");
            connection.setRequestProperty("Cookie", Data.getCookies());
            connection.setRequestProperty("Connection", "keep-alive");
            String query = "station_id_from=" + fromId + "&station_id_till=" + toId + "&station_from=" + URLEncoder.encode(from,"UTF-8")+"&station_till=" + URLEncoder.encode(to,"UTF-8")+"&date_dep=" + dateDepartment + "&time_dep=00%3A00&time_dep_till=&another_ec=0&search=";
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
                System.out.println("jsonobject.error = " + jsonObject.get("error"));
                //System.out.println((String)(jsonObject.get("error")));
                if(((jsonObject.get("error")) == null)){
                    System.out.println("return jsonObject;");
                    return jsonObject;
                }
                System.out.println("return null;");
                return null;
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public static JSONObject purchasesearchWithTransfers(String fromId, String toId, String acrossId, final String across, String from, String to, String dateDepartment) throws InterruptedException {
        JSONObject beforeResponse = purchasesearch(fromId,acrossId,from,across,dateDepartment);
        Thread.sleep(3000);
        JSONObject afterResponse = purchasesearch(acrossId,toId,across,to,dateDepartment);
        if(beforeResponse==null||afterResponse==null){
            return null;
        }
        JSONArray before = (JSONArray) beforeResponse.get("value");
        JSONArray after = (JSONArray)afterResponse.get("value");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM HH:mm YYYY");
        System.out.println(dateFormatter.format(new Date(1492459680)));
        System.out.println(dateFormatter.format(new Date(139289680)));
        for(int i=0;i<before.size();i++){
            JSONObject trainBefore = (JSONObject) before.get(i);
            System.out.println(trainBefore);
            final String num = String.valueOf(trainBefore.get("num"));
            final String travelTime = String.valueOf(trainBefore.get("travel_time"));
            JSONObject fromData = (JSONObject) trainBefore.get("from");
            JSONObject toData = (JSONObject) trainBefore.get("till");
            final String fromStation = String.valueOf(fromData.get("station"));
            final String toStation = String.valueOf(toData.get("station"));
            String date = String.valueOf(fromData.get("src_date"));
            final String timeDepartment = String.valueOf(fromData.get("date"));
            final String timeArrival = String.valueOf(toData.get("date"));
           // System.out.println(String.valueOf(fromData.get("date"))+" "+ Long.valueOf(String.valueOf(fromData.get("date"))+"000"));
            final String fromDateFormatted = dateFormatter.format(new Date(Long.valueOf(String.valueOf(fromData.get("date"))+"000")));
            final String toDateFormatted = dateFormatter.format(new Date(Long.valueOf(String.valueOf(toData.get("date"))+"000")));
            for(int j=0;j<after.size();j++){
                JSONObject trainAfter = (JSONObject) after.get(j);
                System.out.println(trainAfter);
                final String _num = String.valueOf(trainAfter.get("num"));
                final String _travelTime = String.valueOf(trainAfter.get("travel_time"));
                JSONObject _fromData = (JSONObject) trainAfter.get("from");
                JSONObject _toData = (JSONObject) trainAfter.get("till");
                final String _fromStation = String.valueOf(_fromData.get("station"));
                final String _toStation = String.valueOf(_toData.get("station"));
                String _date = String.valueOf(_fromData.get("src_date"));
                final String _timeDepartment = String.valueOf(_fromData.get("date"));
                final String _timeArrival = String.valueOf(_toData.get("date"));
                final String _fromDateFormatted =dateFormatter.format(new Date(Long.valueOf(String.valueOf(_fromData.get("date"))+"000")));
                final String _toDateFormatted = dateFormatter.format(new Date(Long.valueOf(String.valueOf(_toData.get("date"))+"000")));
                if(Long.valueOf(_timeDepartment).compareTo(Long.valueOf(timeArrival))>=0){
                    System.out.println(num+"->"+_num);
                    final Train fromTrain = new Train(num,travelTime,fromStation,toStation,fromDateFormatted,toDateFormatted);
                    final Train toTrain = new Train(_num,_travelTime,_fromStation,_toStation, _fromDateFormatted,_toDateFormatted);
                    Platform.runLater(new Runnable() {
                        public void run() {
                            ((FindAllRoutesCtrl)ControllerManager.getControllers().get("FindAllRoutesCtrl")).getTrains().add(new TrainCouple(fromTrain,toTrain,across,num + " ->  " + _num,getStringFromTimelongmillis(Long.valueOf(_timeArrival)-Long.valueOf(timeDepartment)),getStringFromTimelongmillis(Long.valueOf(_timeDepartment)-Long.valueOf(timeArrival))));
                        }
                    });
                    }
        }
        }
        return null;
    }
    public static ArrayList<JSONObject> purchasesearchWithAllTransfers(String fromId, String toId , String from, String to, String dateDepartment){
        ArrayList<JSONObject> rezult = new ArrayList<JSONObject>();
        for(String across:mainRailwayStations.keySet()){
            System.out.println("transfer in " + across+"("+mainRailwayStations.get(across)+")");
            try {
                if(across.equals(from)||across.equals(to)){
                    continue;
                }
                Thread.sleep(1500);
                JSONObject oneTransfer = purchasesearchWithTransfers(fromId,toId,mainRailwayStations.get(across),across,from,to,dateDepartment);
                //rezult.add(oneTransfer);
                System.out.println("ok");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return rezult;
    }
    private static int getTimelongmillisFromString(String timestring){
        return Integer.valueOf(timestring.split(":")[0])*3600 + Integer.valueOf(timestring.split(":")[1])*60;
    }
    private static String getStringFromTimelongmillis(long timelongmillis){
        String hours = String.valueOf(Long.valueOf(timelongmillis/3600));
        String minutes = String.valueOf(Long.valueOf((timelongmillis - Integer.valueOf(hours)*3600)/60));
        return hours + "год. " + minutes+"хв.";
    }
}
/*
    Спочатку визначаємо всі міста, через які будемо шукати поїзди з пересадками.
    Після цього шукаємо поїзди з початкової до пересадкової станції, а потім з
    пересадкової до кінцевої.
*/