package com.weavewhisper.services.impl;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weavewhisper.custom_exceptions.DuplicateBrandNameException;
import com.weavewhisper.custom_exceptions.DuplicateEmailException;
import com.weavewhisper.custom_exceptions.DuplicatePanNumberException;
import com.weavewhisper.custom_exceptions.ResourceNotFoundException;
import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.ManufacturerSoldProductResponseDto;
import com.weavewhisper.dtos.OrderHistoryResponseDto;
import com.weavewhisper.dtos.ProductShortResponseDto;
import com.weavewhisper.dtos.RegisterUserDto;
import com.weavewhisper.entities.Manufacturer;
import com.weavewhisper.entities.OrderHistory;
import com.weavewhisper.entities.Product;
import com.weavewhisper.enums.OrderStatusType;
import com.weavewhisper.repositories.ManufacturerDao;
import com.weavewhisper.repositories.OrderHistoryDao;
import com.weavewhisper.repositories.ProductDao;
import com.weavewhisper.repositories.UserDao;
import com.weavewhisper.services.ManufacturerService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService {

	@Autowired
	public ManufacturerDao manufacturerDao;

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	public ProductDao productDao;

	@Autowired
	public OrderHistoryDao orderHistoryDao;

	@Autowired
	private UserDao userDao;

	@Override
	public void registerManufacturer(RegisterUserDto manufacturerDto) {
		if (userDao.existsByEmail(manufacturerDto.getEmail())) {
			throw new DuplicateEmailException("User with this email already exists!");
		} else if (manufacturerDao.existsByBrandName(manufacturerDto.getBrandName())) {
			throw new DuplicateBrandNameException("Brand already registered.");
		} else if (manufacturerDao.existsByPanNumber(manufacturerDto.getPanNumber())) {
			throw new DuplicatePanNumberException("Pan Number already exists.");
		} else {
			manufacturerDao.save(modelMapper.map(manufacturerDto, Manufacturer.class));
		}
	}

	@Override
	public ApiResponse deleteManufacturerListings(Long manufacturerId) {
		Manufacturer manufacturer = manufacturerDao.findById(manufacturerId)
				.orElseThrow(() -> new ResourceNotFoundException("No Manufacturer found with that id"));

		manufacturer.getProductList().forEach((p) -> {
			manufacturer.removeProduct(p);
		});

		return new ApiResponse(true, "Deleted all listings of the manufacturer successfully!");
	}

	@Override
	public ApiResponse deleteManufacturer(Long manufacturerId) {
		Manufacturer manufacturer = manufacturerDao.findById(manufacturerId)
				.orElseThrow(() -> new ResourceNotFoundException("No Manufacturer found with that id"));

		manufacturerDao.deleteById(manufacturerId);
		return new ApiResponse(true, "Manufacturer deleted successfully!");
	}

	@Override
	public List<ProductShortResponseDto> getAllProducts(Long manufacturerId) {
		Manufacturer manufacturer = manufacturerDao.findById(manufacturerId)
				.orElseThrow(() -> new ResourceNotFoundException("No manufacture found with that id"));

		List<ProductShortResponseDto> productResDto = new ArrayList<>();

		manufacturer.getProductList().stream().forEach(p -> {
			ProductShortResponseDto productShortResponseDto = modelMapper.map(p, ProductShortResponseDto.class);
			productShortResponseDto.setImageName(p.getImageList().get(0).getImageName());
			productResDto.add(productShortResponseDto);
		});

		return productResDto;
	}

	@Override
	public List<String> getAllManufacturerBrandNames() {

		List<Manufacturer> manufacturerList = manufacturerDao.findAll();
		List<String> manufacturerBrandNameList = manufacturerList.stream().map(m -> m.getBrandName())
				.collect(Collectors.toList());

		return manufacturerBrandNameList;
	}

	@Override
	public List<ManufacturerSoldProductResponseDto> getAllSoldProducts(Long manufacturerId) {

		Manufacturer manufacturer = manufacturerDao.findById(manufacturerId)
				.orElseThrow(() -> new ResourceNotFoundException("No Manufacturer found with that id"));

		List<OrderHistory> orderHistoryList = orderHistoryDao.findByManufacturer(manufacturer);

		List<ManufacturerSoldProductResponseDto> resDtoList = new ArrayList<>();

		for (int i = 0; i < orderHistoryList.size(); i++) {
			OrderHistory oHist = orderHistoryList.get(i);

			ManufacturerSoldProductResponseDto resDto = modelMapper.map(oHist,
					ManufacturerSoldProductResponseDto.class);

			if (oHist.getOrderStatus().equals(OrderStatusType.DELIVERED)
					|| oHist.getOrderStatus().equals(OrderStatusType.CANCELLED)) {
				continue;
			}

			resDto.setImageName(oHist.getProductRef().getImageList().get(0).getImageName());
			resDto.setOrderHistoryId(oHist.getId());
			resDto.setProductId(oHist.getProductRef().getId());
			resDto.setOrderDate(oHist.getCreatedAt().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));

			resDtoList.add(resDto);

		}

		return resDtoList;
	}

}
