package com.weavewhisper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.ProductRequestDto;
import com.weavewhisper.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@PostMapping("/add")
	public ResponseEntity<?> addProduct(@RequestBody ProductRequestDto productRequestDto){
		ApiResponse apiRes = productService.addProduct(productRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(apiRes);
	}
}
