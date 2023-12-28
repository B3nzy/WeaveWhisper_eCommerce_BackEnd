package com.weavewhisper.services;

import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.RegisterUserDto;
import com.weavewhisper.entities.Manufacturer;

public interface ManufacturerService {
	void registerManufacturer(RegisterUserDto manufacturer);
	ApiResponse deleteManufacturer(Long manufacturerId);
}
