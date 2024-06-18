package com.ylog.entities;

import java.util.List;

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
	private String formName;
	private String description;
	private List<String> assignRoleIds;
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
	private String createdBy;
	private String createdOn;
	private String updatedBy;
	private String updatedOn;
	private String status;

}
