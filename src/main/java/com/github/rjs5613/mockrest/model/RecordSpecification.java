package com.github.rjs5613.mockrest.model;

import java.util.List;

public class RecordSpecification {
	
	private String recordingUrl;
	private List<RequestDetails> requestsToMock;
	private List<KeyValue> commonHeaders;
	
	public String getRecordingUrl() {
		return recordingUrl;
	}
	
	public void setRecordingUrl(String recordingUrl) {
		this.recordingUrl = recordingUrl;
	}
	
	public List<RequestDetails> getRequestsToMock() {
		return requestsToMock;
	}
	
	public void setRequestsToMock(List<RequestDetails> requestsToMock) {
		this.requestsToMock = requestsToMock;
	}
	
	public List<KeyValue> getCommonHeaders() {
		return commonHeaders;
	}
	
	public void setCommonHeaders(List<KeyValue> commonHeaders) {
		this.commonHeaders = commonHeaders;
	}
}
