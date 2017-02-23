package sample;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.List;

/**
 * Created by Роман on 22.02.2017.
 */
public class Data {
    private static String token;
    private static String cookies;

    public static String getToken() {
        return token;
    }

    public static String getCookies() {
        return cookies;
    }

   public static void initialize(){
       URL url = null;
       try {
           CookieManager manager = new CookieManager();
           manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
           CookieHandler.setDefault(manager);
           url = new URL("http://booking.uz.gov.ua");
           HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
           connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2; rv:47.0) Gecko/20100101 Firefox/47.0");
           connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
           connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
           connection.setRequestProperty("Connection", "keep-alive");
           if(connection.getResponseCode()==200){
               CookieStore cookieJar =  manager.getCookieStore();
               List<HttpCookie> cookies = cookieJar.getCookies();
               for (HttpCookie cookie: cookies) {
                   Data.cookies += cookie+";";
               }
           }
           InputStream is = connection.getInputStream();
           byte buff[] = new byte[1048576];
           int readed = 0;
           String response = "";
           while((readed = is.read(buff))!=-1){
               response += new String(buff,0,readed,"UTF-8");
           }
           String key = "Common.setOpacHover($$v('#footer .right a'), 70);});";
           if(response.contains("Common.setOpacHover($$v('#footer .right a'), 70);});")){
               String rawToken = new JJencoder().decode(response.substring(response.lastIndexOf(key)+key.length(),response.lastIndexOf("</script></body></html>")));
               Data.token = rawToken.substring(34,66);
           }
       } catch (MalformedURLException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

   }
}
