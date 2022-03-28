package com.tnsss.restcallticket;


public class Parameter {

	private String productSku; 
	private String dealerCost;
	private String retailCost;
	private String formNumber;
	private String classCode;
	private String coverageOptions;
	private String multiType;
	
	
	public Parameter(String productSku, String dealerCost, String retailCost, String formNumber, String classCode,
			String coverageOptions, String multiType) {
		super();
		this.productSku = productSku;
		this.dealerCost = dealerCost;
		this.retailCost = retailCost;
		this.formNumber = formNumber;
		this.classCode = classCode;
		this.coverageOptions = coverageOptions;
		this.multiType = multiType;
	}

	public String getMultiType() {
		return multiType;
	}

	public void setMultiType(String multiType) {
		this.multiType = multiType;
	}

	public String getProductSku() {
		return productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}

	public String getDealerCost() {
		return dealerCost;
	}

	public void setDealerCost(String dealerCost) {
		this.dealerCost = dealerCost;
	}

	public String getRetailCost() {
		return retailCost;
	}

	public void setRetailCost(String retailCost) {
		this.retailCost = retailCost;
	}

	public String getFormNumber() {
		return formNumber;
	}

	public void setFormNumber(String formNumber) {
		this.formNumber = formNumber;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getCoverageOptions() {
		return coverageOptions;
	}

	public void setCoverageOptions(String coverageOptions) {
		this.coverageOptions = coverageOptions;
	}
	
	
	
	
	
}
