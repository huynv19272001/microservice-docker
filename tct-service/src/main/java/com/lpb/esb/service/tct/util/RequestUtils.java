package com.lpb.esb.service.tct.util;

import com.lpb.esb.service.common.model.response.ExecuteModel;
import com.lpb.esb.service.common.model.response.ServiceInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by tudv1 on 2022-02-28
 */
@Component
@Log4j2
public class RequestUtils {
    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity executeRequestTct(String data, ServiceInfo serviceInfo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
        // headers.set("Accept-Encoding", "gzip, deflate, br");
        headers.set("SOAPAction", "sii:T2B_GW_GETLIST_GIP");

        ClientHttpRequestFactory requestFactory = new
            HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpEntity<String> entity = new HttpEntity<>(data, headers);
        try {
            ResponseEntity response = restTemplate.postForEntity(
                serviceInfo.getConnectorURL()
                // "http://localhost:19501/transaction/bill"
                , entity
                , String.class
            );
            return response;
        } catch (RestClientException e) {
            log.error("error when execute request: {}", e.getMessage(), e);
            return null;
        }
    }

    public ExecuteModel<String> callAPI(String data, ServiceInfo serviceInfo) {
        ExecuteModel<String> executeModel = ExecuteModel.<String>builder()
            .statusCode(Long.valueOf(HttpStatus.BAD_REQUEST.value()))
            .build();
        String result = "";
        try {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            URL url = new URL(serviceInfo.getConnectorURL());
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(sslsocketfactory);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(30 * 1000);
            conn.setReadTimeout(30 * 1000);
            conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
            writer.write(data);
            writer.close();
            wr.close();
            InputStream inputstream = null;
            executeModel.setStatusCode(Long.valueOf(conn.getResponseCode()));
            try {
                inputstream = conn.getInputStream();
            } catch (Exception e) {
                inputstream = conn.getErrorStream();
            }
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

            String line;
            do {
                line = bufferedreader.readLine();
                if (line != null) {
                    result += line;
                }
            } while (line != null);

            executeModel.setData(result);
            conn.disconnect();
        } catch (SocketTimeoutException e) {
            log.error("timeout when execute request: {}", e.getMessage(), e);
            e.printStackTrace();
            executeModel.setStatusCode(-1L);
        } catch (Exception e) {
            log.error("error when execute request: {}", e.getMessage(), e);
            e.printStackTrace();
        }
        return executeModel;
    }
}
