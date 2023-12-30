package com.weavewhisper.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.ProductRequestDto;
import com.weavewhisper.dtos.ProductResponseDto;
import com.weavewhisper.dtos.ProductShortResponseDto;
import com.weavewhisper.dtos.ProductCreatedApiResponseDto;
import com.weavewhisper.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/")
	public ResponseEntity<?> getAllProducts(){
		List<ProductResponseDto> productList = productService.getAllProducts();
		return ResponseEntity.status(HttpStatus.OK).body(productList);
		
	}

	@PostMapping("/add")
	public ResponseEntity<?> addProduct(@RequestBody ProductRequestDto productRequestDto) {
		ProductCreatedApiResponseDto productCreatedApiResponseDto = productService.addProduct(productRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(productCreatedApiResponseDto);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody ProductRequestDto productRequestDto) {
		ApiResponse apiResponse = productService.updateProduct(productRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
	}

	@GetMapping("/get/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable Long productId) {
		ProductResponseDto productResponseDto = productService.getSingleProduct(productId);
		return ResponseEntity.status(HttpStatus.OK).body(productResponseDto);
	}

	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<?> deleteProductById(@PathVariable Long productId) {
		ApiResponse apiRes = productService.deleteSingleProduct(productId);
		return ResponseEntity.status(HttpStatus.OK).body(apiRes);
	}

}
