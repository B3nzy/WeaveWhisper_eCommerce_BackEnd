package com.weavewhisper.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weavewhisper.dtos.ApiResponse;

@RestController
@RequestMapping("/api/tests")
public class TestController {

	@GetMapping("/test")
	public ResponseEntity<?> testMe() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Something went wrong"));
	}
}
