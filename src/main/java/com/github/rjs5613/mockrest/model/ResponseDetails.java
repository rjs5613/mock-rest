package com.github.rjs5613.mockrest.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class ResponseDetails {
	
	String a = "{\"diagnosis\" : \"1\"}";
	
	private String body;
	private List<KeyValue> headers;
	private int status;
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public List<KeyValue> getHeaders() {
		if(null == headers) {
			return Collections.emptyList();
		}
		return headers;
	}
	
	public void setHeaders(List<KeyValue> headers) {
		this.headers = headers;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ResponseDefinitionBuilder toResponseDefinition() {
		ResponseDefinitionBuilder aResponse = WireMock.aResponse();
		aResponse.withBody(body);
		aResponse.withStatus(status);
		for(KeyValue header : getHeaders()) {
			for(String val  : header.getValues()) {
				aResponse.withHeader(header.getKey(), val);
			}
		}
		return aResponse;
	}

	public static ResponseDetails fromResponseDefinition(ResponseDefinition response) {
		ResponseDetails responseDetails = new ResponseDetails();
		responseDetails.setStatus(response.getStatus());
		responseDetails.setBody(response.getBody());
		HttpHeaders headers2 = response.getHeaders();
		Collection<HttpHeader> all = headers2.all();
		List<KeyValue> headers = new ArrayList<>();
		all.forEach(header->{
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(header.caseInsensitiveKey().toString());
			keyValue.setValues(header.values());
			headers.add(keyValue);
		});
		responseDetails.setHeaders(headers);
		return responseDetails;
	}
}
