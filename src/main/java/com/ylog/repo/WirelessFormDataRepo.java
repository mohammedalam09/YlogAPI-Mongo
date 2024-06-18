package com.ylog.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ylog.entities.WirelessFormData;

public interface WirelessFormDataRepo extends MongoRepository<WirelessFormData, Long> {

	@Query("{ 'formId':?0 }")
	List<WirelessFormData> getAllFormSubmittedDataByFormId(String formId);

	
	

}
