package com.github.rjs5613.mockrest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.rjs5613.mockrest.wiremock.option.WireMockMongoOptions;
import com.github.tomakehurst.wiremock.WireMockServer;

@SpringBootApplication
public class MockRestApplication {
	
	@Value("${wiremock.port}")
	private int port;

	public static void main(String[] args) {
		SpringApplication.run(MockRestApplication.class, args);
		new WireMockStarter().startWireMock();
	}


	
	private static class WireMockStarter{
		
		private void startWireMock() {
			WireMockMongoOptions options = new WireMockMongoOptions();
			options.port(8081);
			WireMockServer mockServer = new WireMockServer(options);
			mockServer.start();
		}
	}
}
