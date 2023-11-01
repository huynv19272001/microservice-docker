package com.lpb.esb.service.tct.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lpb.esb.service.common.model.response.LpbResCode;
import com.lpb.esb.service.common.service.ConnectInfoService;
import com.lpb.esb.service.common.model.response.EsbErrorPartnerMessage;
import com.lpb.esb.service.tct.util.config.ModuleConfig;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by tudv1 on 2022-02-28
 */
@Log4j2
@Component
public class LogicUtils {

    @Autowired
    RestTemplate restTemplateLB;

    public JSONObject convertXmlToJson(String xml) {
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(xml);
//            String jsonPrettyPrintString = xmlJSONObj.toString(4);
//            return recursiveJsonKeyConverterToLower(xmlJSONObj);
            return xmlJSONObj;
        } catch (Exception je) {
            je.printStackTrace();
            log.error("error: {}", je.getMessage(), je);
            return null;
        }
    }

    public String convertJsonToXml(String jsonString) {
        JSONObject o = new JSONObject(jsonString);
        String xml = org.json.XML.toString(o);
        return xml;
    }

    public JSONObject parseDataFromXml(String input) {
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

    public JSONObject recursiveJsonKeyConverterToLower(JSONObject jsonInput) throws JSONException {
        JsonObject jsonObject = new JsonParser().parse(jsonInput.toString()).getAsJsonObject();
        JsonObject lower = this.jsonKeysLower(jsonObject);
        return new JSONObject(lower.toString());
    }

    public JSONObject recursiveJsonKeyConverterToUpper(JSONObject jsonInput) throws JSONException {
        JsonObject jsonObject = new JsonParser().parse(jsonInput.toString()).getAsJsonObject();
        JsonObject lower = this.jsonKeysUpper(jsonObject);
        return new JSONObject(lower.toString());
    }


    public JsonArray jsonKeysLower(JsonArray arr) {
        JsonArray aux = new JsonArray();
        for (int i = 0; i < arr.size(); ++i)
            aux.add(jsonKeysLower(arr.get(i).getAsJsonObject()));
        return aux;
    }

    public JsonObject jsonKeysLower(JsonObject obj) {
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

    public JsonArray jsonKeysUpper(JsonArray arr) {
        JsonArray aux = new JsonArray();
        for (int i = 0; i < arr.size(); ++i)
            aux.add(jsonKeysUpper(arr.get(i).getAsJsonObject()));
        return aux;
    }

    public JsonObject jsonKeysUpper(JsonObject obj) {
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

    public void convertJsonObjectToArray(JSONObject jsonObject) {
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

    public void convertData(JSONObject jsonObject, ModuleConfig moduleConfig) {
        try {
            switch (moduleConfig) {
                case LPTB: {
                    convertJsonObjectToArray(jsonObject
                            .getJSONObject("DATA")
                            .getJSONObject("BODY")
                            .getJSONObject("ROW")
                            .getJSONObject("THONGTIN_NNT")
//                        .getJSONObject("SOTHUE")
                    );
                    break;
                }

                case CMND: {
                    convertJsonObjectToArray(jsonObject
                        .getJSONObject("DATA")
                        .getJSONObject("BODY")
                        .getJSONObject("ROW")
                        .getJSONObject("THONGTIN_NNT")
                    );
                    break;
                }

                case MST: {
                    convertJsonObjectToArray(jsonObject
                        .getJSONObject("DATA")
                        .getJSONObject("BODY")
                        .getJSONObject("ROW")
                        .getJSONObject("THONGTIN_NNT")
                    );
                    break;
                }

                case THUNOPMST: {
                    convertJsonObjectToArray(jsonObject
                        .getJSONObject("DATA")
                        .getJSONObject("BODY")
                        .getJSONObject("ROW")
                        .getJSONObject("THONGTIN_NNT")
                    );
                    break;
                }

                case PHANHOICHUNGTU: {
                    convertJsonObjectToArray(jsonObject
                        .getJSONObject("DATA")
                        .getJSONObject("BODY")
                        .getJSONObject("ROW")
                        .getJSONObject("PHANHOI")
                    );
                    break;
                }

                case TCN: {
                    convertJsonObjectToArray(jsonObject
                        .getJSONObject("DATA")
                        .getJSONObject("BODY")
                        .getJSONObject("ROW")
                        .getJSONObject("THONGTIN_NNT")
                    );
                    break;
                }

                case TND: {
                    convertJsonObjectToArray(jsonObject
                        .getJSONObject("DATA")
                        .getJSONObject("BODY")
                        .getJSONObject("ROW")
                        .getJSONObject("SOTHUE")
                    );
                    break;
                }
            }
        } catch (Exception e) {

        }
    }

    public LpbResCode getStatus(JSONObject tct_body, String serviceId) {
        String refDesc = "";
        String refCode = "";
        if (tct_body.has("phanhoi")) {

            refCode = tct_body
                .getJSONObject("phanhoi")
                .getString("trang_thai");


            refDesc = tct_body
//                    .getJSONObject("phanhoi")
                .getString("name");

//                EsbErrorPartnerMessageEntity entity = esbErrorPartnerMessageRepository.getErrorMessage(serviceId, tct_body
//                    .getJSONObject("phanhoi")
//                    .getString("type_phanhoi"));
        } else {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode("00")
                .errorDesc("Success")
                .build();
            return lpbResCode;
        }
        try {
            EsbErrorPartnerMessage errorPartnerMessage = ConnectInfoService.getErrorMessage(
                restTemplateLB,
                serviceId,
                "TCT",
                tct_body
                    .getJSONObject("phanhoi")
                    .getString("type_phanhoi"));
            String errorCode = errorPartnerMessage.getErrorCode();
            String errorDesc = errorPartnerMessage.getDescription();
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode(errorCode)
                .errorDesc(errorDesc)
                .refCode(refCode)
                .refDesc(refDesc)
                .build();
            return lpbResCode;
        } catch (Exception e) {
            LpbResCode lpbResCode = LpbResCode.builder()
                .errorCode("99")
                .errorDesc("Failed")
                .refCode(refCode)
                .refDesc(refDesc)
                .build();
            return lpbResCode;
        }
    }
}
