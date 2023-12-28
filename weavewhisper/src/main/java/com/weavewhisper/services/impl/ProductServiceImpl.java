package com.weavewhisper.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weavewhisper.custom_exceptions.ResourceNotFoundException;
import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.ProductRequestDto;
import com.weavewhisper.dtos.ProductResponseDto;
import com.weavewhisper.dtos.ProductCreatedApiResponseDto;
import com.weavewhisper.entities.Product;
import com.weavewhisper.entities.ProductColor;
import com.weavewhisper.entities.ProductSize;
import com.weavewhisper.enums.ColorType;
import com.weavewhisper.enums.SizeType;
import com.weavewhisper.repositories.ProductDao;
import com.weavewhisper.services.ProductService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductDao productDao;

	@Override
	public ProductCreatedApiResponseDto addProduct(ProductRequestDto productRequestDto) {
		Product product = modelMapper.map(productRequestDto, Product.class);
		System.out.println(productRequestDto);
		System.out.println(product);

		ArrayList<String> colorList = productRequestDto.getColors();
		ArrayList<String> sizeList = productRequestDto.getSizes();

		System.out.println(colorList + " " + sizeList);

		for (int i = 0; i < sizeList.size(); i++) {
			product.addSize(new ProductSize(SizeType.valueOf(sizeList.get(i))));
		}

		for (int i = 0; i < colorList.size(); i++) {
			product.addColor(new ProductColor(ColorType.valueOf(colorList.get(i))));
		}

		Product savedProduct = productDao.saveAndFlush(product);

		return new ProductCreatedApiResponseDto(savedProduct.getId(), true, "Product added successfully!");
	}

	@Override
	public ProductResponseDto getSingleProduct(Long productId) {
		Product product = productDao.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("No product found with that id"));

		ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
		productResponseDto
				.setColors(product.getColorSet().stream().map(s -> s.getColor().name()).collect(Collectors.toSet()));
		productResponseDto
				.setSizes(product.getSizeSet().stream().map(s -> s.getSize().name()).collect(Collectors.toSet()));

		return productResponseDto;
	}

}
