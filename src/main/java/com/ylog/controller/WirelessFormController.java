package com.ylog.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ylog.data.Response;
import com.ylog.entities.CustomGroupRequest;
import com.ylog.entities.WirelessFormData;
import com.ylog.entities.WirelessRequest;
import com.ylog.service.WirelessFormService;

@RestController
@RequestMapping("/wirelessForm")
@CrossOrigin("*")
public class WirelessFormController {

	@Value("${app.version}")
	private String appVersion;

	private static Logger logger = LoggerFactory.getLogger(WirelessFormController.class);

	@Autowired
	WirelessFormService wfService;

	@PostMapping("/template/addForm")
	public ResponseEntity<Response> addWirelessFormTemplate(@RequestBody @Valid WirelessRequest wirelessRequest)
			throws Exception {
		logger.info("Start In addWirelessFormTemplate()");

		return wfService.addWirelessFormTemplate(wirelessRequest);

	}

	@GetMapping("/template/dataTable")
	public ResponseEntity<Response> viewAllWirelessFormTemplates() {
		logger.info("Start In viewAllWirelessFormTemplates()");
		return wfService.viewWirelessFormTemplates();

	}

	@GetMapping("/templateList")
	public ResponseEntity<Response> getAllTemplateLists() {

		return wfService.getAllTemplateList();

	}

	@GetMapping("/template/metadata")
	public ResponseEntity<Response> getFormMetadataByFormId(@RequestParam String formId) {
		return wfService.getFormMetadataByFormId(formId);
	}

	@PostMapping("/data/add")
	public ResponseEntity<Response> addWirelessFormData(@RequestBody WirelessFormData wirelessFormData) {

		return wfService.addWirelessFormSubmittedData(wirelessFormData);

	}

	@GetMapping("/data/dataTable")
	public ResponseEntity<Response> viewWirelessFormSubmittedData(@RequestParam String formId,
			@RequestParam(required = false, defaultValue = "") List<String> ascCols,
			@RequestParam(required = false, defaultValue = "createdOn") List<String> descCols,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {

		logger.info("Start In DataTable: " + new Date());

		return wfService.viewWirelessFormSubmittedData(formId, ascCols, descCols, pageNo, pageSize);

	}

	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteForm(@RequestParam List<String> formIds) {

		return wfService.deleteForm(formIds);
	}

	@GetMapping("/version")
	public ResponseEntity<Response> getversion() {

		return Response.buildResponse(appVersion, HttpStatus.OK);
	}

//	------------------------------Not using now

	@PostMapping("/groups/add")
	public ResponseEntity<Response> addCustomGroup(@RequestBody CustomGroupRequest customGroupRequest)
			throws Exception {

//		customGroupRequest.setGroupId(seqService.getNextSequence(CustomGroupRequest.SEQUENCE_NAME));

		return Response.buildResponse("Group Added Successfully", HttpStatus.OK);

	}

	@GetMapping("/groups/dataTable")
	public ResponseEntity<Response> getCustomGroup() throws Exception {

		return wfService.getCustomGroup();

	}

}
