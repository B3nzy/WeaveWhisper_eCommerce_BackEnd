package com.weavewhisper.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weavewhisper.dtos.RegisterUserDto;
import com.weavewhisper.entities.Customer;
import com.weavewhisper.repositories.CustomerDao;
import com.weavewhisper.services.CustomerService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void registerCustomer(RegisterUserDto customer) {

		customerDao.save(modelMapper.map(customer, Customer.class));

	}


}
