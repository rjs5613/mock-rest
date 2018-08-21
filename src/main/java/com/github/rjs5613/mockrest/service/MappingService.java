package com.github.rjs5613.mockrest.service;

import java.util.Collection;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;

/**
 * 
 * @author rrajeshkumar
 *
 */
public interface MappingService {

	/**
	 * 
	 * @return
	 */
	Collection<StubMapping> getAllMapping();

	/**
	 * 
	 * @param stubMapping
	 */
	void saveMapping(StubMapping stubMapping);

	/**
	 * 
	 * @param stubMappings
	 */
	void saveAllMappings(Collection<StubMapping> stubMappings);

	/**
	 * 
	 * @param stubMapping
	 */
	void delete(StubMapping stubMapping);

	/**
	 * 
	 */
	void deleteAll();

}
