package com.ofir.coupons.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.ofir.coupons.beans.Company;

@Repository
public interface CompaniesRepository extends JpaRepository<Company, Integer> {
	Company findById(int companyId);
	boolean existsByNameOrEmail(String name, String email);
	Company findByEmailAndPassword(String email, String password);
	
}
