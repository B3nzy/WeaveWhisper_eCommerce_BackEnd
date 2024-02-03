package com.weavewhisper.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.CartRequestDto;
import com.weavewhisper.dtos.CartResponseDto;
import com.weavewhisper.services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/add")
	public ResponseEntity<?> addProductInCart(@RequestBody CartRequestDto cartRequestDto) {
		System.out.println(cartRequestDto);
		ApiResponse apiRes = cartService.addCart(cartRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(apiRes);
	}

	@DeleteMapping("/{cartId}/customer/{customerId}")
	public ResponseEntity<?> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long customerId) {
		ApiResponse apiRes = cartService.removeCart(cartId, customerId);
		return ResponseEntity.status(HttpStatus.OK).body(apiRes);
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<?> getCartItems(@PathVariable Long customerId) {
		List<CartResponseDto> cartResList = cartService.getCartItemsForCustomer(customerId);
		return ResponseEntity.status(HttpStatus.OK).body(cartResList);
	}

}
