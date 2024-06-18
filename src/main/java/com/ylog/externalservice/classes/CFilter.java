package com.ylog.externalservice.classes;

import java.util.HashMap;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class CFilter {
	private Boolean flag = false;
	private List<HashMap<String, String>> data;

	@ApiModelProperty(value = "Custom filter will apply if flag is true, Default is true" , required = false)
	public Boolean isFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public List<HashMap<String, String>> getData() {
		return data;
	}

	public void setData(List<HashMap<String, String>> data) {
		this.data = data;
	}
}
