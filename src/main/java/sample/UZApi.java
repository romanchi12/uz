package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * Created by Роман on 03.03.2017.
 */
public class UZApi {
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
            connection.setRequestProperty("GV-Token", Data.getToken());
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
                return jsonObject;
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
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
