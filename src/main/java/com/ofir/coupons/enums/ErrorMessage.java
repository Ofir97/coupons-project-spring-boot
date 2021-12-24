package com.ofir.coupons.enums;

public enum ErrorMessage {

	COMPANY_NAME_OR_EMAIL_EXISTS("ERROR: company name or email already exists."),
	COMPANY_NAME_UPDATED("ERROR: company name cannot be updated."),
	COMPANY_NOT_FOUND("ERROR: company does not exist."),
	
	CUSTOMER_EMAIL_EXISTS("ERROR: customer email already exists."),
	CUSTOMER_NOT_FOUND("ERROR: customer does not exist."),
	
	COUPON_TITLE_EXISTS("ERROR: coupon title already exists in company's coupons."),
	COUPON_NOT_FOUND("ERROR: coupon does not exist."),
	COUPON_ALREADY_PURCHASED("ERROR: coupon has already been purchased."),
	COUPON_SOLD_OUT("ERROR: coupon is sold out."),
	COUPON_EXPIRED("ERROR: coupon has expired."),
	
	BAD_LOGIN("LOGIN FAILED: bad credentials");
	
	private String description;
	
	ErrorMessage(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
