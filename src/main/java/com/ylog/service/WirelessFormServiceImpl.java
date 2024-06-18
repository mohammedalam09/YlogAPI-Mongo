package com.ylog.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ylog.data.Response;
import com.ylog.entities.CustomGroupRequest;
import com.ylog.entities.MultiSelectRequest;
import com.ylog.entities.WirelessFormData;
import com.ylog.entities.WirelessRequest;
import com.ylog.repo.AddresssRepo;
import com.ylog.repo.CustomGroupRepo;
import com.ylog.repo.MultiSelectRequestRepo;
import com.ylog.repo.WirelessFormDataRepo;
import com.ylog.repo.WirelessFormTemplateRepo;
import com.ylog.service.sequence.SequenceService;
import com.ylog.utility.DateTimeUtility;
import com.ylog.utility.WirelessFormHelper;

@Service
public class WirelessFormServiceImpl implements WirelessFormService {
	private static Logger logger = LoggerFactory.getLogger(WirelessFormServiceImpl.class);

	@Autowired
	AddresssRepo addressRepo;

	@Autowired
	CustomGroupRepo customGroupRepo;

	@Autowired
	MultiSelectRequestRepo multiSelectRequestRepo;

	@Autowired
	SequenceService seqService;

	@Autowired
	WirelessFormDataRepo wirelessFormDataRepo;

	@Autowired
	WirelessFormHelper wfHelper;

	@Autowired
	WirelessFormTemplateRepo wirelessFormTemplateRepo;

	@Value("${base.uri}")
	private String baseUrl;

	public ResponseEntity<Response> addWirelessFormTemplate(WirelessRequest wirelessRequest) throws Exception {
		logger.info("Start In addWirelessFormTemplate() Service");
		ResponseEntity<Response> validate = wfHelper.validateWirelessFormRequest(wirelessRequest);
		if (validate.getStatusCodeValue() != 200)
			return validate;
		wirelessRequest.setCreatedOn(DateTimeUtility.getGmtDateTime());
		wirelessRequest.setStatus("A");
		logger.info("Before Persisting In Db ");
		WirelessRequest wirelessForm = wirelessFormTemplateRepo.save(wirelessRequest);
		logger.info("After Persisting In Db ");
		wirelessRequest.getFormMetadata().forEach(m -> {
			if (null != m.getMultiSelectRequest()) {
				logger.info("In MultiSelect for field: " + m.getLabel());
				m.getMultiSelectRequest().setFormId(wirelessForm.get_id());
				m.getMultiSelectRequest().setUiLabelKey(m.getUiLabelKey());
				if (null != m.getMultiSelectRequest().getEntity()) {
					switch (m.getMultiSelectRequest().getEntity()) {
					case "USER":
						m.getMultiSelectRequest().setUrl("user/dataTable");
						break;
					case "VEHICLE":
						m.getMultiSelectRequest().setUrl("vehicle/dataTable");
						break;
					default:
						return;
					}
				}
				multiSelectRequestRepo.save(m.getMultiSelectRequest());
				logger.info("After Persisting DropdownRequest for field: " + m.getLabel());
			}
		});
		logger.info("Exit from addWirelessFormTemplate() Service ");
		return Response.buildResponse("Template Added Successfully", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> viewWirelessFormTemplates() {
		logger.info("Started In viewWirelessFormTemplates() Service ");
		List<WirelessRequest> forms = wirelessFormTemplateRepo.findAll();
		logger.info("After fetching all records In viewWirelessFormTemplates() Service Where all Forms Count is: "
				+ forms.size());

		forms.stream().forEach(form -> {

//			if (form.getFormName().equalsIgnoreCase("Drop Down") ){

				logger.info("Started In viewWirelessFormTemplates() Where formName: " + form.getFormName());
				List<MultiSelectRequest> dropdownData = multiSelectRequestRepo.findByFormId(form.get_id());
				logger.info("After getting dropdown list in viewWirelessFormTemplates() Where formName: "
						+ form.getFormName());
				form.getFormMetadata().stream().forEach(metadata -> {

					dropdownData.stream().forEach(data -> {
						if (data.getUiLabelKey().equalsIgnoreCase(metadata.getUiLabelKey())) {
							
							if (null != data.getEntity()) {
								data.setUrl(baseUrl+data.getUrl());
							}
							metadata.setMultiSelectRequest(data);

						}
					});

				});
//			}

		});
		logger.info("Exit from viewWirelessFormTemplates() Service");
		return Response.buildResponse(forms, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> addWirelessFormSubmittedData(WirelessFormData wirelessFormData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Response> viewWirelessFormSubmittedData(String formId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Response> deleteForm(List<String> formIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Response> getAllTemplateList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Response> getFormMetadataByFormId(String formId) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseEntity<Response> addCustomGroup(CustomGroupRequest customGroupRequest) throws Exception {
		customGroupRepo.save(customGroupRequest);
		return Response.buildResponse("Group Added Successfully", HttpStatus.OK);
	}

	public ResponseEntity<Response> getCustomGroup() {
		return Response.buildResponse(customGroupRepo.findAll(), HttpStatus.OK);
	}

}
