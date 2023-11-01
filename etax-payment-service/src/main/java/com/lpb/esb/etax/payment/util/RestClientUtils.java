/**
 * @author Trung.Nguyen
 * @date 11-May-2022
 */
package com.lpb.esb.etax.payment.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
@Log4j2
public class RestClientUtils {

    @Autowired
    RestTemplate restTemplate;
    @Value("${service.file.convert2pdf}")
    private String convert2pdfUrl;

    public <T> ResponseEntity<String> doPost(String url, HttpEntity<T> body) {
        try {
            ResponseEntity<String> restResponse = restTemplate.postForEntity(url, body, String.class);
            return restResponse;
        } catch (HttpClientErrorException ex) {
            String exBody = ex.getResponseBodyAsString();
            log.error("doPost, HttpClientErrorEx: " + exBody);
            return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
        } catch (RestClientResponseException ex) {
            String exBody = ex.getResponseBodyAsString();
            log.error("doPost, RestClientResponseEx: " + exBody);
            return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
        }
    }

    public <T> ResponseEntity<String> doGet(String url, HttpEntity<T> body) {
        try {
            ResponseEntity<String> restResponse = restTemplate.exchange(url, HttpMethod.GET, body, String.class);
            return restResponse;
        } catch (HttpClientErrorException ex) {
            String exBody = ex.getResponseBodyAsString();
            log.error("doGet, HttpClientErrorEx: " + exBody);
            return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
        } catch (RestClientResponseException ex) {
            String exBody = ex.getResponseBodyAsString();
            log.error("doGet, RestClientResponseEx: " + exBody);
            return ResponseEntity.status(ex.getRawStatusCode()).body(ex.getResponseBodyAsString());
        }
    }

    public Resource sendFileConvertBody(Resource resource, String outputDir) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", resource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//            String serverUrl = "http://file-converter-service/docx/convert-2-pdf";
//            ResponseEntity<Resource> response = restTemplate.postForEntity(serverUrl, requestEntity, Resource.class);
            ResponseEntity<Resource> response = restTemplate.postForEntity(convert2pdfUrl, requestEntity, Resource.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Resource bodyRes = response.getBody();
                String fileName = bodyRes.getFilename();
                fileName = URLDecoder.decode(fileName, String.valueOf(StandardCharsets.ISO_8859_1));

                //save to examine file
                File targetFile = new File(outputDir + fileName);
                FileUtils.copyInputStreamToFile(response.getBody().getInputStream(), targetFile);
                return bodyRes;
            }

            return null;
        } catch (RestClientResponseException ex) {
            String exBody = ex.getResponseBodyAsString();
            log.error("RestClientResponseEx: " + exBody);
            return null;
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage(), e);
            return null;
        }
    }

}
