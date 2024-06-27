package com.ylog.externalservice.classes;

import java.util.List;

import lombok.Data;

@Data
public class SearchSortCriteria {

	public SearchSortCriteria() {
		pageNo = 1;
		pageSize = 10;
	}

	private int pageNo;
	private int pageSize;
	private List<String> ascColumns;
	private List<String> descColumns;

}
