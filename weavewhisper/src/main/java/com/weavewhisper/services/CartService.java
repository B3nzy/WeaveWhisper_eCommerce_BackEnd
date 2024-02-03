package com.weavewhisper.services;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.CartRequestDto;

public interface CartService {

	ApiResponse addCart(CartRequestDto cartRequestDto);

	ApiResponse removeCart(Long CartId);
}
