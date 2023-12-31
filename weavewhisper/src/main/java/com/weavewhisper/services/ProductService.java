package com.weavewhisper.services;

import java.util.List;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.ProductRequestDto;
import com.weavewhisper.dtos.ProductResponseDto;
import com.weavewhisper.entities.Manufacturer;
import com.weavewhisper.dtos.ProductCreatedApiResponseDto;

public interface ProductService {
	ProductCreatedApiResponseDto addProduct(ProductRequestDto productRequestDto);
	ProductResponseDto getSingleProduct(Long productId);
	ApiResponse deleteSingleProduct(Long productId, Long manufacturerId);
	ApiResponse updateProduct(ProductRequestDto productRequestDto);
	List<ProductResponseDto> getAllProducts();
}
