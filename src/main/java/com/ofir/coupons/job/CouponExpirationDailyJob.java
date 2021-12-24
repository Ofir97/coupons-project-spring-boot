package com.ofir.coupons.job;

import java.sql.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ofir.coupons.repositories.CouponsRepository;
import com.ofir.coupons.utils.Utils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CouponExpirationDailyJob {

	private final CouponsRepository couponsRepository;
	private static final long TWENTY_FOUR_HOURS = 1000 * 60 * 60 * 24;
	
	@Scheduled(fixedDelay = TWENTY_FOUR_HOURS)
	public void removeExpiredCoupons() {
		couponsRepository.deleteByEndDateBefore(new Date(Utils.getCurrentTime().getTime()));
	}
	
}
