package com.ylog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableFeignClients
@EnableRetry
public class YlogAPIApplication {

	// Making it static and final to prevent this from sharing this logger instance accidentally with other class during execution
	private static final Logger logger = LoggerFactory.getLogger(YlogAPIApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(YlogAPIApplication.class, args);
		logger.info("YLogAPI Application Started!!!");
	}

}
