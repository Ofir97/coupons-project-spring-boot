package com.ofir.coupons.custom_exception;

import com.ofir.coupons.enums.ErrorMessage;

public class CouponSystemException extends Exception {

	public CouponSystemException(ErrorMessage message) {
		super(message.getDescription());
	}
}
