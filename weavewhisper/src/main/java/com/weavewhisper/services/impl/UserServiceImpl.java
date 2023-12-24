package com.weavewhisper.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weavewhisper.dtos.UserResponseDto;
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
		return modelMapper.map(userDao.findByEmailAndPassword(email, password), UserResponseDto.class);
	}

}
