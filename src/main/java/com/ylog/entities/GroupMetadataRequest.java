package com.ylog.entities;

import java.util.List;

import lombok.Data;

@Data
public class GroupMetadataRequest {

	private String label;
	private Long sequence;
	private Long fieldTypeId;
	private Boolean isVisibleLabel;
	private Long FieldMaxLength;
	private Boolean isRequired;
	private String calculationFormula;
	private Boolean isPreFillField;
	private Boolean isTotalField;
	private String followingField;
	private String defaultValue;
	private Boolean isDefaultValue;
	private String validation;
	private Boolean isEditable;
//	private MultiSelectRequest multiSelectRequest;
	private List<DropdownFilters> dropdownFilters;

	private String description;
	private Boolean isMultiSupportData;
	private Long fieldMinLength;
	private String fieldContains;
	private String errorMessage;
	private String containsValue;
	private Boolean isPreFillKeyField;
	private String preFillWith;
	private Long minDiffenence;
	private Long maxDiffenence;
	private String sourceCondition;
	private String dependentOn;
	private Boolean isLocationManager;
	private Boolean isGoogle;
	private Boolean isGeofence;
	private Boolean isDataTableVisible;
	private Long parentEntityId;
	private Long childEntityId;

	// fields used for terms and conditions
	private Boolean isUrl;
	private Boolean isFixedLongText;
	private Boolean isTermAcceptMandatory;
	private Boolean isTermCollapsible;
	private Boolean isTermSendEmail;
	private String longTextValue;

	// key to support QRCode/BarCode
	private Boolean isScannerAvailable;

	private Boolean isViewOnlyField; // if true field will be disabled on both add and update
	private Boolean totalInFooter; // if true show total of field in footer
	private Boolean isFact;
	private Boolean isDimension;
	private String showFieldOn;
	private Long maskStartFrom;
	private Long maskLength;
	private Boolean isMaskingEnable;
	private String letterCase;
	private String uiLabelKey;
	private String formulaKeys;
	private String placeholder;
	private Boolean isEnableOcrScanning;
	private Boolean isEnableQrcodeScanning;
	private Boolean isEnableBarcodeScanning;
	private Boolean isEnableReadOnly;
	private List<String> enableReadOnlyValues;
	private List<Long> readOnlyApprovalStepIds;
	private String fieldStyle;
	private String calculationFormat;
	private String calculationType;
	private String decimalScale;
	private Boolean isReportEnable;

}
