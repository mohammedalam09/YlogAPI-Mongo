package com.ylog.externalservice.classes;

import java.util.HashMap;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class BetweenFilter {
	private Boolean flag = false;
	private List<HashMap<String, Between>> data;
	private Boolean isDate = false;
	private Boolean isOrCondition = false;
	

	
	
	@ApiModelProperty(value = "Custom between filter will apply if flag is true, Default is true" , required = false)
	public Boolean isFlag() {
		return flag;
	}
	
	
	@ApiModelProperty(value = "If isDate is true then will apply for date format only otherwise date and time, Default is true" , required = false)
	public Boolean isDate() {
		return isDate;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public List<HashMap<String, Between>> getData() {
		return data;
	}

	
	public void setData(List<HashMap<String, Between>> data) {
		this.data = data;
	}



	
	public void setIsDate(Boolean isDate) {
		this.isDate = isDate;
	}


	public Boolean isOrCondition() {
		return isOrCondition;
	}


	public void setIsOrCondition(Boolean isOrCondition) {
		this.isOrCondition = isOrCondition;
	}

}
