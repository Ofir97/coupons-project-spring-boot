package com.ofir.coupons.services;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ofir.coupons.annotations.LogOperation;
import com.ofir.coupons.beans.Company;
import com.ofir.coupons.beans.Coupon;
import com.ofir.coupons.custom_exception.CouponSystemException;
import com.ofir.coupons.enums.Category;
import com.ofir.coupons.enums.ErrorMessage;
import com.ofir.coupons.repositories.CompaniesRepository;
import com.ofir.coupons.repositories.CouponsRepository;
import com.ofir.coupons.repositories.CustomersRepository;

@Service
@Scope("prototype")
public class CompanyService extends ClientService {

	private int companyID; // represents the company id that logged in

	@Autowired
	public CompanyService(CompaniesRepository companiesRepository,
			CustomersRepository customersRepository, CouponsRepository couponsRepository) {
		super(companiesRepository, customersRepository, couponsRepository);
	}

	/**
	 * @param email    - the company's email
	 * @param password - the company's password
	 * @return true if the email and password exist in companies table, otherwise
	 *         false
	 */
	@Override
	public boolean login(String email, String password) {
		Company company = companiesRepository.findByEmailAndPassword(email, password);
		if (company != null) { // if company exists
			companyID = company.getId(); // sets company id to the company id that logged in.
			return true;
		}
		return false;
	}

	/**
	 * this method adds a coupon to coupons table. (coupon object parameter doesn't
	 * have to contain company object since it will be set to the company that
	 * logged in)
	 * 
	 * @param coupon is the coupon object to be added to coupons table
	 * @throws IOException
	 * @throws CouponSystemException
	 */
	@Transactional
	@LogOperation(msg = "coupon added successfully")
	public void addCoupon(Coupon coupon) throws IOException, CouponSystemException {
		// if coupon title already exists in the coupons of the company that logged in
		if (couponsRepository.existsByCompanyIdAndTitle(companyID, coupon.getTitle()))
			throw new CouponSystemException(ErrorMessage.COUPON_TITLE_EXISTS);

		// getting company object of the company that logged in and setting it to
		// coupon.
		Company company = companiesRepository.findById(companyID);
		coupon.setCompany(company);

		couponsRepository.save(coupon);
	}

	/**
	 * this method updates coupon which belongs to the company that logged in.
	 * (updatedCoupon object parameter doesn't have to contain company object since
	 * it will be set to the company that logged in) (the company that logged in can
	 * update its own coupons only)
	 * 
	 * @param coupon object (must contain coupon id in order to be updated)
	 * @throws IOException
	 * @throws CouponSystemException
	 */
	@LogOperation(msg = "coupon updated successfully")
	public void updateCoupon(Coupon updatedCoupon) throws IOException, CouponSystemException {
		// getting the coupon that needs to be updated from DB.
		Coupon coupon = couponsRepository.findById(updatedCoupon.getId());

		// if coupon does not exist or does not belong to the company that logged in
		if (coupon == null || coupon.getCompany().getId() != companyID)
			throw new CouponSystemException(ErrorMessage.COUPON_NOT_FOUND);

		// setting company object of the company that logged in to updatedCoupon.
		updatedCoupon.setCompany(companiesRepository.findById(companyID));

		couponsRepository.save(updatedCoupon);
	}

	/**
	 * this method deletes the coupon with the id that is passed as parameter. (the
	 * company that logged in can delete its own coupons only) once a coupon gets
	 * deleted - all its customer purchases will be deleted too: -
	 * customers_vs_coupons table: on delete = cascade for coupon_id_fk
	 * 
	 * @param couponID is the id of the coupon that is to be deleted from coupons
	 *                 table
	 * @throws IOException
	 * @throws CouponSystemException
	 */
	@LogOperation(msg = "coupon deleted successfully")
	public void deleteCoupon(int couponId) throws IOException, CouponSystemException {
		// if coupon id does not exist for the company that logged in
		if (!(couponId > 0 && couponsRepository.existsByIdAndCompanyId(couponId, companyID)))
			throw new CouponSystemException(ErrorMessage.COUPON_NOT_FOUND);

		couponsRepository.deleteById(couponId);
	}

	/**
	 * @return list of all coupons that belong to the company that logged in
	 */
	public List<Coupon> getCompanyCoupons() {
		List<Coupon> coupons = couponsRepository.findByCompanyId(companyID);
		return coupons;
	}

	/**
	 * @param category indicates that only coupons from the specific category will
	 *                 be included in the list
	 * @return list of all coupons that belong to the company that logged in from
	 *         the specified category
	 */
	public List<Coupon> getCompanyCoupons(Category category) {
		List<Coupon> coupons = couponsRepository.findByCompanyIdAndCategory(companyID, category);
		return coupons;
	}

	/**
	 * @param maxPrice indicates that only coupons until the specified maxPrice will
	 *                 be included in the list
	 * @return list of all coupons of the company that logged in until maxPrice
	 */
	public List<Coupon> getCompanyCoupons(double maxPrice) {
		List<Coupon> coupons = couponsRepository.findByCompanyIdAndPriceLessThanEqual(companyID, maxPrice);
		return coupons;
	}

	/**
	 * @return company object with all details of the company that logged in
	 */
	public Company getCompanyDetails() {
		Company company = companiesRepository.findById(companyID);
		return company;
	}
}
