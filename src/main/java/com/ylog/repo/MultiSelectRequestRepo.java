package com.ylog.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ylog.entities.MultiSelectRequest;

public interface MultiSelectRequestRepo extends MongoRepository<MultiSelectRequest, String> {

	@Query(value = "{'formId' : ?0}")
	List<MultiSelectRequest> findByFormId(String formId);

}
