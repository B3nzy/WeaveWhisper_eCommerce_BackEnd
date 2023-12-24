package com.weavewhisper.services;

import com.weavewhisper.dtos.UserResponseDto;

public interface UserService {
	UserResponseDto loginUser(String email, String password);
}
