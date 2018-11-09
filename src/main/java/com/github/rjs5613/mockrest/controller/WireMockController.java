package com.github.rjs5613.mockrest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.rjs5613.mockrest.model.RequestDetails;
import com.github.rjs5613.mockrest.util.WireMockUtils;

@RestController
public class WireMockController {
	
	@RequestMapping(value="**")
	public ResponseEntity<Object> forwardToWireMock(HttpServletRequest httpServletRequest){
		RequestDetails requestDetails = RequestDetails.fromHttpRequest(httpServletRequest);
		ResponseEntity<Object> wireMockResult = WireMockUtils.callWireMock(requestDetails);
		return wireMockResult;
	}
}
