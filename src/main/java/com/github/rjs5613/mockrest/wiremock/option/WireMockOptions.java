package com.github.rjs5613.mockrest.wiremock.option;

import com.github.rjs5613.mockrest.service.MappingServiceFactory;
import com.github.rjs5613.mockrest.wiremock.source.WireMockMappingSource;
import com.github.tomakehurst.wiremock.core.MappingsSaver;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.standalone.MappingsLoader;
import com.github.tomakehurst.wiremock.standalone.MappingsSource;

/**
 * 
 * @author Rajesh
 *
 */
public class WireMockOptions extends WireMockConfiguration {

	@Override
	public MappingsLoader mappingsLoader() {
		return getMappingSource();
	}

	@Override
	public MappingsSaver mappingsSaver() {
		return getMappingSource();
	}

	private MappingsSource getMappingSource() {
		WireMockMappingSource mappingSource = new WireMockMappingSource();
		mappingSource.setMappingService(MappingServiceFactory.getMappingService(""));
		return mappingSource;
	}

}
