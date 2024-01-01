package com.weavewhisper.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weavewhisper.custom_exceptions.ResourceNotFoundException;
import com.weavewhisper.dtos.ApiResponse;
import com.weavewhisper.dtos.ProductCreatedApiResponseDto;
import com.weavewhisper.dtos.ProductRequestDto;
import com.weavewhisper.dtos.ProductResponseDto;
import com.weavewhisper.entities.Manufacturer;
import com.weavewhisper.entities.Product;
import com.weavewhisper.entities.ProductColor;
import com.weavewhisper.entities.ProductImage;
import com.weavewhisper.entities.ProductSize;
import com.weavewhisper.enums.ColorType;
import com.weavewhisper.enums.SizeType;
import com.weavewhisper.repositories.ManufacturerDao;
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

	@Autowired
	private ManufacturerDao manufacturerDao;

	@Override
	public ProductCreatedApiResponseDto addProduct(ProductRequestDto productRequestDto) {

		Manufacturer manufacturer = manufacturerDao.findById(productRequestDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("No user found with that user id"));

		Product product = modelMapper.map(productRequestDto, Product.class);
		System.out.println(productRequestDto);
		System.out.println(product);

		ArrayList<String> colorList = productRequestDto.getColors();
		ArrayList<String> sizeList = productRequestDto.getSizes();
		ArrayList<String> imageList = productRequestDto.getImageNames();

		System.out.println(colorList + " " + sizeList);

		for (int i = 0; i < sizeList.size(); i++) {
			product.addSize(new ProductSize(SizeType.valueOf(sizeList.get(i))));
		}

		for (int i = 0; i < colorList.size(); i++) {
			product.addColor(new ProductColor(ColorType.valueOf(colorList.get(i))));
		}

		for (int i = 0; i < imageList.size(); i++) {
			product.addImage(new ProductImage(imageList.get(i)));
		}

		manufacturer.addProduct(product);

		Product savedProduct = productDao.findByName(product.getName());

		return new ProductCreatedApiResponseDto(savedProduct.getId(), true, "Product added successfully!");
	}

	@Override
	public ProductResponseDto getSingleProduct(Long productId) {
		Product product = productDao.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("No product found with that id"));
		if (product.getManufacturer() == null) {
			throw new ResourceNotFoundException("No product found with that id");
		}

		ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
		productResponseDto
				.setColors(product.getColorSet().stream().map(s -> s.getColor().name()).collect(Collectors.toSet()));
		productResponseDto
				.setSizes(product.getSizeSet().stream().map(s -> s.getSize().name()).collect(Collectors.toSet()));
		productResponseDto
				.setImageNames(product.getImageList().stream().map(p -> p.getImageName()).collect(Collectors.toList()));

		productResponseDto.setBrandName(product.getManufacturer().getBrandName());

		return productResponseDto;
	}

	@Override
	public ApiResponse deleteSingleProduct(Long productId, Long manufacturerId) {
		Manufacturer manufacturer = manufacturerDao.findById(manufacturerId)
				.orElseThrow(() -> new ResourceNotFoundException("No such manufacturer found with that id"));
		Product product = productDao.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("No product found with that id"));
		manufacturer.removeProduct(product);
		return new ApiResponse(true, "Product deleted successfully");
	}

	@Override
	public ApiResponse updateProduct(ProductRequestDto productRequestDto) {
		Manufacturer manufacturer = manufacturerDao.findById(productRequestDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("No such manufacturer found with that id!"));

		manufacturer.getProductList().forEach(p -> {
			if (p.getId() == productRequestDto.getProductId()) {
				p.setActualPrice(productRequestDto.getActualPrice());
				p.setSellingPrice(productRequestDto.getSellingPrice());
				p.setInventoryCount(productRequestDto.getInventoryCount());
				p.setDescription(productRequestDto.getDescription());
			}
		});

		return new ApiResponse(true, "Product updated successfully");
	}

	@Override
	public List<ProductResponseDto> getAllProducts() {

		List<Product> productList = productDao.findAll();

		List<ProductResponseDto> productResDtoList = new ArrayList<>();

		for (int i = 0; i < productList.size(); i++) {

			Product product = productList.get(i);

			if (product.getManufacturer() == null) {
				continue;
			}

			ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
			productResponseDto.setColors(
					product.getColorSet().stream().map(s -> s.getColor().name()).collect(Collectors.toSet()));
			productResponseDto
					.setSizes(product.getSizeSet().stream().map(s -> s.getSize().name()).collect(Collectors.toSet()));
			productResponseDto.setImageNames(
					product.getImageList().stream().map(p -> p.getImageName()).collect(Collectors.toList()));

			productResponseDto.setBrandName(product.getManufacturer().getBrandName());

			productResDtoList.add(productResponseDto);
		}

		return productResDtoList;
	}

}
