package com.ofir.coupons.utils;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ofir.coupons.beans.Company;
import com.ofir.coupons.beans.Coupon;
import com.ofir.coupons.beans.Customer;
import com.ofir.coupons.enums.Category;
import com.ofir.coupons.enums.ClientType;
import com.ofir.coupons.services.AdminService;
import com.ofir.coupons.services.CompanyService;
import com.ofir.coupons.services.CustomerService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Test {

	private final LoginManager loginManager;

	public void testAll() {
		try {
			testAdmin();
			testCompany();
			testCustomer();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void testAdmin() throws Exception {
    	AdminService adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);

//    	adminService.addCompany(Company.builder()
//                .name("BUG2")
//                .email("bug2@gmail.com")
//                .password("b123")
//                .build());

//    	adminService.updateCompany(Company.builder()
//                .id(59)
//                .name("BUG2")
//                .email("ba@gmail.com")
//                .password("ua")
//                .build());

//    	adminService.deleteCompany(59);

//    	adminService.getAllCompanies().forEach(company -> System.out.println(company));

//    	System.out.println(adminService.getOneCompany(58));

//      adminService.addCustomer(Customer.builder()
//                .firstName("Beni")
//                .lastName("Goren")
//                .password("beni11")
//                .email("beni@gmail.com")
//                .build());

//      adminService.updateCustomer(Customer.builder()
//                .id(28)
//                .firstName("Steve")
//                .lastName("Goren")
//                .password("s1")
//                .email("gorenush@walla.com")
//                .build());

//    	adminService.deleteCustomer(31);

//    	adminService.getAllCustomers().forEach(customer -> System.out.println(customer));

//    	System.out.println(adminService.getOneCustomer(23));

	}

	public void testCompany() throws Exception {
		CompanyService companyService = (CompanyService) loginManager.login("bug@gmail.com", "b10",
				ClientType.COMPANY);

//       companyService.addCoupon(Coupon.builder()
//                 .category(Category.TECHNOLOGY)
//                 .title("iPhone 13")
//                 .description("20% discount on iPhone 13, 128GB")
//                 .startDate(new Date(System.currentTimeMillis()))
//                 .endDate(Date.valueOf("2022-12-5"))
//                 .amount(20)
//                 .price(3100)
//                 .image("https://d2bgjx2gb489de.cloudfront.net/gbb-blogs/wp-content/uploads/2017/05/16213722/Berlin_city_viewXL.jpg")
//                 .build());

//		companyService.updateCoupon(Coupon.builder()
//				.id(83)
//				.category(Category.TECHNOLOGY)
//				.title("iPhone 13")
//				.description("15% discount on iPhone 13, 128GB")
//				.startDate(new Date(System.currentTimeMillis()))
//				.endDate(Date.valueOf("2022-12-5"))
//				.amount(20)
//				.price(3100)
//				.image("https://d2bgjx2gb489de.cloudfront.net/gbb-blogs/wp-content/uploads/2017/05/16213722/Berlin_city_viewXL.jpg")
//				.build());

//        companyService.deleteCoupon(83);

//        companyService.getCompanyCoupons().forEach(coupon -> System.out.println(coupon));

//        companyService.getCompanyCoupons(Category.TECHNOLOGY).forEach(coupon -> System.out.println(coupon));

//        companyService.getCompanyCoupons(1300).forEach(coupon -> System.out.println(coupon));

//        System.out.println(companyService.getCompanyDetails());

	}

	public void testCustomer() throws Exception {
    	 CustomerService customerService = (CustomerService) loginManager.login("maria@gmail.com", "323", ClientType.CUSTOMER);

//    	 customerService.purchaseCoupon(84);

//    	 customerService.getCustomerCoupons().forEach(coupon -> System.out.println(coupon));

//       customerService.getCustomerCoupons(Category.TECHNOLOGY).forEach(coupon -> System.out.println(coupon));

//       customerService.getCustomerCoupons(1500).forEach(coupon -> System.out.println(coupon));

//       System.out.println(customerService.getCustomerDetails());

	}

}
