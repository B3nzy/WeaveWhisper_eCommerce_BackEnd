package com.weavewhisper.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tests")
public class TestController {

	@GetMapping("/")
	public String testMe() {
		return "Test success";
	}
}
