package com.github.rjs5613.mockrest.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.rjs5613.mockrest.MockRestApplication;
import com.github.rjs5613.mockrest.model.MappingDetails;
import com.github.rjs5613.mockrest.model.RecordSpecification;
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

	@PostMapping("/create")
	public MappingDetails createMapping(@RequestBody MappingDetails mappingDetails) {
		MockRestApplication.mockServer.stubFor(mappingDetails.toMappingBuilder());
		return mappingDetails;
	}

	@PostMapping("/record")
	public Set<MappingDetails> recordAndStubApi(@RequestBody RecordSpecification recordSpecification) {
		List<StubMapping> recordAndStub = wireMockService.recordAndStub(recordSpecification);
		Set<MappingDetails> mappingDetails = new HashSet<>();
		recordAndStub.forEach(mapping->{
			mappingDetails.add(MappingDetails.fromStubMapping(mapping));
		});
		return mappingDetails;
	}
}
