package com.github.rjs5613.mockrest.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class MappingDetails {

	private RequestDetails requestDetails;
	private ResponseDetails responseDetails;

	public RequestDetails getRequestDetails() {
		return requestDetails;
	}

	public void setRequestDetails(RequestDetails requestDetails) {
		this.requestDetails = requestDetails;
	}

	public ResponseDetails getResponseDetails() {
		return responseDetails;
	}

	public void setResponseDetails(ResponseDetails responseDetails) {
		this.responseDetails = responseDetails;
	}

	@Override
	public String toString() {
		return String.format("{requestDetails=%s, responseDetails=%s}", requestDetails, responseDetails);
	}
	
	public MappingBuilder toMappingBuilder() {
		MappingBuilder mappingBuilder = requestDetails.toMappingBuilder();
		mappingBuilder.persistent();
		mappingBuilder.willReturn(responseDetails.toResponseDefinition());
		return mappingBuilder;
	}
	
	public static MappingDetails fromStubMapping(StubMapping stubMapping) {
		MappingDetails mappingDetails = new MappingDetails();
		mappingDetails.setRequestDetails(RequestDetails.fromRequestPattern(stubMapping.getRequest()));
		mappingDetails.setResponseDetails(ResponseDetails.fromResponseDefinition(stubMapping.getResponse()));
		stubMapping.getRequest();
		return mappingDetails;
	}

	

}
