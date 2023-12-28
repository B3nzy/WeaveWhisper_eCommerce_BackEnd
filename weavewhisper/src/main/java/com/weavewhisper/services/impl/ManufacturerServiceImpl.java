package com.weavewhisper.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weavewhisper.custom_exceptions.ResourceNotFoundException;
import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.RegisterUserDto;
import com.weavewhisper.entities.Manufacturer;
import com.weavewhisper.repositories.ManufacturerDao;
import com.weavewhisper.services.ManufacturerService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService {

	@Autowired
	public ManufacturerDao manufacturerDao;

	@Autowired
	public ModelMapper modelMapper;

	@Override
	public void registerManufacturer(RegisterUserDto manufacturer) {
		manufacturerDao.save(modelMapper.map(manufacturer, Manufacturer.class));

	}

	@Override
	public ApiResponse deleteManufacturer(Long manufacturerId) {
		if(manufacturerDao.existsById(manufacturerId)) {
			manufacturerDao.deleteById(manufacturerId);
			return new ApiResponse(true, "Manufacturer deleted successfully!");
		} else {
			throw new ResourceNotFoundException("No Manufacturer found with that id");
		}
	}

}
