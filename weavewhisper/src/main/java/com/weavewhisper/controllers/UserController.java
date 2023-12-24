package com.weavewhisper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.AuthDto;
import com.weavewhisper.dtos.RegisterUserDto;
import com.weavewhisper.dtos.UserResponseDto;
import com.weavewhisper.entities.BaseUser;
import com.weavewhisper.enums.UserType;
import com.weavewhisper.services.CustomerService;
import com.weavewhisper.services.ManufacturerService;
import com.weavewhisper.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	public CustomerService customerService;

	@Autowired
	public ManufacturerService manufacturerService;

	@Autowired
	public UserService userService;

	@PostMapping("/sign-up")
	public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserDto user) {		
		if (user.getType().equals(UserType.CUSTOMER)) {
			customerService.registerCustomer(user);
		} else if (user.getType().equals(UserType.MANUFACTURER)) {
			manufacturerService.registerManufacturer(user);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "Successfully created the user!"));
	}

	@PostMapping("/sign-in")
	public ResponseEntity<?> loginUser(@RequestBody @Valid AuthDto authDto) {
		System.out.println(authDto);
		UserResponseDto userResponseDto = userService.loginUser(authDto.getEmail(), authDto.getPassword());
		System.out.println(userResponseDto);
		return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
	}

}
