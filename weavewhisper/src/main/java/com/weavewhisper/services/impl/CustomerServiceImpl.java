package com.weavewhisper.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weavewhisper.custom_exceptions.ResourceNotFoundException;
import com.weavewhisper.custom_exceptions.UnauthorizedException;
import com.weavewhisper.dtos.RegisterUserDto;
import com.weavewhisper.dtos.UserResponseDto;
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

	@Override
	public UserResponseDto updateCustomer(RegisterUserDto user) {
		Customer customer = customerDao.findById(user.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("No such user found with that id"));
		if (customer.getPassword().equals(user.getPassword())) {
			Customer newCustomer = modelMapper.map(user, Customer.class);
			newCustomer.setId(user.getUserId());
			System.out.println(newCustomer);
			Customer savedCustomer = customerDao.save(newCustomer);
			UserResponseDto userResponseDto = modelMapper.map(savedCustomer, UserResponseDto.class);
			return (userResponseDto);

		} else {
			throw new UnauthorizedException("Wrong password");
		}
	}

}
