package com.ylog.externalservice.classes;

import java.util.HashMap;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class InFilter {

	private Boolean flag = false;
	private List<HashMap<String, ListData>> data;
	
	@ApiModelProperty(value = "IN filter will apply if flag is true, Default is true" , required = false)
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public List<HashMap<String, ListData>> getData() {
		return data;
	}
	public void setData(List<HashMap<String, ListData>> data) {
		this.data = data;
	}
}
