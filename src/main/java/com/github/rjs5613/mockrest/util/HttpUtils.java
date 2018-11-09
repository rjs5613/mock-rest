package com.github.rjs5613.mockrest.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.rjs5613.mockrest.model.KeyValue;
import com.github.rjs5613.mockrest.model.RequestDetails;

@Component
public class HttpUtils {
	
	@PostConstruct
	public void init() {
		restTemplate = template;
	}
	
	@Autowired
	private RestTemplate template;
	
	private static RestTemplate restTemplate;
	
	public static Map<String, List<String>> getQueryParams(HttpServletRequest httpServletRequest) {
		Enumeration<String> attributeNames = httpServletRequest.getParameterNames();
		Map<String, List<String>> headers = new HashMap<>();
		while(attributeNames.hasMoreElements()) {
			String nextElement = attributeNames.nextElement();
			headers.put(nextElement, Arrays.asList(httpServletRequest.getParameterValues(nextElement)));
		}
		return headers;
	}

	public static Map<String, List<String>> getHeaders(HttpServletRequest httpServletRequest) {
		Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
		Map<String, List<String>> headers = new HashMap<>();
		while(headerNames.hasMoreElements()) {
			String nextElement = headerNames.nextElement();
			Enumeration<String> headers2 = httpServletRequest.getHeaders(nextElement);
			headers.put(nextElement, Collections.list(headers2));
		}
		return headers;
	}
	
	public static ResponseEntity<Object> callAPI(String host, RequestDetails requestDetails) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		for(KeyValue header : requestDetails.getHeaders()) {
			List<String> value = header.getValue();
			for(String val : value) {
				headers.set(header.getKey(), val);
			}
		}
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host).path(requestDetails.getPath());
		for(KeyValue queryParam : requestDetails.getQueryParams()) {
			for(String val : queryParam.getValue()) {
				builder.queryParam(queryParam.getKey(), val);
			}
		}

		HttpEntity<?> entity = new HttpEntity<>(headers);
		String uriString = builder.toUriString();
		return restTemplate.exchange(
		        uriString, 
		        requestDetails.getMethod(), 
		        entity, 
		        Object.class);
	}

}
