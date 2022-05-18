package com.company.mx.framework.notifications.impl;

import com.company.mx.framework.utils.CustomException;
import com.company.mx.framework.utils.PropertiesLoader;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Twilio {

    private static final String ACCOUNT_SID = PropertiesLoader.getProperty("twilio.account.sid");
    private static final String AUTH_TOKEN = PropertiesLoader.getProperty("twilio.auth.token");
    private static String URI = "https://api.twilio.com/2010-04-01/Accounts/"+ACCOUNT_SID+"/Messages.json";
    private static final String ISO = "ISO-8859-1";
    private static final String phoneFrom = PropertiesLoader.getProperty("twilio.phone.from");
    private static final String MODE = PropertiesLoader.getProperty("twilio.mode");
    private static final Logger LOG = Logger.getLogger(Twilio.class.getName());


    public static boolean sendSMS(String phone, String msg){
        boolean flag = false;

        LOG.log(Level.INFO, "Enviando a telefono{"+phone+"}, mensaje{"+msg+"}");

        if (!MODE.equals("production"))
            return true;

        try {
            String data = "";

            data += "Body="+ URLEncoder.encode(msg, ISO);
            data += "&From="+URLEncoder.encode(phoneFrom, ISO);
            data += "&To="+URLEncoder.encode(phone, ISO);

            URL url = new URL(URI);

            HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Content-Length", String.valueOf(data.getBytes(StandardCharsets.UTF_8).length));
            httpConn.setDoOutput(true);
            httpConn.setRequestProperty("Authorization", "Basic QUMyYTc3YTVjNjliNDg1NTY3MTE3ZWIzMDZkMWU3MjU5ZTo0ODhjNWRhOGVlN2RiNzEwM2E4ZDU0ODJhN2JmYWE0Nw==");

            OutputStreamWriter osw = new OutputStreamWriter(httpConn.getOutputStream());
            osw.write(data);
            osw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));

            String s = "";
            String response;

            while ((response = br.readLine()) != null){
                s = response;
            }

            osw.close();
            br.close();

            if (s != null && s != "" && s.length() > 0){
                JSONObject res = new JSONObject(s);

                if (! res.has("sid"))
                    throw new CustomException("000", "response_from_twilio_invalid");

                flag = true;
            }

        }
        catch(Exception | CustomException e){
            LOG.log(Level.INFO, e.getMessage());
            flag = false;
        }

        return flag;
    }

    public static void main(String[] args) {
        sendSMS("+529818187588", "hola");
    }
}
