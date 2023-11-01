package com.lpb.esb.service.config.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;

public class JsonUtil {
    private static XmlMapper XML_MAPPER = new XmlMapper();
    private static ObjectMapper JSON_MAPPER = new ObjectMapper();

    public static ObjectMapper getJsonMapper(){
        JSON_MAPPER.setPropertyNamingStrategy(
            PropertyNamingStrategy.UPPER_CAMEL_CASE);
        return JSON_MAPPER;
    }

    public static XmlMapper getXmlMapper(){
        return XML_MAPPER;
    }

    public static String xmlToJson(String xml){
        try {
            return getJsonMapper().writeValueAsString(getXmlMapper().readTree(xml));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
