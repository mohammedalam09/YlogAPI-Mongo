package com.ylog.service;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.ylog.utility.Constants;
import com.ylog.utility.DateTimeUtility;
import com.ylog.utility.RedisService;
import com.ylog.utility.WirelessFormHelper;

import io.lettuce.core.RedisConnectionException;

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

	@Autowired
	private RedisService redisService;

	@Value("${base.uri}")
	private String baseUrl;

	int retry = 0;

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
			logger.info(
					"After getting dropdown list in viewWirelessFormTemplates() Where formName: " + form.getFormName());
			form.getFormMetadata().stream().forEach(metadata -> {

				dropdownData.stream().forEach(data -> {
					if (data.getUiLabelKey().equalsIgnoreCase(metadata.getUiLabelKey())) {

						if (null != data.getEntity()) {
							data.setUrl(baseUrl + data.getUrl());
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
		if (null == wirelessFormData.getFormId() || wirelessFormData.getFormId().isEmpty())
			return Response.buildResponse("FormId not found", HttpStatus.PRECONDITION_FAILED);
		if (wfHelper.checkIfFormDeleted(wirelessFormData.getFormId()))
			return Response.buildResponse("Form does not exists!!!", HttpStatus.PRECONDITION_FAILED);
		wirelessFormData.setCreatedOn(DateTimeUtility.getGmtDateTime());
		wirelessFormDataRepo.save(wirelessFormData);
		return Response.buildResponse("Data Added Successfully", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> viewWirelessFormSubmittedData(String formId, List<String> ascCols,
			List<String> descCols, Integer pageNo, Integer pageSize) {
		logger.info("Started In viewWirelessFormSubmittedData Service");
		List<Map<String, Object>> list = new LinkedList<>();

		if (wfHelper.checkIfFormDeleted(formId)) {
			return Response.buildResponse("Form does not exists!!!", HttpStatus.PRECONDITION_FAILED);
		}

		if (ascCols.contains("createdOn") || descCols.contains("createdOn")) {
			return Response.buildResponse("No such column Exists", HttpStatus.PRECONDITION_FAILED);
		}

		List<Map<String, Object>> allFormData = wfHelper.getSubmittedDataList(formId, ascCols, descCols, pageNo,
				pageSize);

		List<String> labelNamesInSequence = wfHelper.getFormMetadata(formId);

		allFormData.stream().forEach(data -> {
			Map<String, Object> map = new LinkedHashMap<>();
			labelNamesInSequence.stream().forEach(label -> {
				map.put(label, data.get(label));
			});

			list.add(map);
		});
		logger.info("Exit from viewWirelessFormSubmittedData Service");
		if (list.isEmpty()) {
			return Response.buildResponse(Arrays.asList(), HttpStatus.NO_CONTENT);
		}
		return Response.buildResponse(list, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> deleteForm(List<String> formIds) {
		formIds.stream().forEach(id -> {
			multiSelectRequestRepo.deleteAllById(
					multiSelectRequestRepo.findByFormId(id).stream().map(m -> m.get_id()).collect(Collectors.toList()));
			wirelessFormTemplateRepo.deleteById(id);
		});
		return Response.buildResponse("Deleted Successfully", HttpStatus.OK);
	}

	@Override
	@Retryable(value = RedisConnectionFailureException.class, maxAttempts = 3, recover = "getAllFormsFromDB", backoff = @Backoff(3000))
	public ResponseEntity<Response> getAllTemplateList() throws Exception {

		logger.info("Started In getAllTemplateList() Service ");

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> cachedFormList = redisService.get(Constants.KEY_FORM_NAMES, List.class);

		if (null != cachedFormList) {
			logger.info("Started In getAllTemplateList() Service Data From cache");
			return Response.buildResponse(cachedFormList, HttpStatus.OK);
		}

		logger.info("Started In getAllTemplateList() Service DB call");
		List<Map<String, Object>> allFormNames = wfHelper.getAllFormNames();

		if (!allFormNames.isEmpty()) {

			try {
				redisService.set(Constants.KEY_FORM_NAMES, new ObjectMapper().writeValueAsString(allFormNames), 5l);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		}

		return Response.buildResponse(allFormNames, HttpStatus.OK);
	}

	@Recover
	public ResponseEntity<Response> getAllFormsFromDB(RedisConnectionFailureException e) {
		logger.info(
				"Started In getFormMetadataByFormId() Service Recovery Method calling for exception" + e.getMessage());
		return Response.buildResponse(wfHelper.getAllFormNames(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Response> getFormMetadataByFormId(String formId) throws Exception {
		logger.info("Started In getFormMetadataByFormId() Service ");

		WirelessRequest cachedFormData = redisService.get(formId, WirelessRequest.class);

		if (null != cachedFormData) {
			logger.info("Started In getFormMetadataByFormId() Service Data From cache");
			return Response.buildResponse(cachedFormData, HttpStatus.OK);
		}

		logger.info("Started In getFormMetadataByFormId() Service DB call");
		WirelessRequest dataFromDB = wirelessFormTemplateRepo.getFormMetadata(formId);

		if (null != dataFromDB) {

			try {
				redisService.set(formId, new ObjectMapper().writeValueAsString(dataFromDB), 15l);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		return Response.buildResponse(dataFromDB, HttpStatus.OK);
	}

//	------------------------------Not using now

	public ResponseEntity<Response> addCustomGroup(CustomGroupRequest customGroupRequest) throws Exception {
		customGroupRepo.save(customGroupRequest);
		return Response.buildResponse("Group Added Successfully", HttpStatus.OK);
	}

	public ResponseEntity<Response> getCustomGroup() {
		return Response.buildResponse(customGroupRepo.findAll(), HttpStatus.OK);
	}

}
