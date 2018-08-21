/**
 * 
 */
package com.github.rjs5613.mockrest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

/**
 * @author Rajesh
 *
 */
@Service
class MongoMappingService implements MappingService {
	
	private static final String MAPPINGS_COLLECTION = "mappings";
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Collection<StubMapping> getAllMapping() {
		List<JSONObject> findAll = mongoTemplate.findAll(JSONObject.class, MAPPINGS_COLLECTION);
		Collection<StubMapping> mappings = new ArrayList<>();
		findAll.forEach(json->{
			String string = json.toString();
			StubMapping read = Json.read(string, StubMapping.class);
			mappings.add(read);
		});
		return mappings;
	}

	@Override
	public void saveMapping(StubMapping stubMapping) {
		JSONObject jsonObject = new JSONObject(Json.write(stubMapping));
		mongoTemplate.save(jsonObject, MAPPINGS_COLLECTION);
	}

	@Override
	public void saveAllMappings(Collection<StubMapping> stubMappings) {
		stubMappings.forEach(mapping->{
			saveMapping(mapping);
		});
	}

	@Override
	public void delete(StubMapping stubMapping) {
		JSONObject jsonObject = new JSONObject(Json.write(stubMapping));
		mongoTemplate.remove(jsonObject, MAPPINGS_COLLECTION);
	}

	@Override
	public void deleteAll() {
		
	}
}
