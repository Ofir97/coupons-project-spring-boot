package com.ofir.coupons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ofir.coupons.beans.Customer;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Integer>{
	Customer findById(int customerId);
	boolean existsByEmail(String email);
	Customer findByEmailAndPassword(String email, String password);
	
	@Query(value = "select if( exists(select * from customers_vs_coupons "
			+ "where customer_id=:customerId and coupon_id=:couponId), true, false) ", nativeQuery = true)
	int isCouponPurchaseExists(@Param("customerId") int customerId, @Param("couponId") int couponId); //returns 1 for true, 0 for false
	
	
}
