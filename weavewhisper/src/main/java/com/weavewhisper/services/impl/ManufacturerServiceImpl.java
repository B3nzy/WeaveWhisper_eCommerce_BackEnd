package com.weavewhisper.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
