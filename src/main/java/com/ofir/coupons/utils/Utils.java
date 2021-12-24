package com.ofir.coupons.utils;

import com.ofir.coupons.beans.Coupon;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {

	/**
	 * @param 	myEnum is the enum to be converted to String
	 * @return 	String representation of enum parameter, with first letter capitalized
	 */
	public static String convertEnumToString(Enum<?> myEnum) {
		String str = myEnum.toString().toLowerCase();
		String firstLetterCapitalizedStr = str.substring(0, 1).toUpperCase() + str.substring(1);
		return firstLetterCapitalizedStr;
	}

	/**
	 * @param 	coupons is the coupon list
	 * @return 	String representation of coupons list
	 */
	public static String getCouponsAsStr(List<Coupon> coupons) {
		if (coupons == null || coupons.size() == 0)
			return "---\n";

		String str = "\n";
		for (Coupon coupon : coupons) {
			str += coupon + "\n";
		}
		return str;
	}

	public static Date getCurrentTime() {
		return Calendar.getInstance().getTime();
	}

}
