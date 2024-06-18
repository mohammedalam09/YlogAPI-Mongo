package com.ylog.entities;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "ADDRESS_V")
public class Address {

	@Id
	private String wirelessFormId;

	private String wirelessFormName;

	private String companyId;

	private String submittedOn;

	private HashMap<String, String> formSubmittedData;

	private List<HashMap<String, String>> groupData;

	public String getWirelessFormId() {
		return wirelessFormId;
	}

	public void setWirelessFormId(String wirelessFormId) {
		this.wirelessFormId = wirelessFormId;
	}

	public String getWirelessFormName() {
		return wirelessFormName;
	}

	public void setWirelessFormName(String wirelessFormName) {
		this.wirelessFormName = wirelessFormName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSubmittedOn() {
		return submittedOn;
	}

	public void setSubmittedOn(String submittedOn) {
		this.submittedOn = submittedOn;
	}

	public HashMap<String, String> getFormSubmittedData() {
		return formSubmittedData;
	}

	public void setFormSubmittedData(HashMap<String, String> formSubmittedData) {
		this.formSubmittedData = formSubmittedData;
	}

	public List<HashMap<String, String>> getGroupData() {
		return groupData;
	}

	public void setGroupData(List<HashMap<String, String>> groupData) {
		this.groupData = groupData;
	}

}
