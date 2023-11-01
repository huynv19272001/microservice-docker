/**
 * @author Trung.Nguyen
 * @date 21-Apr-2022
 * */
package com.lpb.esb.flex.query.model.soapclient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class FlexSoapWSClient {
	
	protected String callWSSoap(String url, String request) {
		RestTemplate restTemplate = null;
		try {
			if ((url == null) || (url.trim().equals("")))
				return null;
			if ((url == request) || (request.trim().equals("")))
				return null;
			
			HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> entity = new HttpEntity<>(request, headers);
			restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
			if ((response == null) || (response.getBody() == null))
				return null;
			String body = response.getBody();
            log.info("status: [{}] body {}", response.getStatusCode(), body);
			return body;
		} catch (RestClientResponseException ex) {
			String body = ex.getResponseBodyAsString();
            log.error("status: [{}] body {} exception: {}", ex.getRawStatusCode(), body, ex.getMessage(), ex);
            ex.printStackTrace();
			return null;
		}
	}

}
