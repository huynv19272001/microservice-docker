/**
 * @author Trung.Nguyen
 * @date 11-May-2022
 * */
package com.lpb.esb.etax.inquiry.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class RestClientUtils {

	@Autowired
	RestTemplate restTemplate;

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

}
