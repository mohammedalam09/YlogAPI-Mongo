package com.ylog.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ylog.entities.Address;

public interface AddresssRepo extends MongoRepository<Address, String>{

}
