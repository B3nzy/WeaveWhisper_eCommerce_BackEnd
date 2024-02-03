package com.weavewhisper.services;

import java.util.List;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.CartRequestDto;
import com.weavewhisper.dtos.CartResponseDto;

public interface CartService {

	ApiResponse addCart(CartRequestDto cartRequestDto);

	List<CartResponseDto> getCartItemsForCustomer(Long customerId);
	
	ApiResponse removeCart(Long CartId, Long customerId);
}
