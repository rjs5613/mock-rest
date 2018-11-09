package com.github.rjs5613.mockrest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.rjs5613.mockrest.MockRestApplication;
import com.github.rjs5613.mockrest.model.RecordSpecification;
import com.github.rjs5613.mockrest.model.RequestDetails;
import com.github.rjs5613.mockrest.util.WireMockUtils;
import com.github.tomakehurst.wiremock.recording.SnapshotRecordResult;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

@Service
public class WireMockService {
	
	public List<StubMapping> recordAndStub(RecordSpecification recordSpecification){
		MockRestApplication.mockServer.startRecording(recordSpecification.getRecordingUrl());
		List<RequestDetails> requestsToMock = recordSpecification.getRequestsToMock();
		requestsToMock.forEach(requestDetails->{
			WireMockUtils.callWireMock(requestDetails);
		});
		SnapshotRecordResult stopRecording = MockRestApplication.mockServer.stopRecording();
		return stopRecording.getStubMappings();
	}

}
