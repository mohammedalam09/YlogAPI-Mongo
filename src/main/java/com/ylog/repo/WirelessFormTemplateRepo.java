package com.ylog.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ylog.entities.WirelessRequest;

public interface WirelessFormTemplateRepo extends MongoRepository<WirelessRequest, String> {

//	@Query(value = "{}" ,fields = "{'formName':1}")
//	public List<String> getAllFormNames();

	@Query(value = "{'_id':?0 }")
	public WirelessRequest getFormMetadata(String formId);

	@Query(value = "{'formName':?0}", exists = true)
	public Boolean isFormExistsWithName(String formName);

}
