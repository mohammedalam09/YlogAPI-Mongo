package com.ylog.externalservice;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ylog.data.Response;
import com.ylog.externalservice.classes.SearchCriteria;

@FeignClient(name = "YLogAPI", url = "${rest.uri}")
public interface v2FeignClient {

	@PostMapping("/login")
	public ResponseEntity<Response> doLogin(@RequestBody Map<String, Object> loginMap);
	
	@PutMapping("/logout")
	public ResponseEntity<Response> logout(@RequestHeader String authToken);

	@PostMapping("/user/dataTable")
	public ResponseEntity<Response> getUserList(@RequestHeader String authToken,@RequestBody SearchCriteria searchCriteria);

}
