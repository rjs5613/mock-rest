/*******************************************************************************
 * Copyright © 2015 - 2017 Praxify Technologies, Inc. All Rights Reserved.
 * NOTICE:  All information contained herein is, and remains the property of Praxify Technologies, Inc. ("Praxify") and its suppliers, if any.  The intellectual and technical concepts contained herein are proprietary to Praxify Technologies, Inc. and its licensors and may be covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret or copyright law. Dissemination of this information or reproduction of this material is strictly forbidden unless prior written permission is obtained from Praxify.
 * No license, express or implied, by estoppel or otherwise to any other Praxify intellectual property right, and no license to any third party technology or intellectual property right, is granted herein, including but not limited to any patent right, copyright, mask work right, or other intellectual property right relating to any combination, machine, or process in which Praxify source files are used. Praxify disclaims all warranties of any kind, implied, statutory, or in any communication between them, including without limitation, the implied warranties of merchantability, non-infringement, title, and fitness for a particular purpose.
 * In no event shall Praxify be liable for any actual, special, incidental, consequential or indirect damages, however caused, including without limitation to the generality of the foregoing, loss of anticipated profits, goodwill, reputation, business receipts or contracts, costs of procurement of substitute goods or services; loss of use, data, or profits; or business interruption, losses or expenses resulting from third party claims. These limitations will apply regardless of the form of action, whether under statute, in contract or tort including negligence or any other form of action and whether or not Praxify has been advised of the possibility of such damages.
 ******************************************************************************/
package com.github.rjs5613.mockrest.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rjs5613.mockrest.model.MappingEntity;
import com.github.rjs5613.mockrest.repository.MappingRepository;
import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

@Service
public class RdbmsMappingService implements MappingService {
	
	@Autowired
	private MappingRepository mappingRepository;

	@Override
	public Collection<StubMapping> getAllMapping() {
		List<MappingEntity> findAll = mappingRepository.findAll();
		Collection<StubMapping> stubMappings= new HashSet<>();
		findAll.forEach(mappingEntity->{
			String string = mappingEntity.getStubDetails();
			StubMapping read = Json.read(string, StubMapping.class);
			stubMappings.add(read);
		});
		return stubMappings;
	}

	@Override
	public void saveMapping(StubMapping stubMapping) {
		JSONObject jsonObject = new JSONObject(Json.write(stubMapping));
		MappingEntity entity = new MappingEntity();
		entity.setStubDetails(jsonObject.toString());
		entity.setName(stubMapping.getName());
		mappingRepository.save(entity);

	}

	@Override
	public void saveAllMappings(Collection<StubMapping> stubMappings) {
		Collection<MappingEntity> mappingEntities = new HashSet<>();
		stubMappings.forEach(stubMapping->{
			JSONObject jsonObject = new JSONObject(Json.write(stubMapping));
			MappingEntity entity = new MappingEntity();
			entity.setStubDetails(jsonObject.toString());
			mappingEntities.add(entity);
		});
		mappingRepository.saveAll(mappingEntities);
	}

	@Override
	public void delete(StubMapping stubMapping) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

}
