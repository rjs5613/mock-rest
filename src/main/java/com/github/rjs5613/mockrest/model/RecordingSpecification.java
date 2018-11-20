package com.github.rjs5613.mockrest.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecordingSpecification {

	private String hostUrl;
	private List<RequestDetails> requestsToMock;
	private List<KeyValue> commonHeaders;

	public String getHostUrl() {
		return hostUrl;
	}

	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}

	public List<RequestDetails> getRequestsToMock() {
		return requestsToMock;
	}

	public void setRequestsToMock(List<RequestDetails> requestsToMock) {
		this.requestsToMock = requestsToMock;
	}

	public List<KeyValue> getCommonHeaders() {
		if (null == commonHeaders) {
			return Collections.emptyList();
		}
		return commonHeaders;
	}

	public void setCommonHeaders(List<KeyValue> commonHeaders) {
		this.commonHeaders = commonHeaders;
	}

	public Set<String> getAllHeaders() {
		Set<String> headers = new HashSet<>();
		getCommonHeaders().forEach(keyValue -> {
			headers.add(keyValue.getKey());
		});
		getRequestsToMock().forEach(request -> {
			request.getHeaders().forEach(keyValue -> {
				headers.add(keyValue.getKey());
			});
		});
		return headers;
	}
}
