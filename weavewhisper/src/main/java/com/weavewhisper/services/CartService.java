package com.weavewhisper.services;

import java.util.List;

import com.razorpay.RazorpayException;
import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.CartRequestDto;
import com.weavewhisper.dtos.CartResponseDto;
import com.weavewhisper.dtos.PlaceOrderRequestDto;
import com.weavewhisper.dtos.PlaceOrderResponseDto;

public interface CartService {

	ApiResponse addCart(CartRequestDto cartRequestDto);

	List<CartResponseDto> getCartItemsForCustomer(Long customerId);

	ApiResponse removeCart(Long CartId, Long customerId);

	PlaceOrderResponseDto handlePlaceOrderRequest(PlaceOrderRequestDto placeOrderRequestDto) throws RazorpayException;
}
