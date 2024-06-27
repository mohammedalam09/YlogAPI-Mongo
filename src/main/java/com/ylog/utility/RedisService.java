package com.ylog.utility;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RedisService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public <T> T get(String key, Class<T> className) throws Exception {
//		try {
//			System.out.println(new Date());
			String valueFromCache = redisTemplate.opsForValue().get(key);
//			System.out.println(new Date());
			return null != valueFromCache ? new ObjectMapper().readValue(valueFromCache, className) : null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}

	}

	public void set(String key, String value, Long timeToLive) {
		try {
			redisTemplate.opsForValue().set(key, value, timeToLive, TimeUnit.MINUTES);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
