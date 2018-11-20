package com.github.rjs5613.mockrest.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.rjs5613.mockrest.MockRestApplication;
import com.github.rjs5613.mockrest.model.MappingDetails;
import com.github.rjs5613.mockrest.model.RecordingSpecification;
import com.github.rjs5613.mockrest.service.MappingService;
import com.github.rjs5613.mockrest.service.WireMockService;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

/**
 * 
 * @author rrajeshkumar
 *
 */
@RestController
@RequestMapping("/mappings")
public class MappingController {
	
	@Autowired
	private WireMockService wireMockService;
	
	@Autowired
	private MappingService mappingService;
	
	@GetMapping
	public Set<MappingDetails> getAllMappings(){
		Set<MappingDetails> mappingDetails = new HashSet<>();
		Collection<StubMapping> allMapping = mappingService.getAllMapping();
		allMapping.forEach(mapping->{
			mappingDetails.add(MappingDetails.fromStubMapping(mapping));
		});
		return mappingDetails;
	}

	@PostMapping("/create")
	public MappingDetails createMapping(@RequestBody MappingDetails mappingDetails) {
		MockRestApplication.mockServer.stubFor(mappingDetails.toMappingBuilder());
		return mappingDetails;
	}

	@PostMapping("/record")
	public Set<MappingDetails> recordAndStubApi(@RequestBody RecordingSpecification recordSpecification) {
		List<StubMapping> recordAndStub = wireMockService.recordAndStub(recordSpecification);
		Set<MappingDetails> mappingDetails = new HashSet<>();
		recordAndStub.forEach(mapping->{
			mappingDetails.add(MappingDetails.fromStubMapping(mapping));
		});
		return mappingDetails;
	}
}
