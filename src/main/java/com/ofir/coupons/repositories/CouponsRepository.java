package com.ofir.coupons.repositories;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ofir.coupons.beans.Coupon;
import com.ofir.coupons.enums.Category;

@Repository
public interface CouponsRepository extends JpaRepository<Coupon, Integer> {
	Coupon findById(int couponId);

	List<Coupon> findByCompanyId(int companyId);

	List<Coupon> findByCompanyIdAndCategory(int companyID, Category category);

	List<Coupon> findByCompanyIdAndPriceLessThanEqual(int companyID, double maxPrice);
	
	boolean existsByCompanyIdAndTitle(int companyId, String title);
	
	boolean existsByIdAndCompanyId(int couponId, int companyId);

	@Query(value = "select coupons.* from customers_vs_coupons " + "join coupons "
			+ "on customers_vs_coupons.coupon_id=coupons.id " + "where customer_id=:id", nativeQuery = true)
	List<Coupon> getCustomerCoupons(@Param("id") int customerId);

	@Query(value = "select coupons.* from customers_vs_coupons " + "join coupons "
			+ "on customers_vs_coupons.coupon_id=coupons.id "
			+ "where customer_id=:customerId and category_id=:categoryId", nativeQuery = true)
	List<Coupon> getCustomerCouponsByCategoryId(@Param("customerId") int customerId,
			@Param("categoryId") int categoryId);

	@Query(value = "select coupons.* from customers_vs_coupons " + "join coupons "
			+ "on customers_vs_coupons.coupon_id=coupons.id "
			+ "where customer_id=:customerId and price<=:maxPrice", nativeQuery = true)
	List<Coupon> getCustomerCouponsUntilMaxPrice(@Param("customerId") int customerId,
			@Param("maxPrice") double maxPrice);

	@Modifying
	@Transactional
	@Query(value = "insert into customers_vs_coupons (customer_id, coupon_id) values(:customerId, :couponId)", nativeQuery = true)
	void addCouponPurchase(@Param("customerId") int customerId, @Param("couponId") int couponId);

	@Modifying
	@Transactional
	@Query(value = "update coupons set amount=amount-1 where id=:couponId", nativeQuery = true)
	void decreaseCouponAmountByOne(@Param("couponId") int couponId);

	@Modifying
	@Transactional
	void deleteByEndDateBefore(Date currentTime);

}
