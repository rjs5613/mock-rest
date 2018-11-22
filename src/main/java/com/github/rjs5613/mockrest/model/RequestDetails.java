package com.github.rjs5613.mockrest.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.github.rjs5613.mockrest.util.HttpUtils;
import com.github.rjs5613.mockrest.util.WireMockUtils;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.EqualToJsonPattern;
import com.github.tomakehurst.wiremock.matching.MultiValuePattern;
import com.github.tomakehurst.wiremock.matching.RegexPattern;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import com.github.tomakehurst.wiremock.recording.RecordSpecBuilder;

/**
 * 
 * @author rrajeshkumar
 *
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class RequestDetails {
	
	private String name;

	private String path;
	private HttpMethod method;
	private Set<KeyValue> headers;
	private Set<KeyValue> queryParams;
	private String body;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = HttpMethod.valueOf(method.toUpperCase());
	}

	public Set<KeyValue> getHeaders() {
		if (Objects.isNull(headers)) {
			return Collections.emptySet();
		}
		return headers;
	}

	public void setHeaders(Set<KeyValue> headers) {
		this.headers = new TreeSet<KeyValue>(new KeyValue.KeyBasedComparator());
		this.headers.addAll(headers);
	}

	public Set<KeyValue> getQueryParams() {
		if (Objects.isNull(queryParams)) {
			return Collections.emptySet();
		}
		return queryParams;
	}

	public void setQueryParams(Set<KeyValue> queryParams) {
		this.queryParams = new TreeSet<KeyValue>(new KeyValue.KeyBasedComparator());
		this.queryParams.addAll(queryParams);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return String.format("{path=%s, method=%s, headers=%s, queryParams=%s, body=%s}", path, method, headers,
				queryParams, body);
	}

	public MappingBuilder toMappingBuilder() {
		StringValuePattern url = new RegexPattern(getPath() + ".*");
		UrlPattern urlPattern = new UrlPattern(url, true);
		MappingBuilder mappingBuilder;
		switch (getMethod()) {
		case GET:
			mappingBuilder = WireMock.get(urlPattern);
			break;
		case POST:
			mappingBuilder = WireMock.post(urlPattern);
			break;
		case PUT:
			mappingBuilder = WireMock.put(urlPattern);
			break;
		case DELETE:
			mappingBuilder = WireMock.delete(urlPattern);
			break;
		case HEAD:
			mappingBuilder = WireMock.head(urlPattern);
			break;
		case OPTIONS:
			mappingBuilder = WireMock.options(urlPattern);
			break;
		case PATCH:
			mappingBuilder = WireMock.patch(urlPattern);
			break;
		case TRACE:
			mappingBuilder = WireMock.trace(urlPattern);
			break;
		default:
			mappingBuilder = WireMock.get(urlPattern);
			break;
		}
		
		if(StringUtils.isEmpty(getName())) {
			mappingBuilder.withName(getName());
		}

		if (!StringUtils.isEmpty(getBody())) {
			StringValuePattern requestBodyPattern = new EqualToJsonPattern(getBody(), true, true);
			mappingBuilder.withRequestBody(requestBodyPattern);
		}

		for (KeyValue headers : getHeaders()) {
			for (String value : headers.getValues()) {
				mappingBuilder.withHeader(headers.getKey(), WireMockUtils.getEqulPattern(value));
			}
		}

		for (KeyValue queryParam : getQueryParams()) {
			for (String value : queryParam.getValues()) {
				mappingBuilder.withQueryParam(queryParam.getKey(), WireMockUtils.getEqulPattern(value));
			}
		}
		return mappingBuilder;
	}

	public RecordSpecBuilder toRecordSpec() {
		RecordSpecBuilder specBuilder = new RecordSpecBuilder();
		specBuilder.forTarget(getPath());
		return null;
	}

	public static RequestDetails fromHttpRequest(HttpServletRequest httpServletRequest) {
		Map<String, List<String>> headers2 = HttpUtils.getHeaders(httpServletRequest);
		Map<String, List<String>> queryParams2 = HttpUtils.getQueryParams(httpServletRequest);
		Set<KeyValue> queryParams = getKeyValue(queryParams2);
		Set<KeyValue> headers = getKeyValue(headers2);
		String path = httpServletRequest.getRequestURI();
		RequestDetails requestDetails = new RequestDetails();
		requestDetails.setPath(path);
		requestDetails.setHeaders(headers);
		requestDetails.setQueryParams(queryParams);
		requestDetails.setMethod(httpServletRequest.getMethod());
		return requestDetails;
	}

	private static Set<KeyValue> getKeyValue(Map<String, List<String>> entries) {
		Set<KeyValue> queryParams = new TreeSet<>(new KeyValue.KeyBasedComparator());
		for (Entry<String, List<String>> entry : entries.entrySet()) {
			KeyValue keyValue = new KeyValue();
			keyValue.setKey(entry.getKey());
			keyValue.setValues(entry.getValue());
			queryParams.add(keyValue);
		}
		return queryParams;
	}

	public static RequestDetails fromRequestPattern(RequestPattern request) {
		RequestDetails requestDetails = new RequestDetails();
		requestDetails.setHeaders(getKeyValuePairs(request.getHeaders()));
		requestDetails.setQueryParams(getKeyValuePairs(request.getQueryParameters()));
		requestDetails.setMethod(request.getMethod().getName());
		requestDetails.setPath(request.getUrlMatcher().getExpected());
		requestDetails.setName(request.getName());
		return requestDetails;
	}

	private static Set<KeyValue> getKeyValuePairs(Map<String, MultiValuePattern> keyValueMap) {
		Set<KeyValue> keyValues = new TreeSet<>(new KeyValue.KeyBasedComparator());
		if (Objects.nonNull(keyValueMap)) {
			for (Entry<String, MultiValuePattern> entrySet : keyValueMap.entrySet()) {
				String key = entrySet.getKey();
				MultiValuePattern value = entrySet.getValue();
				StringValuePattern valuePattern = value.getValuePattern();
				valuePattern.getValue();
				KeyValue keyValue = new KeyValue();
				keyValue.setKey(key);
				keyValue.setValues(Arrays.asList(valuePattern.getValue()));
				keyValues.add(keyValue);
			}
		}
		return keyValues;
	}

	public void addHeaders(Collection<KeyValue> commonHeaders) {
		if(null == headers) {
			headers = new TreeSet<>(new KeyValue.KeyBasedComparator());
		}
		if(!CollectionUtils.isEmpty(commonHeaders)) {
			headers.addAll(commonHeaders);
		}
	}

}
