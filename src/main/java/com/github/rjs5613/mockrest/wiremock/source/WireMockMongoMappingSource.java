/**
 * 
 */
package com.github.rjs5613.mockrest.wiremock.source;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rjs5613.mockrest.service.MappingService;
import com.github.tomakehurst.wiremock.standalone.MappingsSource;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.github.tomakehurst.wiremock.stubbing.StubMappings;

/**
 * @author Rajesh
 *
 */
@Service
public class WireMockMongoMappingSource implements MappingsSource {
	
	@Autowired
	private MappingService mappingService;

	@Override
	public void loadMappingsInto(StubMappings stubMappings) {
		Collection<StubMapping> allMapping = mappingService.getAllMapping();
		allMapping.forEach(mapping->{
			stubMappings.addMapping(mapping);
		});
	}

	@Override
	public void save(List<StubMapping> stubMappings) {
		mappingService.saveAllMappings(stubMappings);

	}

	@Override
	public void save(StubMapping stubMapping) {
		mappingService.saveMapping(stubMapping);

	}

	@Override
	public void remove(StubMapping stubMapping) {
		mappingService.delete(stubMapping);

	}

	@Override
	public void removeAll() {
		mappingService.deleteAll();

	}

}
