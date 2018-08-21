package com.github.rjs5613.mockrest.wiremock.option;

import com.github.rjs5613.mockrest.config.SpringContext;
import com.github.rjs5613.mockrest.wiremock.source.WireMockMongoMappingSource;
import com.github.tomakehurst.wiremock.core.MappingsSaver;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.standalone.MappingsLoader;
import com.github.tomakehurst.wiremock.standalone.MappingsSource;

/**
 * 
 * @author Rajesh
 *
 */
public class WireMockMongoOptions extends WireMockConfiguration {

	@Override
	public MappingsLoader mappingsLoader() {
		return getMappingSource();
	}

	@Override
	public MappingsSaver mappingsSaver() {
		return getMappingSource();
	}

	private MappingsSource getMappingSource() {
		return SpringContext.getAppContext().getBean(WireMockMongoMappingSource.class);
	}

}
