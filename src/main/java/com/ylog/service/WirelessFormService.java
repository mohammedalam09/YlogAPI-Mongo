package com.ylog.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.ylog.data.Response;
import com.ylog.entities.CustomGroupRequest;
import com.ylog.entities.WirelessFormData;
import com.ylog.entities.WirelessRequest;

public interface WirelessFormService {

	ResponseEntity<Response> addWirelessFormTemplate(WirelessRequest wirelessRequest) throws Exception;

	ResponseEntity<Response> viewWirelessFormTemplates();

	ResponseEntity<Response> addWirelessFormSubmittedData(WirelessFormData wirelessFormData);

	ResponseEntity<Response> viewWirelessFormSubmittedData(String formId);
	
	ResponseEntity<Response> deleteForm(List<String> formIds);
	
	ResponseEntity<Response> getAllTemplateList();
	
	ResponseEntity<Response> getFormMetadataByFormId(String formId);
	
	
	ResponseEntity<Response> addCustomGroup(@RequestBody CustomGroupRequest customGroupRequest)throws Exception;
	
	ResponseEntity<Response> getCustomGroup();

}
