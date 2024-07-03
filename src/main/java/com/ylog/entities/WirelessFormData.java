package com.ylog.entities;

import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Data
@Document(collection = "WirelessFormSubmittedData")
public class WirelessFormData {

	@Transient
	public static final String SEQUENCE_NAME = "GET_SUBMITTED_DATA_ID";

	@Id
	private String _id;
	@NotNull
	@NotEmpty
	private String formId;
	@NotNull
	private Long companyId;
	private Map<String,Object> formSubmittedData;
	@NotNull
	@NotEmpty
	private String createdBy;
	private String createdOn;
	private String updatedBy;
	private String updatedOn;
	private String status;

}
