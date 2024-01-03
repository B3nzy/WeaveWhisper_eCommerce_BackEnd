package com.weavewhisper.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.ProductCreatedApiResponseDto;
import com.weavewhisper.dtos.ProductRequestDto;
import com.weavewhisper.dtos.ProductResponseDto;
import com.weavewhisper.dtos.SearchProductDto;

@Service
public interface ProductService {
	ProductCreatedApiResponseDto addProduct(ProductRequestDto productRequestDto);
	ProductResponseDto getSingleProduct(Long productId);
	ApiResponse deleteSingleProduct(Long productId, Long manufacturerId);
	ApiResponse updateProduct(ProductRequestDto productRequestDto);
	List<ProductResponseDto> getAllProducts(SearchProductDto searchProductDto);
}
