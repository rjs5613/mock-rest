package com.github.rjs5613.mockrest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.rjs5613.mockrest.MockRestApplication;
import com.github.rjs5613.mockrest.model.KeyValue;
import com.github.rjs5613.mockrest.model.RecordingSpecification;
import com.github.rjs5613.mockrest.model.RequestDetails;
import com.github.rjs5613.mockrest.util.WireMockUtils;
import com.github.tomakehurst.wiremock.recording.RecordSpecBuilder;
import com.github.tomakehurst.wiremock.recording.SnapshotRecordResult;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

@Service
public class WireMockService {
	
	/**
	 * 
	 * @param recordSpecification
	 * @return
	 */
	public List<StubMapping> recordAndStub(RecordingSpecification recordSpecification){
		List<RequestDetails> requestsToMock = recordSpecification.getRequestsToMock();
		List<KeyValue> commonHeaders = recordSpecification.getCommonHeaders();

		RecordSpecBuilder recordSpec = new RecordSpecBuilder().forTarget(recordSpecification.getHostUrl());
		recordSpecification.getAllHeaders().forEach(header->{
			recordSpec.captureHeader(header, false);
		});
		
		MockRestApplication.mockServer.startRecording(recordSpec);
		requestsToMock.forEach(requestDetails->{
			requestDetails.addHeaders(commonHeaders);
			WireMockUtils.callWireMock(requestDetails);
		});
		SnapshotRecordResult recordResult = MockRestApplication.mockServer.stopRecording();
		return recordResult.getStubMappings();
	}

}