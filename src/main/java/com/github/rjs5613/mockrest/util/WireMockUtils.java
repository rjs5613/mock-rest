package com.github.rjs5613.mockrest.util;

import org.springframework.http.ResponseEntity;

import com.github.rjs5613.mockrest.constant.WebConstants;
import com.github.rjs5613.mockrest.model.RequestDetails;
import com.github.tomakehurst.wiremock.matching.EqualToPattern;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;

public class WireMockUtils {
	
	private static int port;
	private static String host = WebConstants.LOCALHOST_URL;

	public static StringValuePattern getEqulPattern(String value) {
		return new EqualToPattern(value);
	}

	public static ResponseEntity<Object> callWireMock(RequestDetails requestDetails) {
		return HttpUtils.callAPI(getBaseUrl(), requestDetails);
	}
	
	public static void setWireMockPort(int port) {
		WireMockUtils.port = port;
	}

	public static String getBaseUrl() {
		return new StringBuilder(host).append(":").append(port).toString();
	}

}
 