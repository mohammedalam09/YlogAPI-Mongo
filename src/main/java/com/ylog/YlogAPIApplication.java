package com.ylog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class YlogAPIApplication {

	private static Logger logger = LoggerFactory.getLogger(YlogAPIApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(YlogAPIApplication.class, args);
		logger.info("YLogAPI Application Started!!!");
	}

}
