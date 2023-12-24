package com.weavewhisper.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weavewhisper.custom_exceptions.ResourceNotFoundException;
import com.weavewhisper.dtos.UserResponseDto;
import com.weavewhisper.entities.BaseUser;
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

	@Override
	public UserResponseDto loginUser(String email, String password) {
		BaseUser user = userDao.findByEmailAndPassword(email, password);
		
		if (user != null) {
			return modelMapper.map(user, UserResponseDto.class);
		} else {
			throw new ResourceNotFoundException("No such user exists with that email and password!!");
		}

	}

}
