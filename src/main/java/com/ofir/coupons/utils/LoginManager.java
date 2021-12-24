package com.ofir.coupons.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.ofir.coupons.custom_exception.CouponSystemException;
import com.ofir.coupons.enums.ClientType;
import com.ofir.coupons.enums.ErrorMessage;
import com.ofir.coupons.services.AdminService;
import com.ofir.coupons.services.ClientService;
import com.ofir.coupons.services.CompanyService;
import com.ofir.coupons.services.CustomerService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginManager {
	
	private final ApplicationContext context;
	
	/**
	 * @param 	email is the email of the admin/company/customer that wants to log in
	 * @param 	password is the password of the admin/company/customer that wants to log in
	 * @param 	clientType is the type of client that wants to login(types are: admin, company and customer)
	 * @return	the matching ClientFacade if login is successful, otherwise throws BadLoginException
	 * @throws 	BadLoginException if login failed so the user can be informed about the error
	 */
	public ClientService login(String email, String password, ClientType clientType) throws CouponSystemException {
		ClientService clientService = null;
		
		switch (clientType) {
		case ADMINISTRATOR:
			clientService = context.getBean(AdminService.class);
			break;
		case COMPANY:
			clientService = context.getBean(CompanyService.class);
			break;
		case CUSTOMER:
			clientService = context.getBean(CustomerService.class);
		}

		if (clientService != null && clientService.login(email, password))
			return clientService;

		throw new CouponSystemException(ErrorMessage.BAD_LOGIN);
	}
}
