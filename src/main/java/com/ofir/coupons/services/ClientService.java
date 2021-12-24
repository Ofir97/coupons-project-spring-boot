package com.ofir.coupons.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ofir.coupons.repositories.CompaniesRepository;
import com.ofir.coupons.repositories.CouponsRepository;
import com.ofir.coupons.repositories.CustomersRepository;

@Service
public abstract class ClientService {
	
	protected CompaniesRepository companiesRepository;
    protected CustomersRepository customersRepository;
    protected CouponsRepository couponsRepository;
    
    @Autowired
    public ClientService(CompaniesRepository companiesRepository, 
    				     CustomersRepository customersRepository, 
    				     CouponsRepository couponsRepository) {
    	this.companiesRepository = companiesRepository;
    	this.customersRepository = customersRepository;
    	this.couponsRepository = couponsRepository;
    	
    }

    public abstract boolean login(String email, String password);
}
