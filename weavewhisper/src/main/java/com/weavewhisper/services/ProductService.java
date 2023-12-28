package com.weavewhisper.services;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.ProductRequestDto;
import com.weavewhisper.dtos.ProductResponseDto;
import com.weavewhisper.dtos.ProductCreatedApiResponseDto;

public interface ProductService {
	ProductCreatedApiResponseDto addProduct(ProductRequestDto productRequestDto);
	ProductResponseDto getSingleProduct(Long productId);
	ApiResponse deleteSingleProduct(Long productId);
}
