package com.weavewhisper.services;

import com.weavewhisper.dtos.RegisterUserDto;
import com.weavewhisper.entities.Customer;

public interface CustomerService {
	void registerCustomer(RegisterUserDto customer);
}
