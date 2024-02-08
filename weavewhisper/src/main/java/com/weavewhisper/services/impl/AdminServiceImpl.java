package com.weavewhisper.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weavewhisper.custom_exceptions.ResourceNotFoundException;
import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.admindtos.AdminLoginRequestDto;
import com.weavewhisper.dtos.admindtos.AdminLoginResponseDto;
import com.weavewhisper.dtos.admindtos.AdminRegistrationRequestDto;
import com.weavewhisper.dtos.admindtos.RequestedManufacturerRegistrationResponseDto;
import com.weavewhisper.entities.BaseUser;
import com.weavewhisper.entities.Manufacturer;
import com.weavewhisper.enums.ManufacturerAccountStatusType;
import com.weavewhisper.enums.UserType;
import com.weavewhisper.repositories.ManufacturerDao;
import com.weavewhisper.repositories.UserDao;
import com.weavewhisper.services.AdminService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ManufacturerDao manufacturerDao;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AdminLoginResponseDto login(AdminLoginRequestDto adminLoginRequestDto) {
		BaseUser user = userDao.findByEmailAndPassword(adminLoginRequestDto.getEmail(),
				adminLoginRequestDto.getPassword());

		if (user != null) {
			AdminLoginResponseDto adminLoginResponseDto = modelMapper.map(user, AdminLoginResponseDto.class);
			return adminLoginResponseDto;
		} else {
			throw new ResourceNotFoundException("No such user exists with that email and password!!");
		}
	}

	@Override
	public ApiResponse register(AdminRegistrationRequestDto adminRegistrationRequestDto) {
		BaseUser user = modelMapper.map(adminRegistrationRequestDto, BaseUser.class);
		user.setType(UserType.ADMIN);
		System.out.println(user);
		userDao.save(user);
		return new ApiResponse(true, "Successfully created an admin.");
	}

	@Override
	public List<?> getRequestedManufacturerRegistration() {
		List<Manufacturer> manufacturerList = manufacturerDao
				.findByAccountStatus(ManufacturerAccountStatusType.REQUESTED);
		List<RequestedManufacturerRegistrationResponseDto> reqManufacturerListDto = new ArrayList<>();
		for (int i = 0; i < manufacturerList.size(); i++) {
			Manufacturer manufacturer = manufacturerList.get(i);
			reqManufacturerListDto
					.add(modelMapper.map(manufacturer, RequestedManufacturerRegistrationResponseDto.class));
		}
		return reqManufacturerListDto;
	}

}
