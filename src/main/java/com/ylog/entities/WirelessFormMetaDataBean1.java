package com.ylog.entities;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import lombok.Data;

@Data
public class WirelessFormMetaDataBean1 {
	
	//This key is used for maintaining the sequence of the field on form
	@NotNull(message = "sequence can not be null")
	private Long sequence;
	//This key is used for field label
	private String label;
	//This key is used for field placeholder
	private String placeholder;
	//This key is used for field description
	private String description;
	//This key is used for making this field mandatory on form submission
	private Boolean isRequired;
	//This key is used for making this field hidden on form
	private Boolean isVisibleLabel;
	//This key is used for denotation that if any default value is assigned in this field or not
	private Boolean isDefaultValue;
	//This key is used for storing defaultValue
	private String defaultValue;
	private Boolean isEnableOtpVerification;
	//This key is used for enabling read only field
	private Boolean isEnableReadOnly; 
	//This key is used for read only when(Add/Edit)
	private List<String> enableReadOnlyValues; 
	//This key is used for enabling validation on field
	private String validation;
	//This key is used for what validations are allowed to enter in this field
	private String fieldContains;
	//This key is used for custom error message here
	private String errorMessage;  
	//This key is used for dependable fields
	private String dependentOn;
	//This key is used for parentEntity
	private Long parentEntityId;
	//This key is used for parentEntity
	private Long childEntityId;
	//This key is used for this field to show on Wirelessform Report
	private Boolean isReportEnable;
	//Below 3 keys are to support QRCode/BarCode for this field
	private Boolean isEnableOcrScanning;
	private Boolean isEnableQrcodeScanning;
	private Boolean isEnableBarcodeScanning;
	//This key is used for showing this fieldOn Add/Edit.
	private String showFieldOn;
	//This key is used for what case type is supported in this field.
	private String letterCase;
	
	//This key is used when someone chooses for GPS as location service.
	private Boolean isLocationManager;
	//This key is used when someone chooses for Google as location service.
	private Boolean isGoogle;
	//This key is used when someone chooses for Geofence as location service.
	private Boolean isGeofence;
	
	//These fields are used for terms and conditions
	private Boolean isUrl;
	private Boolean isFixedLongText;
	private Boolean isTermAcceptMandatory;
	private Boolean isTermCollapsible;
	private Boolean isTermSendEmail;
	
	private String longTextValue;
	//This key is used for showing how many decimal places are allowed after decimal point.
	private String decimalScale;
	//This key is used to represent the field editability
	private Boolean isEditable;
	//Below two keys are used for AutoFill Functionality
	// AutoFill Functionality :-Let's Say you have submitted data on any of the field on form previously and this field has AutoFieldKey enabled then when you again try the same value all   	AutoFieldValue will be fetched with previously submitted data and set automatically. 
	private Boolean isPreFillKeyField;
	private Boolean isPreFillField;
	//This key represents minimum length that can be inserted into the field
	private Long fieldMinLength;
	//This key represents maximum length that can be inserted into the field
	private Long FieldMaxLength;
	
	private Boolean isMaskingEnable;

	private Boolean isScannerAvailable;
	private Boolean isFact;
	private Boolean isDimension;
	//This key represents that this field will be visible on dataTable or not	
	private Boolean isDataTableVisible;
	
	private Boolean isTotalField;
	
	private Long maskStartFrom;
	private Long maskLength;
	private Long minDiffenence;
	private Long maxDiffenence;
	
	private String preFillWith;
	private String sourceCondition;
	private String containsValue;
	private String followingField;
	private String formulaKeys;
	private String calculationFormula;
	private String calculationFormat;
	private String calculationType;
	private Boolean isMultiSupportData;
	private List<Long> readOnlyApprovalStepIds;
	private String fieldStyle;
	private Boolean isConditionBound;
	private String conditionBoundWith;
	private Boolean isHiddenApprovalSteps;
	private List<String> conditionBoundValues;
	private List<Long> hiddenApprovalStepIds;
	@Transient
	private MultiSelectRequest multiSelectRequest;
	private MultiSelectRequest dropDownRequest;
//	private List<DropdownFilters> dropdownFilters;
// if field is group type add its metadata
//	private Boolean isAddable;
//	private Boolean isViewConsolidated;
//	private Long groupId;
//	private List<GroupMetadataRequest> groupMetadataList;
	private Boolean isViewOnlyField; // if true field will be disabled on both add and update
	private Boolean totalInFooter; // if true show total of field in footer
	private String uiLabelKey;
	private Boolean isTabularView;
	private String fieldType;
	@Transient
	private List<Object> values;
	


}
