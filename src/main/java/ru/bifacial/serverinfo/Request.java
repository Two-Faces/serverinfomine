package ru.bifacial.serverinfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.StringJoiner;

public class Request {
    public void send(String method, URL url, HashMap<String, String> data) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method.toUpperCase());
        con.setDoOutput(true);
        con.getOutputStream().write(this.hashToBytes(data));
        con.getInputStream();
    }

    private byte[] hashToBytes(HashMap<String, String> data) {
        StringJoiner string = new StringJoiner("&");
        data.forEach((k,v) -> {
            try {
                string.add(URLEncoder.encode(k, "UTF-8") + "=" + URLEncoder.encode(v, "UTF-8") );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

        System.out.println(string);
        return string.toString().getBytes(StandardCharsets.UTF_8);
    }
}
