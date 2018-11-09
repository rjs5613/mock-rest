package com.github.rjs5613.mockrest;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.rjs5613.mockrest.util.WireMockUtils;
import com.github.rjs5613.mockrest.wiremock.option.WireMockMongoOptions;
import com.github.tomakehurst.wiremock.WireMockServer;

@SpringBootApplication
public class MockRestApplication {
	
	public static WireMockServer mockServer;
	
	@Value("${wiremock.port}")
	private int port;
	
	private static int wireMockPort;
	
	@PostConstruct
	public void init() {
		wireMockPort = port;
		WireMockUtils.setWireMockPort(port);
	}

	public static void main(String[] args) {
		SpringApplication.run(MockRestApplication.class, args);
		new WireMockStarter().startWireMock();
	}
	
	private static class WireMockStarter{
		private void startWireMock() {
			WireMockMongoOptions options = new WireMockMongoOptions();
			options.port(wireMockPort);
			mockServer = new WireMockServer(options);
			mockServer.start();
		}
	}
}
