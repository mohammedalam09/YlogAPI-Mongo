package com.ylog.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ylog.data.Response;
import com.ylog.entities.WirelessFormData;
import com.ylog.entities.WirelessFormMetaDataBean1;
import com.ylog.entities.WirelessRequest;
import com.ylog.repo.WirelessFormTemplateRepo;

@Component
public class WirelessFormHelper {

	private static Logger logger = LoggerFactory.getLogger(WirelessFormHelper.class);

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	WirelessFormTemplateRepo wirelessFormTemplateRepo;

	public boolean checkIfFormDeleted(String formId) {
		return wirelessFormTemplateRepo.findAll().stream().noneMatch(f -> f.get_id().equalsIgnoreCase(formId));
	}

	public List<String> getFormMetadata(String formId) {
		Query query = new Query(Criteria.where("_id").is(formId));
		query.fields().include("formMetadata");

		// Need to improve
//		query.with(Sort.by(Sort.Direction.ASC, "submittedOn"));

		return mongoTemplate.find(query, WirelessRequest.class).get(0).getFormMetadata().stream()
				.map(WirelessFormMetaDataBean1::getLabel).toList();

	}

//	public List<WirelessFormData> sortWfDataBySubmittedOnDesc(List<WirelessFormData> wfDatas) {
//		return wfDatas.stream().sorted(Comparator.comparing(WirelessFormData::getCreatedOn).reversed())
//				.collect(Collectors.toList());
//	}

	public List<Map<String, Object>> getSubmittedDataList(String formId, List<String> ascCols, List<String> descCols,
			Integer pageNo, Integer pageSize) {

		Query query = new Query(Criteria.where("formId").is(formId));
	
		ascCols.stream().forEach(a -> {

			query.with(Sort.by(Sort.Direction.ASC, "formSubmittedData." + a));

		});

		descCols.stream().forEach(d -> {

			query.with(Sort.by(Sort.Direction.DESC, "formSubmittedData." + d));
		});

		if (ascCols.isEmpty() && descCols.isEmpty()) {
			// default search with submittedOn desc
			query.with(Sort.by(Sort.Direction.DESC, "createdOn"));
		}

		PageRequest page = PageRequest.of(pageNo, pageSize);

		query.with(page);

		query.fields().include("formSubmittedData");

		return mongoTemplate.find(query, WirelessFormData.class).stream().map(WirelessFormData::getFormSubmittedData)
				.toList();

	}

	public ResponseEntity<Response> validateWirelessFormRequest(WirelessRequest wirelessRequest) {
		logger.info("Started In validateWirelessFormRequest() ");

		List<Map<String, Object>> validationList = new ArrayList<Map<String, Object>>();
		Map<String, Object> validationMap = new LinkedHashMap<String, Object>();

		if (null == wirelessRequest) {
			return Response.buildResponse("Request is blank", HttpStatus.PRECONDITION_FAILED);
		}

		if (null == wirelessRequest.getFormName() || wirelessRequest.getFormName().isEmpty()) {
			return Response.buildResponse("Form Name should not be blank!!! ", HttpStatus.PRECONDITION_FAILED);
		}

		if (null == wirelessRequest.getCreatedBy() || wirelessRequest.getCreatedBy().isEmpty()) {
			return Response.buildResponse("Created by should not be blank!!! ", HttpStatus.PRECONDITION_FAILED);
		}

		if (wirelessFormTemplateRepo.isFormExistsWithName(wirelessRequest.getFormName())) {

			return Response.buildResponse("Form Name Exists Already!!!", HttpStatus.PRECONDITION_FAILED);
		}

		wirelessRequest.getFormMetadata().forEach(f -> {

			if (null == f.getSequence()) {
				validationMap.put("error", "sequence should not be null");
			}
			if (f.getSequence().longValue() == 0l) {
				validationMap.put("error", "sequence should be start from 1");
			}

			else if (null == f.getLabel() || f.getLabel().isEmpty()) {

				validationMap.put("error", "label should not be null or empty");
			} else if (null != f.getIsDefaultValue() && f.getIsDefaultValue()
					&& (null == f.getDefaultValue() || f.getDefaultValue().isEmpty())) {

				validationMap.put("error", "Default Value should be present " + f.getLabel());

			} else if ((null != f.getFieldContains() && null == f.getErrorMessage())
					|| (null != f.getErrorMessage() && null == f.getFieldContains())) {

				validationMap.put("error", "Field contains and error message both should be there");
			}

			else if (null == f.getFieldType() || f.getFieldType().isEmpty()
					|| !Constants.fieldsList.contains(f.getFieldType())) {

				validationMap.put("error", "fieldType is Either null or Invalid: " + f.getLabel());

			}

//			else if() {
//				
//			}

			validationList.add(validationMap);

		});

		logger.info("Exit from validateWirelessFormRequest() where validation map is " + validationMap);

		if (!validationMap.isEmpty() && !validationList.isEmpty()) {

			return Response.buildResponse(validationList, HttpStatus.PRECONDITION_FAILED);
		}

		return Response.buildResponse("Validation Success", HttpStatus.OK);
	}

	public List<Map<String, Object>> getAllFormNames() {
		Query query = new Query();
		query.fields().include("formName");
//		query.with(Sort.by(Sort.Direction.DESC, "createdOn"));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		mongoTemplate.find(query, WirelessRequest.class).stream().forEach(f -> {

			list.add(Map.of("formId", f.get_id(), "formName", f.getFormName()));

		});

		return list;
	}
}