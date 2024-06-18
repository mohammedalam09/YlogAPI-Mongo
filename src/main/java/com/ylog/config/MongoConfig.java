package com.ylog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {

	@Value("${spring.data.mongodb.uri}")
	String databaseUri;

	@Value("${spring.data.mongodb.database}")
	String databaseName;

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(MongoClients.create(databaseUri), databaseName);
	}

}
