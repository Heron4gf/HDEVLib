package it.hdev.hDEVLib.weblibs;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class WebUtils {

    public static URL safeURL(String surl) {
        try {
            return new URL(surl);
        } catch (MalformedURLException e) {
            Bukkit.getLogger().warning("There was an error while creating an URL, please contact assistance for support https://hdev.it/discord");
            throw new RuntimeException(e);
        }
    }

    public static JSONObject getJSONResponse(URL url, Map<String, String> args) {
        try {
            String response = postRequest(url, args);
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(response);
        } catch (ParseException e) {
            Bukkit.getLogger().warning("Error parsing JSON response from " + url + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static String postRequest(URL url, Map<String, String> args) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept", "application/json");

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : args.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(param.getKey()).append('=').append(param.getValue());
            }

            byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(postDataBytes);
            }

            int status = connection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("HTTP error code: " + status);
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }
                return response.toString();
            }
        } catch (Exception e) {
            Bukkit.getLogger().warning("Error during HTTP request to " + url + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
