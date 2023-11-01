package com.lpb.esb.service.common.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by tudv1 on 2022-05-04
 */
@Log4j2
public class LogicUtils {
    public static JSONObject convertXmlToJson(String xml) {
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(xml);
            return xmlJSONObj;
        } catch (Exception je) {
            je.printStackTrace();
            log.error("error: {}", je.getMessage(), je);
            return null;
        }
    }

    public static String convertJsonToXml(String jsonString) {
        JSONObject o = new JSONObject(jsonString);
        String xml = org.json.XML.toString(o);
        return xml;
    }

    public static JSONObject parseDataFromXml(String input) {
        try {
            javax.xml.parsers.DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();

            dbf.setNamespaceAware(true);
            dbf.setIgnoringElementContentWhitespace(true);
            dbf.setValidating(false);
            InputStream inputStream = new ByteArrayInputStream(input.getBytes());
            Document doc = dbf.newDocumentBuilder().parse(inputStream);
            String result = doc.getElementsByTagName("return").item(0).getTextContent();
            JSONObject jsonObject = convertXmlToJson(result);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject recursiveJsonKeyConverterToLower(JSONObject jsonInput) throws JSONException {
        JsonObject jsonObject = new JsonParser().parse(jsonInput.toString()).getAsJsonObject();
        JsonObject lower = jsonKeysLower(jsonObject);
        return new JSONObject(lower.toString());
    }

    public static JSONObject recursiveJsonKeyConverterToUpper(JSONObject jsonInput) throws JSONException {
        JsonObject jsonObject = new JsonParser().parse(jsonInput.toString()).getAsJsonObject();
        JsonObject lower = jsonKeysUpper(jsonObject);
        return new JSONObject(lower.toString());
    }


    private static JsonArray jsonKeysLower(JsonArray arr) {
        JsonArray aux = new JsonArray();
        for (int i = 0; i < arr.size(); ++i)
            aux.add(jsonKeysLower(arr.get(i).getAsJsonObject()));
        return aux;
    }

    private static JsonObject jsonKeysLower(JsonObject obj) {
        JsonObject aux = new JsonObject();
        if (obj.isJsonPrimitive()) return obj;
        if (obj.isJsonNull()) return null;
        // Iterate all properties
        for (String o : obj.keySet()) {
            if (obj.get(o).isJsonPrimitive())
                aux.addProperty(o.toLowerCase(), obj.get(o).getAsString());
            else if (obj.get(o).isJsonArray())
                aux.add(o.toLowerCase(), jsonKeysLower(obj.get(o).getAsJsonArray()));
            else
                aux.add(o.toLowerCase(), jsonKeysLower(obj.get(o).getAsJsonObject()));
        }
        return aux;
    }

    private static JsonArray jsonKeysUpper(JsonArray arr) {
        JsonArray aux = new JsonArray();
        for (int i = 0; i < arr.size(); ++i)
            aux.add(jsonKeysUpper(arr.get(i).getAsJsonObject()));
        return aux;
    }

    private static JsonObject jsonKeysUpper(JsonObject obj) {
        JsonObject aux = new JsonObject();
        if (obj.isJsonPrimitive()) return obj;
        if (obj.isJsonNull()) return null;
        // Iterate all properties
        for (String o : obj.keySet()) {
            if (obj.get(o).isJsonPrimitive())
                aux.addProperty(o.toUpperCase(), obj.get(o).getAsString());
            else if (obj.get(o).isJsonArray())
                aux.add(o.toUpperCase(), jsonKeysUpper(obj.get(o).getAsJsonArray()));
            else
                aux.add(o.toUpperCase(), jsonKeysUpper(obj.get(o).getAsJsonObject()));
        }
        return aux;
    }

    public static void convertJsonObjectToArray(JSONObject jsonObject) {
        for (Object key : jsonObject.keySet()) {
            Object val = jsonObject.get(key.toString());
            if (val instanceof JSONObject) {
                convertJsonObjectToArray((JSONObject) val);
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(val);
                jsonObject.put(key.toString(), jsonArray);
            } else if (val instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) val;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    convertJsonObjectToArray(obj);
                }
            }
        }
    }

    public static String createPassEsb(String orgPass, String keyRsa) {

        return null;
    }
}
