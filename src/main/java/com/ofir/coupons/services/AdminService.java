package com.ofir.coupons.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ofir.coupons.annotations.LogOperation;
import com.ofir.coupons.beans.Company;
import com.ofir.coupons.beans.Customer;
import com.ofir.coupons.custom_exception.CouponSystemException;
import com.ofir.coupons.enums.ErrorMessage;
import com.ofir.coupons.repositories.CompaniesRepository;
import com.ofir.coupons.repositories.CouponsRepository;
import com.ofir.coupons.repositories.CustomersRepository;

@Service
@Scope("prototype")
public class AdminService extends ClientService {

	@Autowired
	public AdminService(CompaniesRepository companiesRepository, CustomersRepository customersRepository,
			CouponsRepository couponsRepository) {
		super(companiesRepository, customersRepository, couponsRepository);
	}

	@Override
	public boolean login(String email, String password) {
		return (email.equals("admin@admin.com") && password.equals("admin"));
	}

	/**
	 * @param company is the company object to be added to DB
	 * 
	 * @throws IOException
	 * @throws CouponSystemException 
	 */
	
	@LogOperation(msg = "company added successfully")
	public void addCompany(Company company) throws IOException, CouponSystemException {
		// if company name or email already exists in DB:
		if (companiesRepository.existsByNameOrEmail(company.getName(), company.getEmail()))
			throw new CouponSystemException(ErrorMessage.COMPANY_NAME_OR_EMAIL_EXISTS);

		companiesRepository.save(company);
	}

	/**
	 * company name cannot be updated. company email cannot be updated to an
	 * existing email in DB. (Unique Constraint on email field)
	 * 
	 * @param updatedCompany is the company object to be updated in DB
	 * @throws IOException
	 * @throws CouponSystemException
	 */
	@LogOperation(msg = "company updated successfully")
	public void updateCompany(Company updatedCompany) throws IOException, CouponSystemException {
		// if company does not exist -
		if (!(updatedCompany.getId() > 0 && companiesRepository.existsById(updatedCompany.getId())))
			throw new CouponSystemException(ErrorMessage.COMPANY_NOT_FOUND);

		Company company = companiesRepository.findById(updatedCompany.getId());

		// if updated company name different from name in DB -
		if (!company.getName().equals(updatedCompany.getName()))
			throw new CouponSystemException(ErrorMessage.COMPANY_NAME_UPDATED);

		companiesRepository.save(updatedCompany);
	}

	/**
	 * when a company gets deleted - all its corresponding coupons will be deleted
	 * too, as well as coupon purchases made by customers: - coupons table: on
	 * delete = cascade for company_id_fk - customers_vs_coupons: on delete =
	 * cascade for coupon_id_fk
	 * 
	 * @param  companyID is the id of the company to be deleted from DB
	 * @throws IOException
	 * @throws CouponSystemException 
	 */
	@LogOperation(msg = "company deleted successfully")
	public void deleteCompany(int companyID) throws IOException, CouponSystemException {
		// if company does not exist
		if (!(companyID > 0 && companiesRepository.existsById(companyID))) 
			throw new CouponSystemException(ErrorMessage.COMPANY_NOT_FOUND);

		companiesRepository.deleteById(companyID);
	}

	/**
	 * @return list of all companies in DB.
	 */
	public List<Company> getAllCompanies() {
		List<Company> companies = companiesRepository.findAll();
		return companies;
	}

	/**
	 * @param companyID is the id of the company that exists in DB
	 * @return company object with the id that is passes as parameter
	 * @throws CouponSystemException
	 */
	public Company getOneCompany(int companyID) throws CouponSystemException {
		Company company = companiesRepository.findById(companyID);
		if (company == null) // if company does not exist
			throw new CouponSystemException(ErrorMessage.COMPANY_NOT_FOUND);

		return company;
	}

	/**
	 * @param customer object to be added to DB
	 * @throws IOException
	 * @throws CouponSystemException 
	 */
	@LogOperation(msg = "customer added successfully")
	public void addCustomer(Customer customer) throws IOException, CouponSystemException {
		// if customer email already exists in DB
		if (customersRepository.existsByEmail(customer.getEmail()))
			throw new CouponSystemException(ErrorMessage.CUSTOMER_EMAIL_EXISTS);

		customersRepository.save(customer);
	}

	/**
	 * customer object must contain an id in order to be updated. customer email
	 * cannot be updated to an existing email in DB. (Unique Constraint on email
	 * field)
	 * 
	 * @param customer object to be updated in DB
	 * @throws IOException
	 * @throws CouponSystemException 
	 */
	@LogOperation(msg = "customer updated successfully")
	public void updateCustomer(Customer updatedCustomer) throws IOException, CouponSystemException {
		// if customer does not exist in DB
		if (!(updatedCustomer.getId() > 0 && customersRepository.existsById(updatedCustomer.getId()))) 
			throw new CouponSystemException(ErrorMessage.CUSTOMER_NOT_FOUND);

		// setting the coupons purchased by the customer to updatedCustomer
		Customer customer = customersRepository.findById(updatedCustomer.getId());
		updatedCustomer.setCoupons(customer.getCoupons());

		customersRepository.save(updatedCustomer);
	}

	/**
	 * when a customer gets deleted - all his coupon purchases will be deleted too.
	 * - customers_vs_coupons table: on delete = cascade for customer_id_fk
	 * 
	 * @param customerID is the id of the customer that is to be removed from DB
	 * @throws IOException
	 * @throws CouponSystemException 
	 */
	@LogOperation(msg = "customer deleted successfully")
	public void deleteCustomer(int customerID) throws IOException, CouponSystemException {
		// if customer does not exist in DB
		if (!(customerID > 0 && customersRepository.existsById(customerID))) 
			throw new CouponSystemException(ErrorMessage.CUSTOMER_NOT_FOUND);

		customersRepository.deleteById(customerID);
	}

	/**
	 * @return list of all customers in DB
	 */
	public List<Customer> getAllCustomers() {
		List<Customer> customers = customersRepository.findAll();
		return customers;
	}

	/**
	 * @param customerID is the id of the customer that exists in DB
	 * @return customer object with the id that is passes as parameter
	 * @throws CouponSystemException 
	 */
	public Customer getOneCustomer(int customerID) throws CouponSystemException  {
		Customer customer = customersRepository.findById(customerID);
		if (customer == null) // if customer does not exist
			throw new CouponSystemException(ErrorMessage.CUSTOMER_NOT_FOUND);

		return customer;
	}

}
