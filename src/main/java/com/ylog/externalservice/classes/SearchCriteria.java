package com.ylog.externalservice.classes;

import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.swagger.annotations.ApiModelProperty;



@JsonTypeName("searchCriteria")
public class SearchCriteria {
	
	
	
	private int sEcho;
	private int iDisplayStart;
	private int iDisplayLength;
	private int iSortCol_0;
	private String sSortDir_0;
	private LinkedList<String> columnNames;
	private LinkedList<String> searchColumnNamesWithText;
	private CFilter cFilter;
	private BetweenFilter betweenFilter;
	private InFilter inFilter;
	
	public InFilter getInFilter() {
		return inFilter;
	}

	public void setInFilter(InFilter inFilter) {
		this.inFilter = inFilter;
	}

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	@ApiModelProperty(value = "Start record from ", dataType="Integer" , required = true)
	public int getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	@ApiModelProperty(value = "Show number of records from started record", dataType="Integer" , required = true)
	public int getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	@ApiModelProperty(value = "Index of sorting column", dataType="Integer" , required = true)
	public int getiSortCol_0() {
		return iSortCol_0;
	}

	public void setiSortCol_0(int iSortCol_0) {
		this.iSortCol_0 = iSortCol_0;
	}

	@ApiModelProperty(value = "Sort by asc or desc)" , required = true)
	public String getsSortDir_0() {
		return sSortDir_0;
	}

	public void setsSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
	}

	@ApiModelProperty(value = "Column names separated by commas", dataType="List"  , required = true)
	public LinkedList<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(LinkedList<String> columnNames) {
		this.columnNames = columnNames;
	}

	@ApiModelProperty(value = "Search text in given column names", dataType="List"  , required = true)
	public LinkedList<String> getSearchColumnNamesWithText() {
		return searchColumnNamesWithText;
	}

	public void setSearchColumnNamesWithText(LinkedList<String> searchColumnNamesWithText) {
		this.searchColumnNamesWithText = searchColumnNamesWithText;
	}
	
	@ApiModelProperty(value = "Add custom filters", dataType="List"  , required = false)
	public CFilter getcFilter() {
		return cFilter;
	}

	public void setcFilter(CFilter cFilter) {
		this.cFilter = cFilter;
	}

	@ApiModelProperty(value = "Add between clause filters", dataType="List"  , required = false)
	public BetweenFilter getBetweenFilter() {
		return betweenFilter;
	}

	public void setBetweenFilter(BetweenFilter betweenFilter) {
		this.betweenFilter = betweenFilter;
	}
	
}
