package com.weavewhisper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.CartRequestDto;
import com.weavewhisper.services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;

	@PostMapping("/add")
	private ResponseEntity<?> addProductInCart(@RequestBody CartRequestDto cartRequestDto){
		System.out.println(cartRequestDto);
		ApiResponse apiRes = cartService.addCart(cartRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(apiRes);
	}
	
}
