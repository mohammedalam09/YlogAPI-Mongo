package com.ylog.entities;

import java.util.List;

import lombok.Data;

@Data
public class DropdownFilters {

	private String categoryFilterCode;
	private List<Long> filterValueIds;
	private List<String> filterValueCodes;

}
