package com.weavewhisper.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weavewhisper.custom_exceptions.ResourceNotFoundException;
import com.weavewhisper.dtos.AuthDto;
import com.weavewhisper.dtos.UserResponseDto;
import com.weavewhisper.entities.BaseUser;
import com.weavewhisper.entities.Customer;
import com.weavewhisper.enums.UserType;
import com.weavewhisper.repositories.CartDao;
import com.weavewhisper.repositories.CustomerDao;
import com.weavewhisper.repositories.UserDao;
import com.weavewhisper.services.UserService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CartDao cartDao;

	@Autowired
	private CustomerDao customerDao;

	@Override
	public UserResponseDto loginUser(AuthDto authDto) {
		BaseUser user = userDao.findByEmailAndPassword(authDto.getEmail(), authDto.getPassword());

		if (user != null) {
			UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
			if (userResponseDto.getType().equals(UserType.valueOf("CUSTOMER"))) {
				Customer customer = customerDao.findById(userResponseDto.getId())
						.orElseThrow(() -> new ResourceNotFoundException("No such customer exists with that id."));
				userResponseDto.setCartCount(cartDao.findByCustomerRef(customer).size());
			}
			return userResponseDto;
		} else {
			throw new ResourceNotFoundException("No such user exists with that email and password!!");
		}

	}

}
