package com.weavewhisper.services;

import java.util.List;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.ProductShortResponseDto;
import com.weavewhisper.dtos.RegisterUserDto;

public interface ManufacturerService {
	void registerManufacturer(RegisterUserDto manufacturer);
	ApiResponse deleteManufacturerListings(Long manufacturerId);
	ApiResponse deleteManufacturer(Long manufacturerId);
	List<ProductShortResponseDto> getAllProducts(Long manufacturerId);
}
