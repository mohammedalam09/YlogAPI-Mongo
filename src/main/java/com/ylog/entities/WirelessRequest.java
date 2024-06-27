package com.ylog.entities;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "WirelessFormTemplates")
public class WirelessRequest {

	@Transient
	public static final String SEQUENCE_NAME = "GET_FORM_ID";

	@Id
	private String _id;
	//private Long wirelessFormId;     //this is considering as PK here.
	private Long companyId; 
	@NotBlank
	@NotEmpty
	private String formName;
	private String description;
	private List<String> assignRoleIds;
	@Valid
	private List<WirelessFormMetaDataBean1> formMetadata;
	private Long arrpovalProcessId;
	private Long sequenceId;
	private Boolean analytics; // used to support fact and dimension based analytics
	private String parentMenuCode;
//	private String formTemplateHtml;
	private String publicUrlToken;
	private String actionType;
	private String formCode;
	private Boolean isPublicForm;
	@NotBlank
	@NotEmpty
	private String createdBy;
	private String createdOn;
	private String updatedBy;
	private String updatedOn;
	private String status;

}
