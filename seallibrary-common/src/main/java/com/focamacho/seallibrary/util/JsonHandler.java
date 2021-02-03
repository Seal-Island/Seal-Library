package com.focamacho.seallibrary.util;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings("unused")
public class JsonHandler {

    public static JSONObject getJson(File jsonFile) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(jsonFile.getPath()));
        return new JSONObject(new String(encoded, StandardCharsets.UTF_8));
    }

    public static JSONObject getOrCreateJson(File jsonFile) throws IOException {
        if(!jsonFile.exists()) {
            boolean mk = jsonFile.getParentFile().mkdirs();
            boolean nf = jsonFile.createNewFile();
            FileUtils.write(jsonFile, "{}", StandardCharsets.UTF_8);
        }
        return getJson(jsonFile);
    }

    public static JSONObject readJsonFromURL(String url) {
        try {
            URLConnection urlConnection = new URL(url).openConnection();
            urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            try (InputStream is = urlConnection.getInputStream()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                int cp;
                while ((cp = br.read()) != -1) {
                    sb.append((char) cp);
                }
                return new JSONObject(sb.toString());
            }
        } catch (Exception ignored) {}
        return new JSONObject();
    }

    public static void saveToJson(File jsonFile, JSONObject toWrite) throws IOException {
        FileUtils.write(jsonFile, toWrite.toString(4), StandardCharsets.UTF_8);
    }

    public static String getOrCreateString(File jsonFile, String key, String value) throws IOException {
        JSONObject json = getOrCreateJson(jsonFile);
        if(json.has(key)) {
            return json.getString(key);
        } else {
            json.put(key, value);
            saveToJson(jsonFile, json);
            return value;
        }
    }

    public static boolean getOrCreateBoolean(File jsonFile, String key, boolean value) throws IOException {
        JSONObject json = getOrCreateJson(jsonFile);
        if(json.has(key)) {
            return json.getBoolean(key);
        } else {
            json.put(key, value);
            saveToJson(jsonFile, json);
            return value;
        }
    }

    public static int getOrCreateInt(File jsonFile, String key, int value) throws IOException {
        JSONObject json = getOrCreateJson(jsonFile);
        if(json.has(key)) {
            return json.getInt(key);
        } else {
            json.put(key, value);
            saveToJson(jsonFile, json);
            return value;
        }
    }

    public static JSONObject getOrCreateJsonObject(File jsonFile, String key) throws IOException {
        JSONObject json = getOrCreateJson(jsonFile);
        if(json.has(key)) {
            return json.getJSONObject(key);
        } else {
            JSONObject newObject = new JSONObject();
            json.put(key, newObject);
            saveToJson(jsonFile, json);
            return newObject;
        }
    }

    public static JSONArray getOrCreateJsonArray(File jsonFile, String key) throws IOException {
        JSONObject json = getOrCreateJson(jsonFile);
        if(json.has(key)) {
            return json.getJSONArray(key);
        } else {
            JSONArray newArray = new JSONArray();
            json.put(key, newArray);
            saveToJson(jsonFile, json);
            return newArray;
        }
    }

    public static JSONArray getOrCreateJsonArray(File jsonFile, String key, JSONArray array) throws IOException {
        JSONObject json = getOrCreateJson(jsonFile);
        if(json.has(key)) {
            return json.getJSONArray(key);
        } else {
            json.put(key, array);
            saveToJson(jsonFile, json);
            return array;
        }
    }

}