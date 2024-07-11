package com.ylog.entities;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "MultiSelectRequest")
//public class DropDownRequest {
public class MultiSelectRequest {

	@Id
	private String _id; // PK
	private String formId;  //will insert from backend
	private String uiLabelKey; // unique for every field added while saving the form
	private String entity;
	private Map<String, Object> filters;
	private boolean isParent;
	private boolean isChild;
	private String dependentOn;
	private String url;
	private List<String> values;

}
