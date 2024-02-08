package com.weavewhisper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.admindtos.AdminLoginRequestDto;
import com.weavewhisper.dtos.admindtos.AdminLoginResponseDto;
import com.weavewhisper.dtos.admindtos.AdminRegistrationRequestDto;
import com.weavewhisper.services.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/login")
	public ResponseEntity<?> loginAdmin(@RequestBody AdminLoginRequestDto adminLoginRequestDto) {
		AdminLoginResponseDto adminLoginResponseDto = adminService.login(adminLoginRequestDto);
		return ResponseEntity.status(HttpStatus.OK).body(adminLoginResponseDto);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerAdmin(@RequestBody AdminRegistrationRequestDto adminRegistrationRequestDto) {
		ApiResponse apiResp = adminService.register(adminRegistrationRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResp);
	}

	@GetMapping("/getrequestedmanufacturerregistration")
	public ResponseEntity<?> getRequestedManufacturerRegistration() {

		return null;
	}

}
