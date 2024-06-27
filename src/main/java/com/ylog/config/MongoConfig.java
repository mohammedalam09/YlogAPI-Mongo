package com.ylog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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

	@Bean
	public RedisTemplate getRedisTemplate(RedisConnectionFactory factory) {

		RedisTemplate<String, String> template = new RedisTemplate();
		template.setConnectionFactory(factory);

		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());

		return template;

	}

}
