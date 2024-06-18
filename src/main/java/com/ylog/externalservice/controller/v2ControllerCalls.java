package com.ylog.externalservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylog.data.Response;
import com.ylog.externalservice.v2FeignClient;
import com.ylog.externalservice.classes.SearchCriteria;

@RestController
@RequestMapping("/v3/api/ylogapp")
@CrossOrigin("*")
public class v2ControllerCalls {

	@Autowired
	v2FeignClient client;

	@PostMapping("/login")
	public ResponseEntity<Response> doLogin(@RequestBody Map<String, Object> loginMap) {
		if (!loginMap.isEmpty() && loginMap.containsKey("userName") && loginMap.containsKey("password")) {
			ResponseEntity<Response> reponse = client.doLogin(loginMap);
			return Response.buildResponse(reponse, reponse.getStatusCode());

		}
		return Response.buildResponse("Username and Password Required!!!", HttpStatus.PRECONDITION_FAILED);
	}

	@PutMapping("/logout")
	public ResponseEntity<Response> doLogOut(@RequestHeader String authToken) {
		if (null != authToken && !authToken.isEmpty()) {
			ResponseEntity<Response> reponse = client.logout(authToken);
			return Response.buildResponse(reponse, reponse.getStatusCode());

		}
		return Response.buildResponse("Token is missing", HttpStatus.PRECONDITION_FAILED);
	}

	@GetMapping("/user/dataTable")
	public ResponseEntity<Response> getUserList(@RequestHeader String authToken) throws Exception {
		if (null != authToken && !authToken.isEmpty()) {

			String json = "{ \"sEcho\": 3, \"iDisplayStart\": 0, \"iDisplayLength\": -1, \"iSortCol_0\": 0, \"sSortDir_0\": \"asc\", \"columnNames\": [ \"\", \"formattedName\", \"userName\", \"employeeNumber\", \"roles\", \"formattedDepartmentName\", \"formattedMobileNumber\", \"emailAddress\", \"geofenceName\", \"formattedVendorName\", \"formattedStatus\" ], \"searchColumnNamesWithText\": [ \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\", \"\" ], \"cFilter\": { \"data\": [ { \"status\": \"A\" } ], \"flag\": true }, \"betweenFilter\": { \"data\": [ {} ], \"flag\": false }, \"inFilter\": { \"data\": [ {} ], \"flag\": false } }";

			SearchCriteria searchCriteria = new ObjectMapper().readValue(json, SearchCriteria.class);

			ResponseEntity<Response> reponse = client.getUserList(authToken, searchCriteria);
			return Response.buildResponse(reponse, reponse.getStatusCode());

		}
		return Response.buildResponse("Token is missing", HttpStatus.PRECONDITION_FAILED);
	}
}
