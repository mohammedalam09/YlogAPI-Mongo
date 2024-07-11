package com.ylog.controller;

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
	
	private static final Logger logger = LoggerFactory.getLogger(WirelessFormController.class);

	@Autowired
	private WirelessFormService wfService;

	@PostMapping("/template/addForm")
	public ResponseEntity<Response> addWirelessFormTemplate(@RequestBody @Valid WirelessRequest wirelessRequest)
			throws Exception {
		logger.info("Started In addWirelessFormTemplate() Controller Where WirelessFormAddRequest is: " + wirelessRequest);

		return wfService.addWirelessFormTemplate(wirelessRequest);

	}

	@GetMapping("/template/dataTable")
	public ResponseEntity<Response> viewAllWirelessFormTemplates() {
		logger.info("Started In viewAllWirelessFormTemplates() Controller");
		return wfService.viewWirelessFormTemplates();

	}

	@GetMapping("/templateList")
	public ResponseEntity<Response> getAllTemplateLists() {
		logger.info("Started In getAllTemplateLists() Controller");

		try {
			return wfService.getAllTemplateList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.buildResponse("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@GetMapping("/template/metadata")
	public ResponseEntity<Response> getFormMetadataByFormId(@RequestParam String formId) {
		logger.info("Started In getFormMetadataByFormId() Controller Where FormId is: " + formId);
		try {
			return wfService.getFormMetadataByFormId(formId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.buildResponse("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/data/add")
	public ResponseEntity<Response> addWirelessFormData(@RequestBody WirelessFormData wirelessFormData) {
		logger.info(
				"Started In addWirelessFormData() Controller Where wirelessFormSubmittedData is: " + wirelessFormData);

		return wfService.addWirelessFormSubmittedData(wirelessFormData);

	}

	@GetMapping("/data/dataTable")
	public ResponseEntity<Response> viewWirelessFormSubmittedData(@RequestParam String formId,
			@RequestParam(required = false, defaultValue = "") List<String> ascCols,
			@RequestParam(required = false, defaultValue = "") List<String> descCols,
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = Integer.MAX_VALUE + "") Integer pageSize) {

		logger.info("Started In viewWirelessFormSubmittedData() Controller Where FormId is: " + formId);

		try {
			return wfService.viewWirelessFormSubmittedData(formId, ascCols, descCols, pageNo, pageSize);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.buildResponse("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteForm(@RequestParam List<String> formIds) {

		return wfService.deleteForm(formIds);
	}

	@GetMapping("/version")
	public ResponseEntity<Response> getversion() {
		logger.info("Started In getVersion()");
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
