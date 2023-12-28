package com.weavewhisper.services;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.ProductRequestDto;

public interface ProductService {
	ApiResponse addProduct(ProductRequestDto productRequestDto);
}
