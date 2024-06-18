package com.ylog.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ylog.entities.CustomGroupRequest;

public interface CustomGroupRepo extends MongoRepository<CustomGroupRequest, Long>{

}
