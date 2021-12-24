package com.ofir.coupons.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ofir.coupons.annotations.LogOperation;
import com.ofir.coupons.beans.Coupon;
import com.ofir.coupons.beans.Customer;
import com.ofir.coupons.custom_exception.CouponSystemException;
import com.ofir.coupons.enums.Category;
import com.ofir.coupons.enums.ErrorMessage;
import com.ofir.coupons.repositories.CompaniesRepository;
import com.ofir.coupons.repositories.CouponsRepository;
import com.ofir.coupons.repositories.CustomersRepository;
import com.ofir.coupons.utils.Utils;

@Service
@Scope("prototype")
public class CustomerService extends ClientService {

	private int customerID; // represents the customer id that logged in

	@Autowired
	public CustomerService(CompaniesRepository companiesRepository, CustomersRepository customersRepository,
			CouponsRepository couponsRepository) {
		super(companiesRepository, customersRepository, couponsRepository);
	}

	/**
     * @param	email - the customer's email
     * @param	password - the customer's password
     * @return 	true if the email and password exist in customers table, otherwise false
     */
	@Override
	public boolean login(String email, String password) {
		Customer customer = customersRepository.findByEmailAndPassword(email, password); 
		if (customer != null) { // if customer exists
            customerID = customer.getId(); //sets customer id to the customer id that logged in.
            return true;
        } 
        return false;
	}
	
	/**
	 * this method makes a coupon purchase to the customer that logged in,
	 * responsible to check whether the customer can make that purchase
	 * and if so, it will decrease the coupon amount by 1 and make the purchase.
	 *  
	 * @param 	couponId represents the id of the coupon to be purchased by the customer
	 * @throws  IOException 
	 * @throws  CouponSystemException for one of the following reasons:
	 * 					- coupon id does not exist in DB
	 * 					- customer already purchased the coupon
	 * 					- coupon is sold out (amount=0)
	 * 					- coupon is expired (end date is before the current time)
	 */
	@LogOperation(msg = "coupon purchased successfully")
	public void purchaseCoupon(int couponId) throws IOException, CouponSystemException {
		Coupon coupon = couponsRepository.findById(couponId);
		
		// if coupon id does not exist:
		if (coupon == null) 
			throw new CouponSystemException(ErrorMessage.COUPON_NOT_FOUND);

		// if coupon purchase already exists for customer:
		if (customersRepository.isCouponPurchaseExists(customerID, couponId) == 1) 
			throw new CouponSystemException(ErrorMessage.COUPON_ALREADY_PURCHASED);

		// if coupon is out of stock
		if (coupon.getAmount() == 0) 
			throw new CouponSystemException(ErrorMessage.COUPON_SOLD_OUT);
		
		// if coupon is expired
		if (coupon.getEndDate().before(Utils.getCurrentTime())) 
			throw new CouponSystemException(ErrorMessage.COUPON_EXPIRED);

		// coupon can be purchased by the customer -
		couponsRepository.addCouponPurchase(customerID, couponId);
		couponsRepository.decreaseCouponAmountByOne(couponId);
	}
	
	/**
	 * @return	list of all coupons purchased by the customer that logged in
	 */
	public List<Coupon> getCustomerCoupons()  {
		List<Coupon> coupons = couponsRepository.getCustomerCoupons(customerID);
		return coupons;
	}
	
	/**
	 * @param 	category indicates that only coupons from the specific category will be included in the list
	 * @return	list of all coupons that belong to the customer that logged in from the specified category
	 */
	public List<Coupon> getCustomerCoupons(Category category) {
		List<Coupon> coupons = couponsRepository.getCustomerCouponsByCategoryId(customerID, category.getId());
		return coupons;
	}
	
	/**
	 * @param 	maxPrice indicates that only coupons until the specified maxPrice will be included in the list
	 * @return	list of all coupons of the customer that logged in until maxPrice
	 */
	public List<Coupon> getCustomerCoupons(double maxPrice) {
		List<Coupon> coupons = couponsRepository.getCustomerCouponsUntilMaxPrice(customerID, maxPrice);
		return coupons;
	}
	
	/**
	 * @return 	customer object with all details of the customer that logged in
	 */
	public Customer getCustomerDetails() {
		Customer customer = customersRepository.findById(customerID);
		return customer;
	}

}
