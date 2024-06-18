package com.ylog.externalservice.classes;

import io.swagger.annotations.ApiModelProperty;

public class Between {
	private String from;
	private String to;

	@ApiModelProperty(value = "From field value" , required = true)
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@ApiModelProperty(value = "To field value" , required = true)
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
