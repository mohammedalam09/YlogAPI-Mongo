package com.ylog.entities;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "CustomGroups")
public class CustomGroupRequest {
	
	@Transient
	public static final String SEQUENCE_NAME = "GET_GROUP_ID";

	@Id
	private Long groupId;
	private String groupName;
	private String description;
	private Boolean isAddable;
	private Boolean isViewConsolidated;
	private String groupHtml;
	private String groupStyle;
	private List<GroupMetadataRequest> groupMetadataList;

}
